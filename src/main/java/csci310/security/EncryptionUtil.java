package csci310.security;

import csci310.servlets.SessionAttributes;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class EncryptionUtil {
    static void dbg(final String title,String str) {
        System.out.println(title+':');
        while(str.length() > 64) {
            System.out.println('"'+str.substring(0,64)+"\"+");
            str = str.substring(64);
        }
        System.out.println('"'+str+'"');
        System.out.println();
    }
    /* ========== Hashing Stuff ========== */
    private static final String HASH_ALGORITHM = "SHA-256";
    public static byte[] hash(final byte[] secret) {
        try {
            final MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM);
            md.update(secret);
            return md.digest();
        } catch(NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public static byte[] hash(final byte[] secret,final byte[] salt) {
        try {
            final MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM);
            md.update(secret);
            md.update(salt);
            return md.digest();
        } catch(NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public static byte[] nextSalt() {
        final byte[] salt = new byte[16];
        try {SecureRandom.getInstanceStrong().nextBytes(salt);}
        catch(final NoSuchAlgorithmException e) {throw new RuntimeException(e);}
        return salt;
    }
    
    /* ========== Utilities ========== */
    private static final String RSA = "RSA/ECB/OAEPPadding";
    private static final String AES = "AES/CBC/PKCS5Padding";
    private static final Base64.Decoder B64D = Base64.getDecoder();
    private static Cipher getRSACipher(final int mode,final Key key) {
        try {
            final Cipher c = Cipher.getInstance(RSA);
            c.init(
                mode,
                key,
                new OAEPParameterSpec(
                    HASH_ALGORITHM,
                    "MGF1",
                    new MGF1ParameterSpec(HASH_ALGORITHM),
                    PSource.PSpecified.DEFAULT
                )
            );
            return c;
        } catch(NoSuchAlgorithmException|NoSuchPaddingException|
                InvalidAlgorithmParameterException|InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
    private static Cipher getRSACipher(final PrivateKey key) {return getRSACipher(Cipher.DECRYPT_MODE,key);}
    private static PublicKey getPublicKey(final byte[] key) {
        try {return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(key));}
        catch(NoSuchAlgorithmException|InvalidKeySpecException e) {throw new RuntimeException(e);}
    }
    private static Cipher getRSACipher(final PublicKey key) {return getRSACipher(Cipher.ENCRYPT_MODE,key);}
    
    private static Cipher getAESCipher(final byte[] key,final byte[] iv) {
        try {
            final Cipher c = Cipher.getInstance(AES);
            c.init(
                Cipher.DECRYPT_MODE,
                new SecretKeySpec(key,"AES"),
                new IvParameterSpec(iv)
            );
            return c;
        } catch(InvalidAlgorithmParameterException|NoSuchPaddingException|
                NoSuchAlgorithmException|InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
    /* ########## CLIENT -> SERVER ########## */
    public static KeyPair generateRSAKeyPair() {
        try {
            final KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(4096);
            return keyGen.generateKeyPair();
        } catch(final NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public static byte[] decryptHybrid(final String iv,final String key,
                                       final String msg,final PrivateKey rsa) {
        try {
            //TODO debug
            /*dbg("iv",iv);
            dbg("key",key);
            dbg("msg",msg);*/
            
            final byte[] aes = B64D.decode(getRSACipher(rsa).doFinal(B64D.decode(key)));
            // Decrypt message with AES
            return getAESCipher(aes,B64D.decode(iv)).doFinal(B64D.decode(msg));
        } catch(IllegalBlockSizeException|BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }
    public static byte[] decryptHybrid(final BufferedReader reader,
                                       final PrivateKey rsa) throws IOException {
        final String[] split = reader.readLine().split(",",3);
        return decryptHybrid(split[0],split[1],split[2],rsa);
    }
    /**Initializes the RSA keys.*/
    public static void initRSA(final HttpServletRequest req,
                               final HttpServletResponse resp) throws IOException {
        final KeyPair kp = EncryptionUtil.generateRSAKeyPair();
        SessionAttributes.keys.setAttribute(req.getSession(),kp.getPrivate());
        final byte[] out = kp.getPublic().getEncoded();
        resp.setContentType("application/octet-stream");
        resp.setContentLength(out.length);
        resp.getOutputStream().write(out);
        
        //TODO debug
        //dbg("private",Base64.getEncoder().encodeToString(kp.getPrivate().getEncoded()));
    }
    
    /* ########## SERVER -> CLIENT ########## */
    /**Encrypts the message using a public RSA key from the client.*/
    public static void encrypt(final HttpServletRequest req,
                               final HttpServletResponse resp,
                               final String msg) throws IOException {
        try {
            final byte[] o = getRSACipher(
                getPublicKey(B64D.decode(req.getReader().readLine()))
            ).doFinal(msg.getBytes(StandardCharsets.UTF_8));
            resp.setContentType("application/octet-stream");
            resp.setContentLength(o.length);
            resp.getOutputStream().write(o);
        } catch(IllegalBlockSizeException|BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }
}