package csci310.dbms;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.CountOptions;
import com.mongodb.client.model.Filters;
import csci310.security.EncryptionUtil;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Arrays;

public class DBConnector {
    public static final MongoClient CLIENT;
    static {
        try(
            final BufferedReader br = new BufferedReader(
                new FileReader(
                    Paths.get(
                        System.getProperty("user.dir"),
                        "src","main","dbms","connection_string.txt"
                    ).toFile()
                )
            )
        ) {
            CLIENT = MongoClients.create(
                br.lines().map(String::trim)
                          .filter(s -> s.charAt(0) != '#')
                          .findFirst()
                          .orElseThrow(() -> new RuntimeException("Missing connection string."))
            );
        } catch(final IOException e) {throw new RuntimeException(e);}
        Runtime.getRuntime().addShutdownHook(new Thread() {@Override public void run() {CLIENT.close();}});
    }
    public static final String DB_NAME = "groupie";
    public static final String USERS_COLLECTION_NAME = "users";
    public static MongoCollection<Document> getCollection() {
        return CLIENT.getDatabase(DB_NAME).getCollection(USERS_COLLECTION_NAME);
    }
    public static Bson usernameFilter(final byte[] username) {return Filters.eq("username",username);}
    public static Document findUser(final byte[] username) {
        return getCollection().find(usernameFilter(username)).first();
    }
    public static boolean userExists(final byte[] username) {
        return getCollection().countDocuments(usernameFilter(username),new CountOptions().limit(1)) != 0;
    }
    
    /* ========== Authentication ========== */
    public enum AuthResult {unknown_user,incorrect_password,success}
    public static AuthResult auth(final byte[] username,final byte[] password) {
        final Document doc = findUser(EncryptionUtil.hash(username));
        return
            doc != null
               ? Arrays.equals(
                    EncryptionUtil.hash(
                        password,
                        doc.get("salt",org.bson.types.Binary.class).getData()
                    ),
                    doc.get("password",org.bson.types.Binary.class).getData()
                )
                     ? AuthResult.success
                     : AuthResult.incorrect_password
               : AuthResult.unknown_user;
    }
    public static AuthResult auth(final String username,final String password) {
        return auth(
            username.getBytes(StandardCharsets.UTF_8),
            password.getBytes(StandardCharsets.UTF_8)
        );
    }
    public static AuthResult auth(final String[] credentials) {return auth(credentials[0],credentials[1]);}
    
    /* ========== Registration ========== */
    public enum RegResult {user_exists,success}
    public static RegResult register(byte[] username,final byte[] password) {
        if(userExists(username = EncryptionUtil.hash(username))) return RegResult.user_exists;
        final byte[] salt = EncryptionUtil.nextSalt();
        getCollection().insertOne(
            new Document("username",username)
                 .append("salt",salt)
                 .append("password",EncryptionUtil.hash(password,salt))
        );
        return RegResult.success;
    }
    public static RegResult register(final String username,final String password) {
        return register(
            username.getBytes(StandardCharsets.UTF_8),
            password.getBytes(StandardCharsets.UTF_8)
        );
    }
    public static RegResult register(final String[] credentials) {return register(credentials[0],credentials[1]);}
}