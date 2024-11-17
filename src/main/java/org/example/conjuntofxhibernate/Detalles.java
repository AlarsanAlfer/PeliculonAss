package org.example.conjuntofxhibernate;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import org.example.conjuntofxhibernate.dao.PeliculaDAO;
import org.example.conjuntofxhibernate.models.Pelicula;
import org.example.conjuntofxhibernate.models.Sesion;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import static org.example.conjuntofxhibernate.Principal.loadFXML;


public class Detalles implements Initializable {

    @javafx.fxml.FXML
    private Button atrassbtn;
    @javafx.fxml.FXML
    private Label avUsuario;
    @javafx.fxml.FXML
    private Label dtitulo;
    @javafx.fxml.FXML
    private Label dgenero;
    @javafx.fxml.FXML
    private Label daño;
    @javafx.fxml.FXML
    private Label ddirector;
    @javafx.fxml.FXML
    private Label destado;
    @javafx.fxml.FXML
    private Label ddescripcion;
    @javafx.fxml.FXML
    private Label dsoporte;
    private PeliculaDAO peliculaDAO = new PeliculaDAO(HibernateUtil.getSessionFactory());
    @FXML
    private Label dTitAplic;
    @FXML
    private StackPane idContImgVideo;
    @FXML
    private ImageView idDetPort;
    @FXML
    private MediaView idDetVideo;
    @FXML
    private ImageView salida;
    @FXML
    private Button btnSalida;
    @FXML
    private ImageView adminImg;
    private MediaPlayer mp;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (Sesion.usuarioIniciado.getIsAdmin()) {
            adminImg.setVisible(true);
        }else{
            adminImg.setVisible(false);
        }

        avUsuario.setText("Bienvenido, " + Sesion.usuarioIniciado.getNombre());
        atrassbtn.setOnAction(e -> Principal.loadFXML("/org/example/conjuntofxhibernateResources/views/lista-view.fxml", "Pantalla principal", 900, 720));

        Image normalImage = new Image(getClass().getResource("/org/example/conjuntofxhibernateResources/media/salida.png").toExternalForm());
        Image hoverImage = new Image(getClass().getResource("/org/example/conjuntofxhibernateResources/media/fart.png").toExternalForm());

        salida.setImage(normalImage);

        btnSalida.setOnMouseEntered(event -> salida.setImage(hoverImage));
        btnSalida.setOnMouseExited(event -> salida.setImage(normalImage));
        btnSalida.setOnAction(event -> cerrarSesion());


        setCopia();
    };
    private void cerrarSesion() {
        Media m = new Media(getClass().getResource("/org/example/conjuntofxhibernateResources/media/sonidos/CerrarSesion.mp3").toExternalForm());
        mp = new MediaPlayer(m);
        mp.setVolume(0.4);
        mp.play();
        loadFXML("/org/example/conjuntofxhibernateResources/views/login-view.fxml","PeliculonAss", 800, 500);
    }

    public void setCopia() {
        dtitulo.setText(Sesion.copiaSeleccionada.getTitulo());
        destado.setText(Sesion.copiaSeleccionada.getEstado());
        dsoporte.setText(Sesion.copiaSeleccionada.getSoporte());

        Pelicula pelicula = peliculaDAO.findByTitle(Sesion.copiaSeleccionada.getTitulo());
        if (pelicula != null) {
            String url = "src/main/resources/org/example/conjuntofxhibernateResources/media/portadas/"+ pelicula.getPortada();
            File file = new File(url);

            if (pelicula.getId() == 6) {
                idDetPort.setVisible(false);
                idDetVideo.setVisible(true);
                Media media = new Media(file.toURI().toString());
                MediaPlayer mp = new MediaPlayer(media);
                idDetVideo.setMediaPlayer(mp);
                idDetVideo.setFitWidth(400);
                idDetVideo.setFitHeight(250);
                idDetVideo.setPreserveRatio(true);

                mp.setAutoPlay(true);
                mp.setCycleCount(MediaPlayer.INDEFINITE);
            }else {
                idDetPort.setVisible(true);
                idDetVideo.setVisible(false);

                Image image = new Image(file.toURI().toString());
                idDetPort.setImage(image);
            }
            ddirector.setText(pelicula.getDirector());
            daño.setText(String.valueOf(pelicula.getAnio()));
            ddescripcion.setText(pelicula.getDescripcion());
            dgenero.setText(pelicula.getGenero());

        }
    }

}





