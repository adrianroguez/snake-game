package es.adrianroguez.controller.abstracts;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class AbstractController {
    /**
     * Metodo para cambiar de pantalla.
     * 
     * @param event
     * @param rutaFXML
     */
    protected void cambiarPantalla(ActionEvent event, String rutaFXML) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(rutaFXML));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene;

            if (rutaFXML.equals("/view/gameView.fxml")) {
                int width = 600;
                int height = 400;

                scene = new Scene(root, width, height);

                stage.setResizable(false);
            } else {
                scene = new Scene(root);
                stage.setResizable(true);
            }

            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
