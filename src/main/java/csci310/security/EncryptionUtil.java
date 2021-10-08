package csci310.security;

import csci310.servlets.SessionAttributes;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class EncryptionUtil {
    /* ========== RSA Encryption ========== */
    private static final String RSA = "RSA/ECB/OAEPPadding";
    public static KeyPair generateRSAKeyPair() {
        try {
            final KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(4096);
            return keyGen.generateKeyPair();
        } catch(NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    private static final String AES = "AES/CBC/PKCS5Padding";
    public static byte[] decryptHybrid(final String iv,final String key,final String msg,final PrivateKey rsa) {
        try {
            final byte[] aes;
            {
                // Decrypt AES key with RSA
                final Cipher c = Cipher.getInstance(RSA);
                c.init(
                    Cipher.DECRYPT_MODE,
                    rsa,
                    new OAEPParameterSpec(
                        "SHA-256",
                        "MGF1",
                        new MGF1ParameterSpec("SHA-256"),
                        PSource.PSpecified.DEFAULT
                    )
                );
                aes = Base64.getDecoder().decode(c.doFinal(Base64.getDecoder().decode(key)));
            }
            // Decrypt message with AES
            final Cipher c = Cipher.getInstance(AES);
            c.init(
                Cipher.DECRYPT_MODE,
                new SecretKeySpec(aes,"AES"),
                new IvParameterSpec(Base64.getDecoder().decode(iv))
            );
            return c.doFinal(Base64.getDecoder().decode(msg));
        } catch(NoSuchAlgorithmException|NoSuchPaddingException|
                IllegalBlockSizeException|BadPaddingException|
                InvalidKeyException|InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }
    }
    public static byte[] decryptHybrid(final BufferedReader reader,final PrivateKey rsa) throws IOException {
        final String[] split = reader.readLine().split(",",3);
        /*
        System.out.println("iv ="+split[0]+"\naes="+split[1]+"\nmsg="+split[2]);
        System.out.println("rsa="+Base64.getEncoder().encodeToString(rsa.getEncoded()));
        */
        return decryptHybrid(split[0],split[1],split[2],rsa);
    }
    /**Initializes the RSA keys.*/
    public static void initRSA(final HttpServletRequest req,final HttpServletResponse resp) throws IOException {
        final KeyPair kp = EncryptionUtil.generateRSAKeyPair();
        assert kp != null;
        SessionAttributes.keys.setAttribute(req.getSession(),kp.getPrivate());
        resp.setContentType("application/octet-stream");
        final byte[] out = kp.getPublic().getEncoded();
        resp.setContentLength(out.length);
        resp.getOutputStream().write(out);
    }
    
    /* ========== Hashing Stuff ========== */
    private static final String HASH_ALGORITHM = "SHA-256";
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
}