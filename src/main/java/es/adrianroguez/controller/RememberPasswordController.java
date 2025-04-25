package es.adrianroguez.controller;

import es.adrianroguez.config.ConfigManager;
import es.adrianroguez.controller.abstracts.AbstractController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class RememberPasswordController extends AbstractController {
    @FXML
    Text rememberPasswordTitle;

    @FXML
    Text emailText;

    @FXML
    Button sendButton;

    @FXML
    Button goBackButton;

    @FXML
    public void initialize() {
        cambiarIdioma();
    }

    @FXML
    protected void cambiarIdioma() {
        rememberPasswordTitle.setText(ConfigManager.ConfigProperties.getProperty("rememberPasswordTitle"));
        emailText.setText(ConfigManager.ConfigProperties.getProperty("emailText"));
        sendButton.setText(ConfigManager.ConfigProperties.getProperty("sendButton"));
        goBackButton.setText(ConfigManager.ConfigProperties.getProperty("goBackButton"));
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
