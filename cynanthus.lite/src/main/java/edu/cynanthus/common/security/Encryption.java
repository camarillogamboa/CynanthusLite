package edu.cynanthus.common.security;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * El tipo Encryption.
 */
public final class Encryption {

    /**
     * La constante DEFAULT_SECRECT_SPEC_KEY.
     */
    public static final SecretKeySpec DEFAULT_SECRECT_SPEC_KEY = createSecretKeySpace();

    /**
     * Instancia un nuevo Encryption.
     */
    private Encryption() {}

    /**
     * Create secret key space secret key spec.
     *
     * @return el secret key spec
     */
    private static SecretKeySpec createSecretKeySpace() {
        try {
            return Encryption.createSecretKey(
                "9Vcd7n7LPPc28W30PL4rHA".toCharArray(),
                "8pYWFod3HKNXxrAx320c=".getBytes(),
                40000,
                128
            );
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            return null;
        }
    }

    /**
     * Create secret key secret key spec.
     *
     * @param password       el password
     * @param salt           el salt
     * @param iterationCount el iteration count
     * @param keyLength      el key length
     * @return el secret key spec
     * @throws NoSuchAlgorithmException el no such algorithm exception
     * @throws InvalidKeySpecException  el invalid key spec exception
     */
    public static SecretKeySpec createSecretKey(
        char[] password,
        byte[] salt,
        int iterationCount,
        int keyLength
    ) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return new SecretKeySpec(
            SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512").generateSecret(
                new PBEKeySpec(password, salt, iterationCount, keyLength)
            ).getEncoded(),
            "AES"
        );
    }

    /**
     * Encrypt string.
     *
     * @param dataToEncrypt el data to encrypt
     * @param key           el key
     * @return el string
     * @throws GeneralSecurityException el general security exception
     */
    public static String encrypt(
        String dataToEncrypt,
        SecretKeySpec key
    ) throws GeneralSecurityException {
        Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        pbeCipher.init(Cipher.ENCRYPT_MODE, key);
        AlgorithmParameters parameters = pbeCipher.getParameters();
        IvParameterSpec ivParameterSpec = parameters.getParameterSpec(IvParameterSpec.class);
        byte[] cryptoText = pbeCipher.doFinal(dataToEncrypt.getBytes(StandardCharsets.UTF_8));
        byte[] iv = ivParameterSpec.getIV();
        return base64Encode(iv) + ":" + base64Encode(cryptoText);
    }

    /**
     * Encrypt string.
     *
     * @param dataToEncrypt el data to encrypt
     * @return el string
     * @throws GeneralSecurityException el general security exception
     */
    public static String encrypt(String dataToEncrypt) throws GeneralSecurityException {
        return encrypt(dataToEncrypt, DEFAULT_SECRECT_SPEC_KEY);
    }

    /**
     * Base 64 encode string.
     *
     * @param bytes el bytes
     * @return el string
     */
    private static String base64Encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * Decrypt string.
     *
     * @param encryptedData el encrypted data
     * @param key           el key
     * @return el string
     * @throws GeneralSecurityException el general security exception
     */
    public static String decrypt(String encryptedData, SecretKeySpec key) throws GeneralSecurityException {
        String[] values = encryptedData.split(":");
        String iv = values[0];
        String property = values[1];
        Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        pbeCipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(base64Decode(iv)));
        return new String(pbeCipher.doFinal(base64Decode(property)), StandardCharsets.UTF_8);
    }

    /**
     * Decrypt string.
     *
     * @param encryptedData el encrypted data
     * @return el string
     * @throws GeneralSecurityException el general security exception
     */
    public static String decrypt(String encryptedData) throws GeneralSecurityException {
        return decrypt(encryptedData, DEFAULT_SECRECT_SPEC_KEY);
    }

    /**
     * Base 64 decode byte [ ].
     *
     * @param property el property
     * @return el byte [ ]
     */
    private static byte[] base64Decode(String property) {
        return Base64.getDecoder().decode(property);
    }

}
