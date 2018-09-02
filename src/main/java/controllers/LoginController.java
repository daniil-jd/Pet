package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tools.AuthTool;
import tools.PropertyTool;

import java.io.IOException;

/**
 * Controller for loading the login page.
 */
public class LoginController {

    @FXML
    private AnchorPane loginPane;

    @FXML
    private PasswordField passwordField;

    /**
     * SignUpController.
     */
    private SignUpController signUpController;

    /**
     * Parent.
     */
    private Parent fxmlParent;

    /**
     * SignUpStage.
     */
    private Stage signUpStage;

    /**
     * Check that password exist.
     */
    private boolean checkPasswordIsExist = false;

    @FXML
    private void initialize() {
        initSignUp();
    }

    /**
     * Init sign up stage.
     */
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

    /**
     * Creates main form.
     */
    private void createMainForm() {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/Main.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.NONE);
            stage.setResizable(false);
            stage.setTitle("Password Storage");
            stage.setScene(new Scene(root1));
            passwordField.getScene().getWindow().hide();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert();
        }
    }

    /**
     * Login Button.
     * @param actionEvent actionEvent
     */
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

    /**
     * Show sign up form.
     */
    private void showSignUp() {
        if (signUpStage == null) {
            signUpStage = new Stage();
            signUpStage.setTitle("Enter new password");
            signUpStage.setResizable(false);
            signUpStage.setScene(new Scene(fxmlParent));
            signUpStage.initModality(Modality.WINDOW_MODAL);
            signUpStage.initOwner(passwordField.getScene().getWindow());
        }
        signUpStage.showAndWait();
    }

    /**
     * Show alert window.
     */
    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Wrong Password");
        alert.setHeaderText("Password is incorrect");
        alert.setContentText("Check your pass");
        alert.showAndWait();
    }

    /**
     * When mouse moves to Login Pane.
     * @param mouseEvent mouse Event
     */
    public void onMouseMove(MouseEvent mouseEvent) {
        if (!checkPasswordIsExist) {
            PropertyTool pt = new PropertyTool();
            if (!pt.isFileExist() || (!pt.isPropertyExist("password") && pt.isEmpty())) {
                showSignUp();
            }
            checkPasswordIsExist = true;
        }
    }
}
