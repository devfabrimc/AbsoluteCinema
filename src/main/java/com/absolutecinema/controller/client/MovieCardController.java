package com.absolutecinema.controller.client;

import com.absolutecinema.model.Movie;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class MovieCardController {

    @FXML private HBox hbxFeaturedMovieStars;

    @FXML private Region featuredImage;

    @FXML private Label lblRatingNumerico;

    @FXML private Label lblTitulo;

    @FXML private Label lblDay;

    @FXML private Label lblGenero;

    @FXML private Label lblMonth;

    public void setDatos(Movie pelicula) {
        cargarTitulo(pelicula);
        cargarPortada(pelicula);
        cargarGeneros(pelicula.getGenre());

        if (pelicula instanceof Movie) {
            if (hbxFeaturedMovieStars != null) {
                hbxFeaturedMovieStars.setVisible(true);
                hbxFeaturedMovieStars.setManaged(true);
                pintarEstrellas(((Movie) pelicula).getCalificacion());
            }

            if (lblDay != null) {
                lblDay.setVisible(false);
                lblDay.setManaged(false);
            }
            if (lblMonth != null) {
                lblMonth.setVisible(false);
                lblMonth.setManaged(false);
            }

        } else if (pelicula instanceof MovieRelease) {
            MovieRelease prox = (MovieRelease) pelicula;

            if (hbxFeaturedMovieStars != null) {
                hbxFeaturedMovieStars.setVisible(false);
                hbxFeaturedMovieStars.setManaged(false);
            }

            if (lblDay != null) {
                lblDay.setVisible(true);
                lblDay.setManaged(true);
                lblDay.setText(String.valueOf(prox.getDiaEstreno()));
            }
            if (lblMonth != null) {
                lblMonth.setVisible(true);
                lblMonth.setManaged(true);
                lblMonth.setText(prox.getMesEstreno());
            }
        }
    }

    private void cargarTitulo(Movie pelicula) {
        lblTitulo.setText(pelicula.getTitle());
    }

    private void cargarPortada(Movie pelicula) {
        String ruta = "/com/absolutecinema/images/" + pelicula.getImagePath();
        URL url = getClass().getResource(ruta);

        if (url == null) {
            System.err.println("No existe la imagen: " + ruta);
            return;
        }

        featuredImage.setStyle(
                "-fx-background-image: url(\"" + url.toExternalForm() + "\"); " +
                        "-fx-background-size: cover; " +
                        "-fx-background-radius: 16 16 16 16; "
        );

        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(featuredImage.widthProperty());
        clip.heightProperty().bind(featuredImage.heightProperty());

        clip.setArcWidth(31);
        clip.setArcHeight(31);

        featuredImage.setClip(clip);
    }

    private void pintarEstrellas(double calificacion) {
        hbxFeaturedMovieStars.getChildren().removeIf(node -> node instanceof ImageView);

        double nota = calificacion / 2;

        for (int i = 1; i <= 5; i++) {
            String ruta;
            if (nota >= i) {
                ruta = "/com/absolutecinema/images/star-full.png";
            } else if (nota >= i - 0.5) {
                ruta = "/com/absolutecinema/images/star-half.png";
            } else {
                ruta = "/com/absolutecinema/images/star-empty.png";
            }

            ImageView estrella = crearEstrella(ruta);

            if (estrella != null) {
                int index = hbxFeaturedMovieStars.getChildren().size() - 1;
                if (index < 0) {
                    hbxFeaturedMovieStars.getChildren().add(estrella);
                } else {
                    hbxFeaturedMovieStars.getChildren().add(index, estrella);
                }
            }
        }

        lblRatingNumerico.setText(String.format("%.1f", nota));
    }

    private ImageView crearEstrella(String ruta) {
        InputStream stream = getClass().getResourceAsStream(ruta);

        if (stream == null) {
            System.err.println("No existe " + ruta);
            return null;
        }

        ImageView estrella = new ImageView(new Image(stream));
        estrella.setFitWidth(14);
        estrella.setFitHeight(14);
        estrella.setPreserveRatio(true);
        return estrella;
    }

    private void cargarGeneros(List<String> generos) {
        if (generos == null || generos.isEmpty()) {
            lblGenero.setText("");
            return;
        }

        String textoGeneros = "";
        for (int i = 0; i < generos.size(); i++) {
            textoGeneros = textoGeneros + generos.get(i);

            if (i < generos.size() - 1) {
                textoGeneros = textoGeneros + " • ";
            }
        }

        lblGenero.setText(textoGeneros);
    }
}