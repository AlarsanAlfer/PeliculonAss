package org.example.conjuntofxhibernate;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.example.conjuntofxhibernate.dao.PeliculaDAO;
import org.example.conjuntofxhibernate.dao.UsuarioDAO;
import org.example.conjuntofxhibernate.models.Pelicula;
import org.example.conjuntofxhibernate.models.Sesion;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

import static org.example.conjuntofxhibernate.Principal.loadFXML;

public class NuevaP implements Initializable {
    @javafx.fxml.FXML
    private TextField dtitulo;
    @javafx.fxml.FXML
    private ComboBox dgenero;
    @javafx.fxml.FXML
    private TextField ddirector;
    @javafx.fxml.FXML
    private Spinner daño;
    @javafx.fxml.FXML
    private Button atrassbtn;
    @javafx.fxml.FXML
    private Button guardarbtn;
    @javafx.fxml.FXML
    private TextArea ddescripcion;
    @javafx.fxml.FXML
    private Label avUsuario;
    private PeliculaDAO peliculaDAO;
    private UsuarioDAO usuarioDAO;
    @javafx.fxml.FXML
    private Label dTitAplic;
    @javafx.fxml.FXML
    private Label dsemitit;
    @javafx.fxml.FXML
    private Label dsemisubtit;
    @javafx.fxml.FXML
    private ImageView salida;
    @javafx.fxml.FXML
    private Button btnSalida;
    @javafx.fxml.FXML
    private ImageView adminImg;
    private MediaPlayer mp;
    @javafx.fxml.FXML
    private Button idPortada;
    private String nombrePortada;
    @javafx.fxml.FXML
    private ImageView idVerPortada;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (Sesion.usuarioIniciado.getIsAdmin()) {
            adminImg.setDisable(false);
        }

        avUsuario.setText("Bienvenido, " + Sesion.usuarioIniciado.getNombre());
        Image normalImage = new Image(getClass().getResource("/org/example/conjuntofxhibernateResources/media/salida.png").toExternalForm());
        Image hoverImage = new Image(getClass().getResource("/org/example/conjuntofxhibernateResources/media/fart.png").toExternalForm());

        salida.setImage(normalImage);

        btnSalida.setOnMouseEntered(event -> salida.setImage(hoverImage));
        btnSalida.setOnMouseExited(event -> salida.setImage(normalImage));
        btnSalida.setOnAction(event -> cerrarSesion());

        dgenero.getItems().addAll( "Sci-Fi", "Acción");
        daño.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1995, 2024,1995, 1));

        guardarbtn.setOnAction(e -> {
            try {
                guardar();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        atrassbtn.setOnAction(e -> {
            Principal.loadFXML("/org/example/conjuntofxhibernateResources/views/lista-view.fxml", "Pantalla principal", 900, 720);
        });
        idPortada.setOnAction(event -> seleccionarImagen());
    }

    private void seleccionarImagen(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Portada");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg")
        );

        File archivoSeleccionado = fileChooser.showOpenDialog(null);
        if (archivoSeleccionado != null) {
            try {
                nombrePortada = archivoSeleccionado.getName();
                Path destino = Paths.get("src/main/resources/org/example/conjuntofxhibernateResources/media/portadas/" + nombrePortada);
                Files.copy(archivoSeleccionado.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);
                Image imagen = new Image(destino.toUri().toString());
                idVerPortada.setImage(imagen);
            }catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    private void cerrarSesion() {
        Media m = new Media(getClass().getResource("/org/example/conjuntofxhibernateResources/media/sonidos/CerrarSesion.mp3").toExternalForm());
        mp = new MediaPlayer(m);
        mp.setVolume(0.25);
        mp.play();
        loadFXML("/org/example/conjuntofxhibernateResources/views/login-view.fxml","PeliculonAss", 800, 500);
    }

    private void guardar() throws IOException {
        String titulo = dtitulo.getText();
        String genero = (String) dgenero.getValue();
        String director = ddirector.getText();
        Integer anio = (Integer) daño.getValue();
        String descripcion = ddescripcion.getText();
        String portada = nombrePortada;
        Pelicula nuevap = new Pelicula(titulo, anio, genero, descripcion, director, portada);

        PeliculaDAO peliculaDAO = new PeliculaDAO((HibernateUtil.getSessionFactory()));
        peliculaDAO.save(nuevap);

        Principal.loadFXML("/org/example/conjuntofxhibernateResources/views/lista-view.fxml", "Pantalla principal", 900, 720);
    }


}
