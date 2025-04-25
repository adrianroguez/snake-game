package es.adrianroguez.controller;

import java.util.HashMap;
import java.util.Map;

import es.adrianroguez.config.ConfigManager;
import es.adrianroguez.controller.abstracts.AbstractController;
import es.adrianroguez.model.UserModel;
import es.adrianroguez.model.service.UserServiceModel;
import es.adrianroguez.session.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LoginController extends AbstractController {
    private final Map<String, String> idiomaCodigoMap = new HashMap<>();
    private final String pathFichero = "src/main/resources/";
    private final String idiomaString = "idioma-";

    @FXML
    Text loginTitle;

    @FXML
    ComboBox<String> languagesComboBox;

    @FXML
    Text userText;

    @FXML
    TextField userField;

    @FXML
    Text passwordText;

    @FXML
    TextField passwordField;

    @FXML
    Text messageText;

    @FXML
    Button loginButton;

    @FXML
    Button registerButton;

    @FXML
    Button rememberPasswordButton;

    @FXML
    public void initialize() {
        idiomaCodigoMap.put("Español", "es");
        idiomaCodigoMap.put("English", "en");
        idiomaCodigoMap.put("Français", "fr");
        languagesComboBox.getItems().addAll(idiomaCodigoMap.keySet());

        cargarIdioma("es");
        cambiarIdioma();
    }

    @FXML
    protected void seleccionarIdioma() {
        String idiomaSeleccionado = languagesComboBox.getValue();
        if (idiomaSeleccionado != null) {
            String codigoIdioma = idiomaCodigoMap.get(idiomaSeleccionado);
            cargarIdioma(codigoIdioma);
            cambiarIdioma();
        }
    }

    private void cargarIdioma(String idioma) {
        String pathCargarIdioma = pathFichero + idiomaString + idioma + ".properties";
        ConfigManager.ConfigProperties.setPath(pathCargarIdioma);
    }

    private void cambiarIdioma() {
        loginTitle.setText(ConfigManager.ConfigProperties.getProperty("loginTitle"));
        userText.setText(ConfigManager.ConfigProperties.getProperty("userText"));
        passwordText.setText(ConfigManager.ConfigProperties.getProperty("passwordText"));
        loginButton.setText(ConfigManager.ConfigProperties.getProperty("loginButton"));
        registerButton.setText(ConfigManager.ConfigProperties.getProperty("registerButton"));
        rememberPasswordButton.setText(ConfigManager.ConfigProperties.getProperty("rememberPasswordButton"));
    }

    @FXML
    protected void openUser(ActionEvent event) {
        String username = userField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            messageText.setText(ConfigManager.ConfigProperties.getProperty("loginErrorCamposVacios"));
            return;
        }

        UserServiceModel userService = new UserServiceModel();
        UserModel usuario = userService.obtenerUsuarioPorCredenciales(username, password);

        if (usuario != null) {
            Session.setCurrentUser(usuario);
            cambiarPantalla(event, "/view/userView.fxml");
        } else {
            messageText.setText(ConfigManager.ConfigProperties.getProperty("loginErrorCredenciales"));
        }
    }

    /**
     * Metodo para cambiar a la pantalla registerView.
     * 
     * @param event
     */
    @FXML
    private void openRegister(ActionEvent event) {
        cambiarPantalla(event, "/view/registerView.fxml");
    }

    /**
     * Metodo para cambiar a la pantalla rememberPasswordView.
     * 
     * @param event
     */
    @FXML
    private void openRememberPassword(ActionEvent event) {
        cambiarPantalla(event, "/view/rememberPasswordView.fxml");
    }
}
