package android.app.java.com.duovoc.framework;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

final public class CipherHandler {

    private static final String ALGORITHM = "AES";
    private static final String SALT_KEYS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    private static final int KEY_SIZE = 16;

    private CipherHandler() {}

    public static String encrypt(final String source, final String key) {

        String result = "";

        try {

            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(), ALGORITHM));
            result = new String(Base64.getEncoder().encode(cipher.doFinal(source.getBytes())));

        } catch (NoSuchAlgorithmException
                | NoSuchPaddingException
                | InvalidKeyException
                | BadPaddingException
                | IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String decrypt(final String source, final String key) {

        String result = "";

        try {

            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(), ALGORITHM));
            result = new String(cipher.doFinal(Base64.getDecoder().decode(source.getBytes())));

        } catch (NoSuchAlgorithmException
                | NoSuchPaddingException
                | InvalidKeyException
                | BadPaddingException
                | IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String generateSecretKey() {

        final StringBuilder salt = new StringBuilder();
        final Random rnd = new Random();
        final int keysLength = SALT_KEYS.length();

        while (salt.length() < KEY_SIZE) {
            final int index = (int) (rnd.nextFloat() * keysLength);
            salt.append(SALT_KEYS.charAt(index));
        }

        return salt.toString();
    }
}
