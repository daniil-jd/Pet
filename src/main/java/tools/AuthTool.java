package tools;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.PropertyValueEncryptionUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Auth tool. Responsible for authentication, encryption and decryption of text.
 */
public class AuthTool {
    /**
     * Encoder.
     */
    private static PasswordEncoder encoder = new BCryptPasswordEncoder();
    /**
     * This is absolutely not exactly the password in open form.
     */
    private static String password = "";

    /**
     * Check that password correct.
     * @param password not a password
     * @return true if correct
     */
    public static boolean isPasswordCorrect(String password) {
        PropertyTool pt = new PropertyTool();
        String encodedPass = pt.getProperty("password");
        if (encodedPass == "") {
            return false;
        }
        if (encoder.matches(password, encodedPass)) {
            AuthTool.password = password;
            return true;
        }
        return false;
    }

    /**
     * Gets encoded password.
     * @param password not a password
     * @return encoded password
     */
    public static String getEncodedPass(String password) {
        return encoder.encode(password);
    }

    public static String getOpenText(String encodedText) {
        if (password.length() > 0) {
            StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
            encryptor.setPassword(password);
            encryptor.setAlgorithm("PBEWithMD5AndDES");
            return PropertyValueEncryptionUtils.decrypt(encodedText, encryptor);
        }
        return "";
    }

    /**
     * Gets encoded text from open text.
     * @param openText open text
     * @return encoded text
     */
    public static String getEncodedText(String openText) {
        if (password.length() > 0) {
            StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
            encryptor.setPassword(password);
            encryptor.setAlgorithm("PBEWithMD5AndDES");
            return PropertyValueEncryptionUtils.encrypt(openText, encryptor);
        }
        return "";
    }
}
