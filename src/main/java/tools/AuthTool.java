package tools;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthTool {
    private static PasswordEncoder encoder = new BCryptPasswordEncoder();

    public static boolean isPasswordCorrect(String password) {
        PropertyTool pt = new PropertyTool();
        String encodedPass = pt.getProperty("password");
        if (encodedPass == "") {
            return false;
        }
        if (encoder.matches(password, encodedPass)) {
            return true;
        }
        return false;
    }

    public static String getEncodedPass(String password) {
        return encoder.encode(password);
    }
}
