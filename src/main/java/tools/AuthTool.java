package tools;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.properties.PropertyValueEncryptionUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthTool {
    private static PasswordEncoder encoder = new BCryptPasswordEncoder();
    private static String password = "";

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

    public static String getEncodedPass(String password) {
        return encoder.encode(password);
    }

    public static String getOpenText(String encodedText) {
        try {
            if (password.length() > 0) {
                StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
                encryptor.setPassword(password);
                encryptor.setAlgorithm("PBEWithMD5AndDES");
                return PropertyValueEncryptionUtils.decrypt(encodedText, encryptor);
            }
        } catch (EncryptionOperationNotPossibleException e) {
            e.printStackTrace();
        }
        return encodedText;
    }

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
