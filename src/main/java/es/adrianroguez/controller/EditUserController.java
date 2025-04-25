package es.adrianroguez.controller;

import es.adrianroguez.config.ConfigManager;
import es.adrianroguez.controller.abstracts.AbstractController;
import es.adrianroguez.model.UserModel;
import es.adrianroguez.model.service.UserServiceModel;
import es.adrianroguez.session.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class EditUserController extends AbstractController {
    private final UserServiceModel userService = new UserServiceModel();

    @FXML
    Text editUserTitle;

    @FXML
    Text userText;

    @FXML
    TextField userField;

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
    Button updateUserButton;

    @FXML
    Button goBackButton;

    @FXML
    public void initialize() {
        cambiarIdioma();
        cargarDatosUsuario();
    }

    @FXML
    protected void cambiarIdioma() {
        editUserTitle.setText(ConfigManager.ConfigProperties.getProperty("editUserTitle"));
        userText.setText(ConfigManager.ConfigProperties.getProperty("userText"));
        passwordText.setText(ConfigManager.ConfigProperties.getProperty("passwordText"));
        passwordText.setText(ConfigManager.ConfigProperties.getProperty("passwordText"));
        passwordRepitText.setText(ConfigManager.ConfigProperties.getProperty("passwordRepitText"));
        updateUserButton.setText(ConfigManager.ConfigProperties.getProperty("updateUserButton"));
        goBackButton.setText(ConfigManager.ConfigProperties.getProperty("goBackButton"));
    }

    private void cargarDatosUsuario() {
        UserModel usuario = Session.getCurrentUser();
        if (usuario != null) {
            userField.setText(usuario.getUser());
            passwordField.setText(usuario.getPassword());
            passwordRepitField.setText(usuario.getPassword());
        }
    }

    @FXML
    private void updateUser() {
        String nuevoUser = userField.getText();
        String nuevaPassword = passwordField.getText();
        String repetirPassword = passwordRepitField.getText();

        if (nuevoUser == null || nuevoUser.trim().isEmpty() ||
                nuevaPassword == null || nuevaPassword.trim().isEmpty() ||
                repetirPassword == null || repetirPassword.trim().isEmpty()) {
            messageText.setText("Todos los campos deben estar completos.");
            return;
        }

        if (!nuevaPassword.equals(repetirPassword)) {
            messageText.setText("Las contraseñas no coinciden.");
            return;
        }

        UserModel usuario = Session.getCurrentUser();
        if (usuario == null) {
            messageText.setText("No hay sesión activa.");
            return;
        }

        usuario.setUser(nuevoUser);
        usuario.setPassword(nuevaPassword);

        boolean actualizado = userService.actualizarUsuario(usuario);
        if (actualizado) {
            Session.setCurrentUser(usuario);
            goBack(new ActionEvent(updateUserButton, null));
        } else {
            messageText.setText("Error al actualizar el usuario.");
        }
    }

    @FXML
    private void mostrarMensaje(String msg) {
        if (messageText != null) {
            messageText.setText(msg);
        } else {
            System.out.println(msg);
        }
    }

    /**
     * Metodo para cambiar a la pantalla anterior.
     * 
     * @param event
     */
    @FXML
    private void goBack(ActionEvent event) {
        cambiarPantalla(event, "/view/userView.fxml");
    }
}
