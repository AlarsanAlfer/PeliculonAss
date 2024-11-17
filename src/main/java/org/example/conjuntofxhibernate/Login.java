package org.example.conjuntofxhibernate;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import org.example.conjuntofxhibernate.dao.UsuarioDAO;
import org.example.conjuntofxhibernate.models.Sesion;
import org.example.conjuntofxhibernate.models.Usuario;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class Login {
    @FXML
    private TextField duser;
    @FXML
    private Button diniciar;
    private MediaPlayer mp;
    private UsuarioDAO usuarioDAO;
    @FXML
    private PasswordField dpassword;
    @FXML
    private CheckBox dRecordar;
    @FXML
    private ImageView idportada;
    @FXML
    private Label dTituloLog;
    @FXML
    private Label titApp;
    @FXML
    private Button olvidaPass;

    private List<Image> images;
    private int indiceImg = 0;
    private Timeline timeline;
    @FXML
    private ImageView idportada2;
    @FXML
    private StackPane idContenedorImg;

    public Login() {
        SessionFactory session = HibernateUtil.getSessionFactory();
        this.usuarioDAO = new UsuarioDAO(session);
    }

    @FXML
    private void initialize() {
        diniciar.setOnAction(event -> iniciarSession());

        loadImages();
        if (!images.isEmpty()) {
            idportada.setImage(images.get(indiceImg));
            idportada.setOpacity(1);
            idportada2.setOpacity(0);
            FadeTransition initialFade = new FadeTransition(Duration.seconds(1), idportada);
            initialFade.setFromValue(0.0);
            initialFade.setToValue(1.0);
            initialFade.play();
        }
        timelap();
    }

    @FXML
    private void iniciarSession() {
        String usuario = duser.getText();
        String password = dpassword.getText();
        Usuario logUsuario = usuarioDAO.validar(usuario, password);

        if (logUsuario != null) {
//            Media m = new Media(getClass().getResource("/org/example/conjuntofxhibernateResources/media/main.mp3").toExternalForm());
//            mp = new MediaPlayer(m);
//            mp.play();
            Sesion.usuarioIniciado = logUsuario;
            Principal.loadFXML("/org/example/conjuntofxhibernateResources/views/lista-view.fxml", "Pantalla principal", 900, 720);

            if (timeline != null) {
                timeline.stop();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de autenticación");
            alert.setHeaderText("Usuario o contraseña incorrectos");
            alert.setContentText("Por favor, verifica tus credenciales e intenta nuevamente.");
            alert.getDialogPane().getScene().getStylesheets().add(getClass().getResource("/org/example/conjuntofxhibernateResources/style/estiloLog.css").toExternalForm());
            alert.showAndWait();
        }
    }

    private void loadImages() {
        images = new ArrayList<>();
        images.add(new Image(getClass().getResource("/org/example/conjuntofxhibernateResources/media/aayLuigiPillin.jpg").toExternalForm()));
        images.add(new Image(getClass().getResource("/org/example/conjuntofxhibernateResources/media/winnie.jpg").toExternalForm()));
        images.add(new Image(getClass().getResource("/org/example/conjuntofxhibernateResources/media/miraeldonrramon.jpg").toExternalForm()));
        images.add(new Image(getClass().getResource("/org/example/conjuntofxhibernateResources/media/otrisimo.jpg").toExternalForm()));
        images.add(new Image(getClass().getResource("/org/example/conjuntofxhibernateResources/media/9563801.jpg").toExternalForm()));
    }


    private void timelap() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(4), event -> cambiarImg()));
        //timeline.setCycleCount(images.size());
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


    private void cambiarImg() {
        indiceImg++;
        if (indiceImg < images.size()) {
            ImageView imgVisible = (idportada.getOpacity() == 1) ? idportada : idportada2;
            ImageView imgOculta = (imgVisible == idportada) ? idportada2 : idportada;

            imgOculta.setImage(images.get(indiceImg));

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), imgVisible);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), imgOculta);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeOut.setOnFinished(event -> fadeIn.play());
            fadeOut.play();
        } else {
            timeline.stop();
        }
    }

}
