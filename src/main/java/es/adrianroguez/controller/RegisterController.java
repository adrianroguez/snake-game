package es.adrianroguez.controller;

import es.adrianroguez.config.ConfigManager;
import es.adrianroguez.controller.abstracts.AbstractController;
import es.adrianroguez.model.UserModel;
import es.adrianroguez.model.service.UserServiceModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class RegisterController extends AbstractController {
    private final UserServiceModel userService = new UserServiceModel();

    @FXML
    Text registerTitle;

    @FXML
    Text userText;

    @FXML
    TextField textFieldUsuario;

    @FXML
    Text emailText;

    @FXML
    TextField emailField;

    @FXML
    Text emailRepitText;

    @FXML
    TextField emailRepitField;

    @FXML
    Text passwordText;

    @FXML
    PasswordField passwordField;

    @FXML
    Text passwordRepitText;

    @FXML
    PasswordField passwordRepitField;

    @FXML
    Text messageText;

    @FXML
    Button registerButton;

    @FXML
    Button goBackButton;

    @FXML
    public void initialize() {
        cambiarIdioma();
    }

    @FXML
    protected void cambiarIdioma() {
        registerTitle.setText(ConfigManager.ConfigProperties.getProperty("registerTitle"));
        userText.setText(ConfigManager.ConfigProperties.getProperty("userText"));
        emailText.setText(ConfigManager.ConfigProperties.getProperty("emailText"));
        emailRepitText.setText(ConfigManager.ConfigProperties.getProperty("emailRepitText"));
        passwordText.setText(ConfigManager.ConfigProperties.getProperty("passwordText"));
        passwordRepitText.setText(ConfigManager.ConfigProperties.getProperty("passwordRepitText"));
        registerButton.setText(ConfigManager.ConfigProperties.getProperty("registerButton"));
        goBackButton.setText(ConfigManager.ConfigProperties.getProperty("goBackButton"));
    }

    @FXML
    private void registerUser(ActionEvent event) {
        String user = textFieldUsuario.getText().trim();
        String email = emailField.getText().trim();
        String emailRep = emailRepitField.getText().trim();
        String password = passwordField.getText();
        String passwordRep = passwordRepitField.getText();

        if (user.isEmpty() || email.isEmpty() || emailRep.isEmpty() || password.isEmpty() || passwordRep.isEmpty()) {
            messageText.setText("Todos los campos son obligatorios.");
            return;
        }

        if (!email.equals(emailRep)) {
            messageText.setText("Los correos no coinciden.");
            return;
        }

        if (!password.equals(passwordRep)) {
            messageText.setText("Las contraseñas no coinciden.");
            return;
        }

        if (userService.existeUsuario(email)) {
            messageText.setText("Ya existe un usuario con este email.");
            return;
        }
        UserModel nuevoUsuario = new UserModel(user, email, password);
        boolean registrado = userService.registrarUsuario(nuevoUsuario);

        if (registrado) {
            messageText.setText("Usuario registrado correctamente.");
            cambiarPantalla(event, "/view/loginView.fxml");
        } else {
            messageText.setText("Error al registrar el usuario.");
        }
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