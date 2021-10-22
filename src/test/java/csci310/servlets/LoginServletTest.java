package csci310.servlets;

import com.mongodb.client.MongoCollection;
import csci310.dbms.DBConnector;
import csci310.dbms.DBConnector.AuthResult;
import csci310.security.EncryptionUtil;
import org.bson.Document;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.nio.charset.StandardCharsets;

enum LoginTestCase implements AuthServletTestCase<AuthResult> {
    success(
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
        
        "MIIJQQIBADANBgkqhkiG9w0BAQEFAASCCSswggknAgEAAoICAQCN8FtuZZJlFrxY"+
        "YR7CHcFAN1VNwKXkOo0QQdZCi+iJJ2v79pmScceHkhRh/aoF1pXld7pjZYt8wdE2"+
        "xjYhri7+YnFw0xGptvzdzQEfmwYql+5sSrHH6YjPUcZvh325ooF2ZOIPnIjZNVp2"+
        "KlahJvqFJEkyhIvKCo6QyYcGZ8WDmCLty5zwKNzCTeljVhssdwU851SuHqlVU9v7"+
        "zw4rBKBkcUvfILuob6hwGMo93YPX+zw3PhpbjGZq4V7wT0FnQfCk+Gr5su3Bq1XI"+
        "Iix0v2pP3H7KXDzdsHs+NdIv91SrVBscPDGUm9eUcYuVwRwDBzuVPnmgaDQJHqED"+
        "lZRLjnXM7UauvtJviAgfaeGamseG92jgTRZh7j7h8pvnwwZm4J9deBidUBCy5Qm/"+
        "OWizX2OjtwrhYqmR/OjGDJlz/nhABG5qZc6B6+WUMXlHWxRl4Nm8IQoenP2s9mGp"+
        "uA6q3Cqt1qPb8N6TnBubTIlFvO96ne4l3/Jge6qQIpfdtoR9c8kwo/SxruMFq4MF"+
        "nTPeSW6MqqvimOsVn6Els8DX+pJ7zCeucfMJfsHfsEkx5lQ9cMvLC05P98mn9rW/"+
        "DV2/VLqggnjaqUEQhP8pBWoDaEvjXwzvYRXbX+FVw54NM/TW5B+SHqQBcbgWvse2"+
        "IaV8jMKh6+d4A2rgaw9Y8afd5tN2/QIDAQABAoICABFX9VQgBStlA86oz2FY0WxP"+
        "VpURnTOuE96QrOWkwt0/e+plz44daQwfxW3T2RqthdNsNdI/bjPoWvAeeC7VYdij"+
        "nsjygY4DVhKkIicCglZiCjghSGftGVJ744ttOueb+8XDvfu6XN5trogR1FQMtz/d"+
        "lzQdDhe6AoGenmkh9PiHdn0oDB0sYQ09B++jbd6ZDILMoYRFKtvKS28RdeG7xv1+"+
        "+o5hKY0DV8mavduhXaLeiBUX+ZCGL12P/FHQ3WUIoacG08t/GZ44mVrDEjuRyCPK"+
        "BZQkgSt1jRmk6fICfs4QkN96W3SVNlplJXE/D3UvRRCPeb0n/nO+0l3lwSEaXzpk"+
        "i/wlJEwCUl2Nr6UGaDBFQQtFDWXokgN+S7iRFwAj3JfPDOQ/pS+4iJqYpRMS4yuv"+
        "4IgjVuro9N2Z08iEjtoNmwkPdFwCgkZNvXpBmXkYpcozPldC47I6bS4OKdGx4INb"+
        "F47RoBtilIiWpriv1cwyZnBL4P/v7Qd3oxsQ/cXd5YvZUgaL1b/oH6f6q0pGt2nM"+
        "Fydko/lM+yqBPJOMECxKfeMuPKDu7+OOtTigzmJcc5zB5aeInHymJv15KUGltwxI"+
        "StZ+7aE1EN4d6A6/yBLc/c/CvhAjzFomcewTMaGqRwMmf9/7dRXela1EzQsbIxAd"+
        "QvLX344/TKSkve+VffEBAoIBAQDCT0kkWAcGCc+H2zd0VnHTwrvTFKpdauzy+N/+"+
        "asTKjQwFi1ZmdqYJin8IA5Qy619622c5Gdq/0mnSBrrNUEvu9/j1Y399uFdKi8n4"+
        "M08QFpw3fsgr+t9aDQXDhX0NVp6gbVFmQH7/ISIlEdWsBzulTO7CauxKfiJggMKd"+
        "EikysJkeldQ6l4lIFQwnFgxVFUqEx/8HiGuDBVPwSi/uFLkq7GzkHOWYT5V2PHTe"+
        "MmB2gqoQ7aywwWjdnRPyVkTSEJUAQ/nQyxB3rHi8essdA6wS+VBuXbcrXFdamXtc"+
        "hobT6qsssDFW2vCHBebTALWUAmZqd4EwT5iGY8LOmQ1Y9Yf1AoIBAQC7AJThTiCw"+
        "FE9eqXcpFpdlE9cMA+yR2nn5x6NcyiOoNLnuLovRhBOUY8k7YSqu9JqUSEiuES2m"+
        "+P7shuJbHkeR2PKCqIh5/uwWHpfB5Zs5JDdoYErkxL/cSR3WnieNSYjUrmyU4bFM"+
        "a+wjJBYXF/D2f+Hg4FS0uRuD3LWxlmzxtMu6oiFLfoPfesIlc9kNGhK5KyFWM2rp"+
        "kqEICEcZ7UDh3XjrMHHJxtyOrRx8yIHXkbi2yK4S/NNYZ5PT+REtOTKomKhFBTZw"+
        "fEXvOgEO9s4GPKTQAfWZpo/W25HvCF8G+ms+lDaNRnk3pMmaA6lMsY0T4YLh3PB3"+
        "HoubblMhQTXpAoIBAAQbOtd6yMM259zeJCDgRs2L2v5N/H+258UGv20YAoqpSmy/"+
        "khbeQQoIlECuGq6szLrC8j6Brl7JapUWsVHvdXtaYDeHX8VfZPDdXWpSY0Raxfhi"+
        "RIaxaZD0n3+PTaI/h2CqjsLTcBuiLp+L7fERwg/vPUh8i/vTCJTzg6lgHeDFXKcJ"+
        "0Y9fbnZIg5Y74AzYQ1flBh25o1QEg86bMsIcTAF9N5vmhG+glu8Fvg6IEAPRJRjw"+
        "fOrLW5T7YdWIPzIVSaDUzu27rOBqk66gBbTaRAOuoyWB84IGFI6EmdfNvAG0fuOV"+
        "4aUNQXIJDOS+qhezqUhsuB02QWntp8tH7sFHhxUCggEALm4miQps80sXqdlJzNnf"+
        "HSmf60vOISitwGbhGNjXoZv1zj2gWZUBh4Z/IE8v2eDBwK9x3EZHn32TTgbvjNAn"+
        "1P6Yt/hAaonw9E0EQRBRSg7D8xZ1gfeX64n77p22FE59utDUBONEskTZjKAZHALF"+
        "wW5fFwacAYM0YbkbI8zFn/3T17MJsUToWUSRj9sDuU0P+QnMbSq3EOdnNCdaAAX4"+
        "ZhwnOb39gqvtdz9kI1dlZbC4vCl3jx9XGkwdWDQvH5tkCJher0RsD+HLO2qbeKuu"+
        "272S7lKOOGgyrpY11bOuZH0ow5DpOoqU7kvsAmGGV5R+AsGrTYkd63WaFC2AVnFq"+
        "gQKCAQA0B+XPn9eeBNaglhYyADYiOYXaLGEFkvntXyOFkpaCAce3qF12VW1wmWTs"+
        "jL6NvBOsTgyoUxVQHIYezPjBcamz7QWjIfa67e5dq650jTHFb4AWEGjiSK63c4ae"+
        "Tjjlwps7m9bBcbxGMHjFwMWIj82OcA6O0heuaLCvVcNtQ9RVC7o5k9Nc24nKfe6L"+
        "r9FQ5vy9KtMVbcOxcNGWjIJWL0Ttg31OcdJFgCzN5OF+YJ0R/ib9hGILgJkJfAKv"+
        "vVFk3OvmV2xuvzGIjFUcUxREoeJn3IZijO80HXJZX/V/V6elprzdwswujmdkazcS"+
        "PuX9ZFFivBp38yrFociFqVYSUf3B"
    ),
    unknown_user(
        "5FQ+ag+mUvMdLtFcz+D4Pw==",
        
        "qBnrMZu1b19O2G95IKRoUex9I0USVYBe3KlRhNRCcXoGhMf16Dupa5Yl2dHFlYDH"+
        "KQY4v0YYHBdlxHpweLRzk8LvgjrIICCpxYK0dZDpKhcYnbQJsxadEzumcXMMn6gY"+
        "X4U3TqOjuQvSKTyNUTcSibnXoMGk+TsmJnJVqWPruNgdfMNvLhX6nMsgNNQ2HDlf"+
        "Y/kuoXFhkwn03XfcluG87q3hNaF4c32vc3zQ0CqBrxX12jlvTrnnDkuvTwJ9zS8P"+
        "pjyKWkgkUtnB+jVW+vpvKh8ve8YzY6gMsxx7ljnx+vrH4fEUZjVdj8HYiQ7ZLvDp"+
        "62ROHqSdXmKzA2kn5x/lLtijIVZXls4nJiq/+cU/tFWrOYkTO8L01+4nRjSs0fYB"+
        "ta9R0+whsHgUrDhpNpw4Uj5PVhGpuXeBbQHlTpSf44T8fptE5E6TZWMqffYMrIoG"+
        "NqKXHLF+iAfUhVQIzLjcIB7meDtdZcDbz3TO7+uZqCWEEUPzdWA5QystxctqLrKy"+
        "6ROyZUWPd3tcq10EqVIPIWRMaDWIvyidnAYpwTt+IvAJgxF7KtxrrIaxrPc6Nabp"+
        "4RpahrCJQgjEXGqkOcPCEy+H/vABMKDn0rKrQ+wi6GqE/dZ0E8rOYukyPa4jBQqE"+
        "Qsx3mQkdFx6U39p3dAXUNiHDOYGtUa/W+cNR8PVZBRw=",
        
        "KsrBG2YmZE1dfvBTvY5nJUZILJY5j/ugil3S8s9MfK4=",
        
        "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"+
        "gwi8V7Us+je5lNV74yVmzyKZmlUh"
    ),
    incorrect_password(
        "i0xNjCyk60wEujhBrwQ6Cw==",
        
        "1Eb8RR7atfpwlHFIGM35gbIPgG55YKVAEXjMb3SozNlgfkPa0JuZvFtBFJEijwkL"+
        "TiB3ZaYFw94CyWsu9I4IhmAJThMYaDWJTTKeioBwgOdvLu7G5q8Ql8jMFdQk03+j"+
        "baY6ZFQJ2jvlah2/ou48pC5m4YKhfrVj+o1yjNz2kZxotFFPmUGvnHVlNiryAmhS"+
        "eQLhQmI8Gf1suTktwZFNjH4C2v7uaQeDG6ZDOXmhwmdYTKET+O+TV9dJxL4QkyHB"+
        "7bW3WzdpT6P6obiLJMzyHIZ65z5PzV207GBgeLhrTe5CssOqLNrHlJWzeHe9lFUA"+
        "v9TJExVTL67CTtK9CVBqdyqGTygPwASegrdTvrevnPeOYHi2g5FyGDhBYFOkCNkm"+
        "mEICIQjVi6sgYAYILCRsamSylh0qom/F+0+n+PVubchPaoWgE3vEi7xvtE5vGXCE"+
        "04w7gXoSpWzPtkjBen5SB0sCZ3fxvqybIKavv5x7nJuaiRIlo+dDt21ithbf0wzF"+
        "Woh87RbB8O/IvqrOOVF4TstbPx0fIjw41CnrEQeuq29aCFWjiLEAFBbUXG8MEaAL"+
        "7fND4sgtHre5W4UTJYPdZ0agTM7ArOnU65t7Su3SdK3Za+kxDD993E+i8+uVB3v4"+
        "Jc/+8b9QIcOE9hXbtq/VvuX0a6WrttmYOIh5VjMt7q8=",
        
        "JETkX0cscj+cLXYXV7hh6W4TRnqr5gWhqRhAFbLE6BI=",
        
        "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"+
        "ETZD0WzuCK2wEqy73zfAA2nBxa3eEk0="
    );
    
    final String joined,rsa;
    LoginTestCase(final String iv,final String key,
                  final String msg,final String rsa) {
        joined = String.join(",",iv,key,msg);
        this.rsa = rsa;
    }
    @Override public String rsa() {return rsa;}
    @Override public String joined() {return joined;}
    @Override public String toString() {return name();}
}
@RunWith(MockitoJUnitRunner.class)
public class LoginServletTest extends AuthServletTestBase<AuthResult,LoginTestCase> {
    @InjectMocks LoginServlet lis;
    
    public LoginServletTest() {super(LoginTestCase.values());}
    
    @Override LoginServlet getServlet() {return lis;}
    
    private static final byte[] USERNAME = "test_username".getBytes(StandardCharsets.UTF_8);
    private static final byte[] PASSWORD = "test_password".getBytes(StandardCharsets.UTF_8);
    
    @BeforeClass
    public static void dbSetup() {
        final MongoCollection<Document> coll = DBConnector.getCollection();
        coll.deleteOne(DBConnector.usernameFilter(EncryptionUtil.hash(USERNAME)));
        DBConnector.register(USERNAME,PASSWORD);
    }
}