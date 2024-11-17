package org.example.conjuntofxhibernate;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class Principal extends Application {
    private static Stage ventana;
    @Override
    public void start(Stage stage) throws IOException {
        Principal.ventana = stage;
        loadFXML("/org/example/conjuntofxhibernateResources/views/login-view.fxml","PeliculonAss", 800, 500);
        stage.show();
    }

    public static void loadFXML(String view, String title, Integer anchura, Integer altura) {
        FXMLLoader fxmlLoader = new FXMLLoader(Principal.class.getResource(view));
        Scene scene = null;

        try {
            scene = new Scene(fxmlLoader.load(), anchura, altura);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ventana.setTitle(title);
        ventana.setScene(scene);
        ventana.setResizable(false);

        Rectangle2D sb = Screen.getPrimary().getVisualBounds();
        ventana.setX((sb.getWidth() - anchura) / 2);
        ventana.setY((sb.getHeight() - altura) / 2);

        ventana.show();
    }

    public static void main(String[] args) {
        launch();
    }

}