package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tools.AuthTool;
import tools.PropertyTool;

import java.io.IOException;

public class LoginController {

    @FXML
    private PasswordField passwordField;

    private SignUpController signUpController;

    private Parent fxmlParent;

    private Stage signUpStage;

    @FXML
    private void initialize() {
        initSignUp();
    }

    private void initSignUp() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/views/NewPassword.fxml"));
            fxmlParent = fxmlLoader.load();
            signUpController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void createMainForm() {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/Main.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.NONE);
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            passwordField.getScene().getWindow().hide();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert();
        }
    }

    public void loginButton(ActionEvent actionEvent) {
        PropertyTool pt = new PropertyTool();
        if (!pt.isPropertyExist("password")) {
            showSignUp();
        }

        if (AuthTool.isPasswordCorrect(passwordField.getText())) {
            createMainForm();
        } else {
            showAlert();
        }
    }

    private void showSignUp() {
        if (signUpStage == null) {
            signUpStage = new Stage();
            signUpStage.setTitle("Sign Up");
            signUpStage.setResizable(false);
            signUpStage.setScene(new Scene(fxmlParent));
            signUpStage.initModality(Modality.WINDOW_MODAL);
            signUpStage.initOwner(passwordField.getScene().getWindow());
        }
        signUpStage.showAndWait();
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Wrong Password");
        alert.setHeaderText("Password is incorrect");
        alert.setContentText("Check your pass");
        alert.showAndWait();
    }
}
