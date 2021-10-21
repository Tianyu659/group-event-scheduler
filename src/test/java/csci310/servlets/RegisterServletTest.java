package csci310.servlets;

import com.mongodb.client.MongoCollection;
import csci310.dbms.DBConnector;
import csci310.dbms.DBConnector.RegResult;
import csci310.security.EncryptionUtil;
import org.bson.Document;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.nio.charset.StandardCharsets;

enum RegisterTestCase implements AuthServletTestCase<RegResult> {
    success(
        "OrhnmgRjm6MTsQGlnwNdJA==",
    
        "YULcASLqdGTzuUwAghl7KjGY/PSrBkpShGOLL0igDwl8p/yjIF4m4oXlbQI6pXO+"+
        "uQOAQ2ukt13aYqArdCmtZFVtL5wOZznD+3+ww59NYaFQYVqEy0r5SF5/KyTHGvEb"+
        "dhh+mUSP+/e1WELHQtOT40bvdEUEaURnGoYEDC0q3OXZ+TOlygNpsR2X92zJ11g0"+
        "zOYUX4myJoP1A6PQo0Ua0WcKJgbEy34H/qQYfJsGFQMwUPaQEPWobfW3cZ7C+Sub"+
        "YHfFKAwi0MLn6SfWCDkE0XG4dG2aC92yhCGvgUKOIedZ54cUaybH4NkSg+tR+i4f"+
        "b7l9d15dUp1DQCIEbnm8GSvalxWm56dYVO4iH50BplAQWWxDTogun1Nchjyn/yoT"+
        "rQqKrUolOAC5w0RMtsj0GPw/P8G2T024cVJcsvDYrVBfnnYUq66g+pvFmPk5aWiX"+
        "KfcBlIRDTSrNhEZ+5zaaMwc1TjSHbkCddUGDXmPO2nZFD9Q4aIiaq5Qt2fPp9YTD"+
        "lw/m14Fu8Qrj1EXI8RERfh5vlxGAl2erROPQLYL4y6/gXPNJtXuP8ofDW6vE/g+b"+
        "0usowXm6FwiAym/xoq6Eqg/dWETO0y3V+aVsyQHKw4yQ8jeZP9bAXQtxfkLVZ8pA"+
        "Vw8Yia3BrZriWNM5OtbLzMwSbxTiQ0Y3Pvx2a0kQTYQ=",
    
        "5zPUV0Uw31RKrqAqjYVSPW9ub9zdBx4gIqWIteE6IOw=",
    
        "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"+
        "Sg0n41zvexXqAnUon2sO8OC2Oqwv"
    ),
    user_exists(
        "/DG/8BoDRiUX3OrqKbqTfQ==",
        
        "WgFqHpfWJCB5nLBgDpXTXpFHxWPdR4ivMm+IWWD93ItP+PAG0JCk2rL3TlEwELTX"+
        "1DOF7lej9qrUFranUE2GIrL3hoR8p+kINF7okW6xk1Y3D/AiCYps9JRmI2wm9joN"+
        "6PtKXKZvkIf54m/0LOeNGJfuXUkbQNaWxO1VbKwnsJIn7iVaTrNl7vb4Ubm0G/lE"+
        "ZX/2AZNE0Q2F20FHjScwi2XOznQu1RCIUd86FrFyvAgX8DSeEpbHZ9wZiDff5/gm"+
        "ZDhydPZwFG493f4wv87tUwwhtd4FQBQTEctrb0lRnFmMnSiAuls8YRpvHrK/ZpNu"+
        "csN3ETGWyJ6XAJYTRG9Zmloh+W95wpDSgDKIzrd8q5995cyaPL54mg2wuNp/mCKl"+
        "POmBRIFyNk3xGJ2ms+WFPb04j8TTGgXIHvS+YZ//TsNtsF/WOSYqbkFi3nnesmRs"+
        "zulnpv+EA8SaUs7NR9Il44pM8LVBPSGlEIA+bm8N16Ypiuv+CjUtltEfK1hOsGh8"+
        "ifh0v/tf1IZOCSIdxPUUg0bIl3TaRqRGC/86x8ehXiQcjmpzT6zIQCRdaf7DH4rL"+
        "cfvUx7WXvfFSUIh6uoNLC+gi1g51qjPC/EWAEPbnALKUoQHZRzXbs8t2en+52te6"+
        "xBZpRm4zB0asiO80MBS8ZvmEzRWWr1Ne//Y2LkNi9+w=",
        
        "f7mOedoDnXdWFJVa34zZvlcv6jSYdwCYXosu3J/3KG8=",
        
        "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"+
        "PuX9ZFFivBp38yrFociFqVYSUf3B"
    );
    
    final String joined,rsa;
    RegisterTestCase(final String iv,final String key,
                     final String msg,final String rsa) {
        joined = String.join(",",iv,key,msg);
        this.rsa = rsa;
    }
    @Override public String rsa() {return rsa;}
    @Override public String joined() {return joined;}
    @Override public String toString() {return name();}
}
@RunWith(MockitoJUnitRunner.class)
public class RegisterServletTest extends AuthServletTestBase<RegResult,RegisterTestCase> {
    @InjectMocks RegisterServlet rs;
    
    public RegisterServletTest() {super(RegisterTestCase.values());}
    
    @Override RegisterServlet getServlet() {return rs;}
    
    private static final byte[] TAKEN_USERNAME = "test_username".getBytes(StandardCharsets.UTF_8);
    private static final byte[] FREE_USERNAME = "free_username".getBytes(StandardCharsets.UTF_8);
    private static final byte[] PASSWORD = "test_password".getBytes(StandardCharsets.UTF_8);
    
    @BeforeClass
    public static void dbSetup() {
        final MongoCollection<Document> coll = DBConnector.getCollection();
        coll.deleteOne(DBConnector.usernameFilter(EncryptionUtil.hash(TAKEN_USERNAME))).getDeletedCount();
        coll.deleteOne(DBConnector.usernameFilter(EncryptionUtil.hash(FREE_USERNAME))).getDeletedCount();
        DBConnector.register(TAKEN_USERNAME,PASSWORD);
    }
}