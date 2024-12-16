package org.example.conjuntofxhibernate;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

import static org.example.conjuntofxhibernate.Principal.loadFXML;
import static org.example.conjuntofxhibernate.dao.ReportService.*;

public class Informes implements Initializable {

    @javafx.fxml.FXML
    private Button btnSalida;
    @javafx.fxml.FXML
    private Button verInformeMal;
    @javafx.fxml.FXML
    private Button informeMal;
    @javafx.fxml.FXML
    private ImageView adminImg;
    @javafx.fxml.FXML
    private Button verInformeMas;
    @javafx.fxml.FXML
    private Button informeMas;
    @javafx.fxml.FXML
    private Label dTitAplic;
    @javafx.fxml.FXML
    private Button informePelis;
    @javafx.fxml.FXML
    private ImageView salida;
    @javafx.fxml.FXML
    private Button verInformePelis;
    @javafx.fxml.FXML
    private Label avUsuario;
    @javafx.fxml.FXML
    private Button btnAtras;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        informePelis.setOnAction(event -> generadoPelis());
        verInformePelis.setOnAction(event -> mostrarInformePeliculas());

        informeMal.setOnAction(event -> generadoMal());
        verInformeMal.setOnAction(event -> mostrarInformeCopiasMal());

        informeMas.setOnAction(event -> generadoMasCopias());
        verInformeMas.setOnAction(event -> mostrarInformeMasCopias());

        btnAtras.setOnAction(event -> loadFXML("/org/example/conjuntofxhibernateResources/views/lista-view.fxml","PeliculonAss", 800, 500));

    }


    private void generadoPelis(){
        generarInformeInfoPeliculas();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Generación de informes");
        alert.setHeaderText("Se ha generado el informe correctamente");
        alert.setContentText("Puede encontrar el informe en la carpeta del proyecto en formato PDF");
        alert.getDialogPane().getScene().getStylesheets().add(getClass().getResource("/org/example/conjuntofxhibernateResources/style/estiloLog.css").toExternalForm());
        alert.showAndWait();
    }

    private  void generadoMal(){
        generarInformeCopiasMal();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Generación de informes");
        alert.setHeaderText("Se ha generado el informe correctamente");
        alert.setContentText("Puede encontrar el informe en la carpeta del proyecto en formato PDF");
        alert.getDialogPane().getScene().getStylesheets().add(getClass().getResource("/org/example/conjuntofxhibernateResources/style/estiloLog.css").toExternalForm());
        alert.showAndWait();
    }
    private void generadoMasCopias(){
        generarInformeMasCopias();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Generación de informes");
        alert.setHeaderText("Se ha generado el informe correctamente");
        alert.setContentText("Puede encontrar el informe en la carpeta del proyecto en formato PDF");
        alert.getDialogPane().getScene().getStylesheets().add(getClass().getResource("/org/example/conjuntofxhibernateResources/style/estiloLog.css").toExternalForm());
        alert.showAndWait();
    }

}
