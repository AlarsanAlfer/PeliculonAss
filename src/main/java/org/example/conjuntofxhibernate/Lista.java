package org.example.conjuntofxhibernate;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import org.example.conjuntofxhibernate.dao.CopiaDAO;
import org.example.conjuntofxhibernate.dao.PeliculaDAO;
import org.example.conjuntofxhibernate.dao.UsuarioDAO;
import org.example.conjuntofxhibernate.models.Copia;
import org.example.conjuntofxhibernate.models.Pelicula;
import org.example.conjuntofxhibernate.models.Sesion;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static org.example.conjuntofxhibernate.Principal.loadFXML;
import static org.example.conjuntofxhibernate.dao.ReportService.generarInformeInfoPeliculas;

public class Lista implements Initializable {
    @javafx.fxml.FXML
    private Label avUsuario;
    @javafx.fxml.FXML
    private TableView dtable;
    @javafx.fxml.FXML
    private Button añadirCbtn;
    @javafx.fxml.FXML
    private Button añadirPbtn;
    @javafx.fxml.FXML
    private TableColumn dtableTitulo;
    @javafx.fxml.FXML
    private TableColumn dtableEstado;
    @javafx.fxml.FXML
    private TableColumn dtableSoporte;
    @javafx.fxml.FXML
    private Button deliminarbtn;
    @javafx.fxml.FXML
    private Button modificarBtn;

    private UsuarioDAO usuarioDAO;
    private PeliculaDAO peliculaDAO;
    private CopiaDAO copiaDAO;
    @javafx.fxml.FXML
    private Label dTitAplic;
    @javafx.fxml.FXML
    private VBox idTuto1;
    @javafx.fxml.FXML
    private Button idBtnTutorial;
    @javafx.fxml.FXML
    private Label idTextTutorial;
    @javafx.fxml.FXML
    private HBox idEncabezado;
    @javafx.fxml.FXML
    private HBox idtablacion;
    @javafx.fxml.FXML
    private HBox idbotoness;
    private int tutorialStep = 0;
    @FXML
    private Pane idPanelTutorial;
    @FXML
    private ImageView salida;
    @FXML
    private Button btnSalida;
    @FXML
    private ImageView adminImg;
    private MediaPlayer mp;
    @FXML
    private Button btnInforme;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (Sesion.usuarioIniciado.getIsAdmin()) {
            añadirPbtn.setVisible(true);
            añadirPbtn.setDisable(false);
            adminImg.setVisible(true);

        } else {
            añadirPbtn.setVisible(false);
            adminImg.setVisible(false);
        }

        if(Sesion.tutorialPasado){
            idTuto1.disableProperty().set(true);
            idTuto1.setVisible(false);
            idPanelTutorial.disableProperty().set(true);
            idPanelTutorial.setVisible(false);
        }

        System.out.println(Sesion.usuarioIniciado.toString());
        CopiaDAO copiaDAO = new CopiaDAO(HibernateUtil.getSessionFactory());
        dtableTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        dtableEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        dtableSoporte.setCellValueFactory(new PropertyValueFactory<>("soporte"));

        avUsuario.setText("Bienvenido, " + Sesion.usuarioIniciado.getNombre());
        List<Copia> copiasUsuario = copiaDAO.copiasUsuario(Sesion.usuarioIniciado.getId());
        dtable.setItems(FXCollections.observableArrayList(copiasUsuario));

        deliminarbtn.setOnAction(event -> eliminarPeli());
        añadirCbtn.setOnAction(event -> añadirCopia());
        añadirPbtn.setOnAction(event -> {
            try {
                loadFXML("/org/example/conjuntofxhibernateResources/views/nuevaP-view.fxml", "Pantalla principal", 920, 700);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        modificarBtn.setOnAction(event -> modificarCopia());
        dtable.setRowFactory(tv -> {
            TableRow<Copia> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Sesion.copiaSeleccionada = row.getItem();
                    loadFXML("/org/example/conjuntofxhibernateResources/views/detalles-view.fxml", "Pantalla principal", 940, 740);
                }
            });
            return row;
        });
        idBtnTutorial.setOnAction(event -> avanzarTuto());

        Image normalImage = new Image(getClass().getResource("/org/example/conjuntofxhibernateResources/media/salida.png").toExternalForm());
        Image hoverImage = new Image(getClass().getResource("/org/example/conjuntofxhibernateResources/media/fart.png").toExternalForm());
        salida.setImage(normalImage);

