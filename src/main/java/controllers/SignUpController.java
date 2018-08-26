package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import tools.AuthTool;
import tools.PropertyTool;

import java.util.HashMap;
import java.util.Map;

public class SignUpController {

    @FXML
    private PasswordField passField1;

    @FXML
    private PasswordField passField2;

    public void createPass(ActionEvent actionEvent) {
        if (!passField1.getText().isEmpty() && !passField2.getText().isEmpty()
                && passField1.getText().equals(passField2.getText())) {
            PropertyTool pt = new PropertyTool();
            Map<String, String> map = new HashMap<>();
            map.put("password", AuthTool.getEncodedPass(passField1.getText()));
            pt.saveProperties(map);
            passField1.getScene().getWindow().hide();
        }
    }
}
