package es.adrianroguez.controller;

import es.adrianroguez.config.ConfigManager;
import es.adrianroguez.controller.abstracts.AbstractController;
import es.adrianroguez.model.UserModel;
import es.adrianroguez.session.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class UserController extends AbstractController {
    @FXML
    Text userTitle;

    @FXML
    Text userText;

    @FXML
    TextField userField;

    @FXML
    Text emailText;

    @FXML
    TextField emailField;

    @FXML
    Text passwordText;

    @FXML
    PasswordField passwordField;

    @FXML
    Button playButton;

    @FXML
    Button editUserButton;

    @FXML
    Button goBackButton;

    @FXML
    public void initialize() {
        cambiarIdioma();
        cargarDatosUsuario();
    }

    @FXML
    protected void cambiarIdioma() {
        userTitle.setText(ConfigManager.ConfigProperties.getProperty("userTitle"));
        userText.setText(ConfigManager.ConfigProperties.getProperty("userText"));
        emailText.setText(ConfigManager.ConfigProperties.getProperty("emailText"));
        passwordText.setText(ConfigManager.ConfigProperties.getProperty("passwordText"));
        playButton.setText(ConfigManager.ConfigProperties.getProperty("playButton"));
        editUserButton.setText(ConfigManager.ConfigProperties.getProperty("editUserButton"));
        goBackButton.setText(ConfigManager.ConfigProperties.getProperty("goBackButton"));
    }

    private void cargarDatosUsuario() {
        UserModel currentUser = Session.getCurrentUser();
        if (currentUser != null) {
            userField.setText(currentUser.getUser());
            emailField.setText(currentUser.getEmail());
            passwordField.setText(currentUser.getPassword());
        }
    }

    /**
     * Metodo para cambiar a la pantalla gameView.
     * 
     * @param event
     */
    @FXML
    private void openGame(ActionEvent event) {
        cambiarPantalla(event, "/view/gameView.fxml");
    }

    /**
     * Metodo para cambiar a la pantalla editUserView.
     * 
     * @param event
     */
    @FXML
    private void openEditUser(ActionEvent event) {
        cambiarPantalla(event, "/view/editUserView.fxml");
    }

    /**
     * Metodo para cambiar a la pantalla anterior.
     * 
     * @param event
     */
    @FXML
    private void goBack(ActionEvent event) {
        cambiarPantalla(event, "/view/loginView.fxml");
    }
}