        btnSalida.setOnMouseEntered(event -> salida.setImage(hoverImage));
        btnSalida.setOnMouseExited(event -> salida.setImage(normalImage));
        btnSalida.setOnAction(event -> cerrarSesion());
        btnInforme.setOnAction(event -> loadFXML("/org/example/conjuntofxhibernateResources/views/informes-view.fxml","PeliculonAss", 800, 500));

    }

    private void cerrarSesion() {
        Media m = new Media(getClass().getResource("/org/example/conjuntofxhibernateResources/media/sonidos/CerrarSesion.mp3").toExternalForm());
        mp = new MediaPlayer(m);
        mp.setVolume(0.1);
        mp.play();
        loadFXML("/org/example/conjuntofxhibernateResources/views/login-view.fxml","PeliculonAss", 800, 500);
    }

    @FXML
    private void avanzarTuto() {
        idEncabezado.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        idtablacion.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        idbotoness.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
        switch (tutorialStep) {
            case 0:
                idEncabezado.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
                idTextTutorial.setText("Este es el Encabezado. Aquí puedes ver el título de la aplicación, tu nombre de usuario y el botón para tirarte un pedo y cerrar sesión.\t Si tienes permisos de administrador, verás un icóno identificativo a la derecha de tu nombre.");
                break;
            case 1:
                idtablacion.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
                idTextTutorial.setText("Esta es la tabla donde puedes ver las copias pertenecientes y asociadas a tu usuario");
                break;
            case 2:
                idbotoness.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
                idTextTutorial.setText("Aquí están los botones de acción para añadir, eliminar o modificar una copia. En caso de ser administrador, también podrás añadir una nueva película a la base de datos.");
                break;
            case 3:
                idTextTutorial.setText("¡Tutorial completado! Pues ala, ¡a disfrutar de la aplicación!.");

            default:
                idTuto1.disableProperty().set(true);
                idTuto1.setVisible(false);
                idPanelTutorial.disableProperty().set(true);
                idPanelTutorial.setVisible(false);
                Sesion.tutorialPasado = true;
                break;
        }
        tutorialStep++;
    }


    private void refrescar(TableView tabla){
        CopiaDAO copiaDAO = new CopiaDAO(HibernateUtil.getSessionFactory());
        tabla.getItems().clear();
        List<Copia> copiasUsuario = copiaDAO.copiasUsuario(Sesion.usuarioIniciado.getId());
        tabla.setItems(FXCollections.observableArrayList(copiasUsuario));
    }

    private void modificarCopia(){
        CopiaDAO copiaDAO = new CopiaDAO(HibernateUtil.getSessionFactory());
        PeliculaDAO peliculaDAO = new PeliculaDAO(HibernateUtil.getSessionFactory());
        Copia seleccionada = (Copia) dtable.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Modificar Copia");
            dialog.setHeaderText("Modificando "+seleccionada.getTitulo());

            ChoiceBox<String> opcionesEstado = new ChoiceBox<>();
            opcionesEstado.getItems().addAll( "Bueno", "Dañado");
            opcionesEstado.setValue(seleccionada.getEstado());

            ChoiceBox<String> opcionesSoporte = new ChoiceBox<>();
            opcionesSoporte.getItems().addAll("DVD", "Blu-ray");
            opcionesSoporte.setValue(seleccionada.getSoporte());

            VBox dialogContent = new VBox(10);
            dialogContent.getChildren().addAll(
                    new Label("Estado:"), opcionesEstado,
                    new Label("Soporte:"), opcionesSoporte
            );
            dialog.getDialogPane().setContent(dialogContent);
//            dialog.getDialogPane().setStyle("-fx-background-color: #ffecb4;" +
//                    "-fx-border-color: rgb(255,166,0);" +
//                    "");
            dialog.getDialogPane().getStylesheets().add(
                    getClass().getResource("/org/example/conjuntofxhibernateResources/style/estiloLog.css").toExternalForm()
            );

            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> decision = dialog.showAndWait();

            if (decision.isPresent() && decision.get() == ButtonType.OK){
                seleccionada.setEstado(opcionesEstado.getValue());
                seleccionada.setSoporte(opcionesSoporte.getValue());
                copiaDAO.update(seleccionada);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Copia actualizada correctamente.");
                alert.setContentText("");
                alert.getDialogPane().getScene().getStylesheets().add(getClass().getResource("/org/example/conjuntofxhibernateResources/style/estiloLog.css").toExternalForm());

                Media m = new Media(getClass().getResource("/org/example/conjuntofxhibernateResources/media/sonidos/modificar.mp3").toExternalForm());
                mp = new MediaPlayer(m);
                mp.setVolume(0.6);
                mp.play();

                alert.showAndWait();
                refrescar(dtable);
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Confirmar eliminación:");
            alert.setHeaderText("No se ha seleccionado una pelicula.");
            alert.setContentText("Por favor, selecciona una copia para modificar.");
            alert.getDialogPane().getScene().getStylesheets().add(getClass().getResource("/org/example/conjuntofxhibernateResources/style/estiloLog.css").toExternalForm());
            alert.showAndWait();
        }

    }

    private void eliminarPeli(){
        CopiaDAO copiaDAO = new CopiaDAO(HibernateUtil.getSessionFactory());
        Copia seleccionada = (Copia) dtable.getSelectionModel().getSelectedItem();
        System.out.println(seleccionada.toString());
        if(seleccionada != null){
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Confirmar eliminación:");
            alerta.setHeaderText("¿Estás seguro de que deseas eliminar la copia seleccionada?");
            alerta.setContentText("Copia a eliminar: " + seleccionada.getTitulo());
            alerta.getDialogPane().getScene().getStylesheets().add(getClass().getResource("/org/example/conjuntofxhibernateResources/style/estiloLog.css").toExternalForm());
            Optional<ButtonType> result = alerta.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Media m = new Media(getClass().getResource("/org/example/conjuntofxhibernateResources/media/sonidos/borrar.mp3").toExternalForm());
                mp = new MediaPlayer(m);
                mp.setVolume(0.4);
                mp.play();
                copiaDAO.delete(seleccionada);
                dtable.getItems().remove(seleccionada);
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Ninguna copia seleccionada");
            alert.setHeaderText("No se ha seleccionado ninguna copia para eliminar");
            alert.setContentText("Por favor, selecciona una copia en la tabla y vuelve a intentarlo.");
            alert.getDialogPane().getScene().getStylesheets().add(getClass().getResource("/org/example/conjuntofxhibernateResources/style/estiloLog.css").toExternalForm());

            alert.showAndWait();
        }
    }

    private void añadirCopia(){
        CopiaDAO copiaDAO = new CopiaDAO(HibernateUtil.getSessionFactory());
        PeliculaDAO peliculaDAO = new PeliculaDAO(HibernateUtil.getSessionFactory());

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Añadir Nueva Copia");
        dialog.setHeaderText("Introduce los detalles de la copia");

        ChoiceBox<String> opcionesPelis = new ChoiceBox<>();
        for(String titulo : peliculaDAO.findNames()){
            opcionesPelis.getItems().add(titulo);
        }

        ChoiceBox<String> opcionesEstado = new ChoiceBox<>();
        opcionesEstado.getItems().addAll( "Bueno", "Dañado");
        opcionesEstado.setValue("Bueno");

        ChoiceBox<String> opcionesSoporte = new ChoiceBox<>();
        opcionesSoporte.getItems().addAll("DVD", "Blu-ray");
        opcionesSoporte.setValue("DVD");

        VBox dialogContent = new VBox(10);
        dialogContent.getChildren().addAll(
                new Label("Titulo:"), opcionesPelis,
                new Label("Estado:"), opcionesEstado,
                new Label("Soporte:"), opcionesSoporte
        );
        dialog.getDialogPane().setContent(dialogContent);
        dialog.getDialogPane().getStylesheets().add(
                getClass().getResource("/org/example/conjuntofxhibernateResources/style/estiloLog.css").toExternalForm()
        );
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> decision = dialog.showAndWait();

        if (decision.isPresent() && decision.get() == ButtonType.OK) {
            String tituloSelec = opcionesPelis.getSelectionModel().getSelectedItem();
            Pelicula pelicula = peliculaDAO.findByTitle(tituloSelec);

            Copia nuevaCopia = new Copia();
            nuevaCopia.setId_pelicula(pelicula.getId());
            nuevaCopia.setId_usuario(Sesion.usuarioIniciado.getId());
            nuevaCopia.setEstado(opcionesEstado.getValue());
            nuevaCopia.setSoporte(opcionesSoporte.getValue());
            nuevaCopia.setTitulo(tituloSelec);

            Media m = new Media(getClass().getResource("/org/example/conjuntofxhibernateResources/media/sonidos/añadir.mp3").toExternalForm());
            mp = new MediaPlayer(m);
            mp.setVolume(0.7);
            mp.play();

            copiaDAO.save(nuevaCopia);
            dtable.getItems().add(nuevaCopia);

        }
    }
//    private void loadVentanaModal(){
//
//        stage.initModality(Modality.APPLICATION_MODAL);
//    }

}
