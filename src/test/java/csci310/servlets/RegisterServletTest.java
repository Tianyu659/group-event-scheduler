package csci310.servlets;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RegisterServletTest {
    @InjectMocks
    RegisterServlet rs;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    private static final String IV  = "YbnEOG6PvcYsDMb7zJry8g==";
    private static final String AES = "cEpdazebLLy5hqAvfj0aUb3qTe2KAk0B9nItyhknrEKb43hEFk3NhNvI4S9ynlux" +
                                      "u4+Vi4HqdvmPkEgeRS4B09LyzB6qnmx61SBveVftccgSDRUszJKwzsVJ4uQH8rwe" +
                                      "Sgd38Rq/O6dSzYUu5LZIcq4X5BOvS9lHKFvnWO97X9ptf3LLoBginRD9tW3lCR5H" +
                                      "fowL6b2HuwpMGd2xBvEco+5iaZunmuWSNvGAvULPrRjV8gGM4HHTPTZr5GAI/Gy2" +
                                      "8etjDEZ4I7EEGaKBwIn8ZwzklDQ0Xpe9VVauHetkwuZiV+r+ytMV3tS6jG8UBUfp" +
                                      "FM7FsdugISmvLqoxHqpFpCORrWviWoU6BVCCIDEAQRyOjVQx5Rl1EYo9/tckjAuX" +
                                      "GYRv4kUKakqRU3osZGwmT5j/1IUPk9Tw/+xbho/mSDDHsQ8IQXYJhhgTVPPwKt+V" +
                                      "uybsCgDLCm/uid3UyhBlV6DCwpW3dCDIXfTbjos1zwYqk+Z42yZlivJUiGIZjSrn" +
                                      "uM77We6mXpU72CjvQsvzFrYqAuRtfzBpiKKyGXhhQf5na1wiNVyuD571bSS+hDdH" +
                                      "qjG4v86ktp99UTBHPw6OPLyXB90IXKNwwIviWTj50C8NPEiQvI2t/CPNds6JUmzg" +
                                      "ZVzF1ddQL6mWJhlEJ+x83KUGK3H8W88d+3IasWPrSzU=";
    private static final String MSG = "UwnoWWeGV68XvgZbiOEC2VtAlhI5xIBnZCNyyEbsfb4=";
    private static final String RSA = "MIIJQgIBADANBgkqhkiG9w0BAQEFAASCCSwwggkoAgEAAoICAQCxbdEh/0J5JRD1" +
                                      "Wu81LlbPbu25dgt6JTEl6TSThKpCG28UQqq3RS3hTCZmaavr+Cp+E1YGsluJUzDM" +
                                      "Tjv6Ndcdp40PkafdYyuRtyUq6LBjbqQLADDEQ0gHPFlySdEtSUdvKR3BLEdmU/Ao" +
                                      "EKl7q54YoIMcMZ8Y1zlURdsMo7D6zNSJKWu5zcElZKgko7bjtTQr3RbJFU3KXcUM" +
                                      "BiVWisrMmy53DLEY+RPFVOt2tGkYHiPgqWpAAuelrMO8vQH+FYOle6/II+A/Dfa9" +
                                      "fbFlpFHVP+t2NvRDArfdWOGqPGQ+xzyBMMFVd2pt85sVK3g/hq47Y1e8aT5+Jj0Q" +
                                      "VLW0jqOnNrUu4HHTcxVYa2OnTLbXDCiPOlIudh91BHiQUeJZEIdwiFvJrY3J1wOn" +
                                      "ySX2hr2isdM2bsTu7RsOlFomnFWF6xowhZPaZZZvYFbHyUnO0H+Yi+lBOyDvqU1s" +
                                      "SZkLoeHsAh20rET0AxGUEL0t9s1Krv3Y7AVmpW7Y97NzJbgIjYG2vUEPnOD4re44" +
                                      "6Nxhe6MQNV4RjoAQxDIMn/QVJMEk3QooJUka9o2YNQ4niqHMha78VZU6mxizSDW4" +
                                      "x5eSJ/S81JEwfXZoZ8L0AdpQSjgyxBVSdOKD18jmaxtVy+J+DmW9Q+KnIlIX2QFM" +
                                      "xIVFAv0YMQuTCHtdHWKzi4rBmJj9rwIDAQABAoICAAWba4mdAcwTsUw0e8aQK9S+" +
                                      "jZgX7gRSqsTaPVVqH5O0pT21+HCPqNCe/oze/jyYHG1DWpHrAglX2snVSrNW3AqT" +
                                      "+HLxzr6O1NonUdV5nAidMjImZxdUuJdN8qLlFU2BxgmlYLGk0hU4Rg8mE6auXwuQ" +
                                      "ZJ3rLYfavI7FIS27LV663Jgz4GbAGCbNd9WmMAnuXvM1HfRSp+4v9dTlWGztnu7f" +
                                      "YPtvKCU0MRbb3tyvlkcMvZdRRn1pk7BNj02Zu+qvlSteIMlJscHPkajqxES3icSC" +
                                      "koQRkYX3aIZMud9bRqAhqlZU2AJ47yEBZ7jzPwewgs7d2vBzDQAHxyDXsUeLxT0r" +
                                      "YwuSAgZyu5Bc8O38aDGDnbiN22tqvUMqwtatl6SAPeY1ES2t8Rl8FYfWszymUkyF" +
                                      "Fn0ivzoGzByUPwNnTnm3egcTEtEq01tMaz6q0ipNePw1UUGPI4tH+YWC0FfBJSKI" +
                                      "vaeIJgEMmhse2O9Oq1BWuct0zRqFDoDt/kehs1GBb2Wteu6o5L0l6lKX5oSqEU3d" +
                                      "0GXw2J104V0K3xknsHv4ZhMb9yOhg1MGZ60o3k40hSw4WEEOnnZsui86LJ4PRT+W" +
                                      "8u9ALqqlnVQDd8frLCGHMyngyaR0w4BFF/hH0+2tizdKoOv2UCGFO/B+jCYA90b5" +
                                      "Pl2dsuafa1wuwYEmMVFBAoIBAQDRwJydNsAn4rbv1yR8KLeuU4bUxsAl7KMI3gFy" +
                                      "8e37JbWTQ2gX4aFAsw42+z195eIkfvJKcbATxuVQIZQw7NxiiXVVExs2xK4RjLXG" +
                                      "lyLJkCCp5iMfCSzyIBLzMOIQGONwzqpKxENOp/sz/ZTJ7AkbmDdllszqYIOIZXFZ" +
                                      "kyODzohE6zuBS02zDAmVFDDr8+B0MyPDRDbUFMEF3Y2MUcrgeA5827pBizhS7V6T" +
                                      "EUjfD2aWmSKCxMwi+dAI89wMVp5cMPj/p19b/lpFJTFkkYKpxchGVfs9JfLAvPAy" +
                                      "nFd5HpDM1OHAKroh7APJjKGB2qTeYrLVffzGdETmUmosVD/vAoIBAQDYjLkXzv/3" +
                                      "jV7PG3haY/uRsV0kvcqHPiRJ/m0/CkPdhjZxmupgcCUHgtruDveVj97JpdGBZIQ5" +
                                      "1eBFJiLc5NibxKn+6c6EYFgC0NunJsWiL5KYAF02DLUw1GRCiWqQGwYWDOoxjTMP" +
                                      "BWZDi2e7hLYkva13SG2ut50TvIHt/lqH7QPdra7AOk5BsEWSzqJHxjwc5mH3KzOH" +
                                      "K64i2lFofusgbFZxOzTlDEue5hB0gnOBORYFoO9CTUWxWjkRn3etE3NZxJ8uqWsh" +
                                      "J6R0SZwqO94hOKaUgkTXs/4vXRopkgHDri6yYgjE3cEyi6vUXSYK8e4o5pje4uKL" +
                                      "HrALEqdI9V5BAoIBAGLnBXID/PtRpZzVvzhkIqgqbmsqBDRdrkpiFCDU/rfrggCc" +
                                      "ShrooxZU16EJOmeA2+8FhM2habsJj9sG2k9vgENeOVsSFUTv3Hwd8yBe52JPeXdj" +
                                      "MrBRrUXF5emR5SZd9fCdIiL7BBm0j3hmW6ASkgDW91gJkTn6pErOOnJYNOjTm+GT" +
                                      "YY2Hqm2AgYwv5xl69x0TeAt8wLWjy5guba36a0UIlxw8p0h8kuRXVL7X6Zp0MpVU" +
                                      "Q7eRgAgF7xgplFqlBSrHVMGHdCl2d64e6XlYO1o+Dj9MD5k3qY1ECaZznbNYvEI5" +
                                      "JvFrtDSGiwqgKw+FvzO1KkoMjAIvlMVdmv6oAzUCggEBAL+h6LpVCNd9V4VX/e2f" +
                                      "xSRtHMxC2FNOfrasXOkIoH4NI2jterPVCi/XcP5yaE8GflVcVEZ5OTolNs56rSdl" +
                                      "rPpL01Que/9RcxEq2vbUrNiV2NiCmfoWzNFlY0QubwsPnxcsXqNzHNgSIylq2GVt" +
                                      "T/K7mCKTCd0vx+WLUD/Ycy0OZflqldauwkoHP8pm6d0yZEf+6Hre1D6uPO/C23/p" +
                                      "sRGETgruJiT7+H+pgK5aMvUMG5znilPWJfKIsTATOskDCXVLNsMIUjGI71OpQHVv" +
                                      "fWdQxrltfpaURkCL13cqKj2Miu55KuFsjGe6iljoBZ5x8hxn1KiJh4ktowRpgXhj" +
                                      "CwECggEAJdL/WO6ssqI/gh3HeHbCDVaS/lGVNDOSNrj1eo9lnFikqnl9uyHRr4DY" +
                                      "iYn4i+aeubtqMj8JZP/3jXrKqwMeQo40cP14odvUCuJTTcsISjRFJR0b91M6v9MK" +
                                      "4MFsyey9EZqy8Jn9Y1Te+pfreoL6hbWOGFdHT7N5b8/C2PGKhoVrKhyCRJRvFbl8" +
                                      "5WiOjGABTudU7nnF6mnF+k9waXAZ6J0S5cRMMLnMLmR6nwGGu5V3ufuke2oASOch" +
                                      "04wEDAwM1op9MdwdXOm59tn+dYIq/7CvUpVBqzpevvU8WSRk77kYH7Kiaw7ZbAjA" +
                                      "1b8ifnTPx3Hmcv69Gaz6cH2VG8xUZw==";
    @Test
    public void testDoPost() throws Exception {
        final HashMap<String,Object> map = new HashMap<>();
        map.put(
            "keys",
            KeyFactory.getInstance("RSA").generatePrivate(
                new PKCS8EncodedKeySpec(Base64.getDecoder().decode(RSA))
            )
        );
        final HttpSession session = mock(HttpSession.class);
        doAnswer(a -> map.get(a.<String>getArgument(0))).when(session).getAttribute("keys");
        final HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getSession()).thenReturn(session);
        final BufferedReader br = new BufferedReader(new StringReader(String.join(",",IV,AES,MSG)));
        when(request.getReader()).thenReturn(br);
        when(request.getContentLength()).thenReturn(1);
        
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final StringWriter sw = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(sw));
        
        
        rs.doPost(request,response);
        final String result = sw.toString();
        System.out.println(result);
        //TODO test value of result once db stuff is done
    }
}