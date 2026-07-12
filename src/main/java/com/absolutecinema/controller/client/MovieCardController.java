package com.absolutecinema.controller.client;

import com.absolutecinema.model.Genre;
import com.absolutecinema.model.Movie;
import com.absolutecinema.model.MovieStatus;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;

import java.io.InputStream;
import java.net.URL;

public class MovieCardController {

    @FXML private HBox hbxFeaturedMovieStars;

    @FXML private Region featuredImage;

    @FXML private Label lblRatingNumerico;

    @FXML private Label lblTitulo;

    @FXML private Label lblGenero;

    @FXML private Label lblDay;

    @FXML private Label lblMonth;

    public void setMovie(Movie movie) {

        cargarTitulo(movie);
        cargarPortada(movie);
        cargarGenero(movie.getGenre());

        if (movie.getStatus() == MovieStatus.NOW_SHOWING) {
            mostrarCalificacion(movie.getScore());
        } else {
            mostrarProximamente();
        }
    }

    private void cargarTitulo(Movie movie) {
        lblTitulo.setText(movie.getTitle());
    }

    private void cargarGenero(Genre genre) {

        if (genre == null) {
            lblGenero.setText("");
            return;
        }

        String texto = switch (genre) {
            case TODOS -> "Todos";
            case ACCION -> "Acción";
            case ANIMACION -> "Animación";
            case AVENTURA -> "Aventura";
            case BIOGRAFIA -> "Biografía";
            case CIENCIA_FICCION -> "Ciencia ficción";
            case COMEDIA -> "Comedia";
            case CRIMEN -> "Crimen";
            case DRAMA -> "Drama";
            case FANTASIA -> "Fantasía";
            case HISTORIA -> "Historia";
            case HORROR -> "Horror";
            case ROMANCE -> "Romance";
            case SUSPENSO -> "Suspenso";
            case TERROR -> "Terror";
        };

        lblGenero.setText(texto);
    }

    private void mostrarCalificacion(double score) {

        if (hbxFeaturedMovieStars != null) {
            hbxFeaturedMovieStars.setVisible(true);
            hbxFeaturedMovieStars.setManaged(true);
        }

        if (lblDay != null) {
            lblDay.setVisible(false);
            lblDay.setManaged(false);
        }

        if (lblMonth != null) {
            lblMonth.setVisible(false);
            lblMonth.setManaged(false);
        }

        pintarEstrellas(score);
    }

    private void mostrarProximamente() {

        if (hbxFeaturedMovieStars != null) {
            hbxFeaturedMovieStars.setVisible(false);
            hbxFeaturedMovieStars.setManaged(false);
        }

        if (lblDay != null) {
            lblDay.setVisible(false);
            lblDay.setManaged(false);
        }

        if (lblMonth != null) {
            lblMonth.setVisible(false);
            lblMonth.setManaged(false);
        }
    }

    private void cargarPortada(Movie movie) {

        String ruta = "/com/absolutecinema/images/" + movie.getImagePath();

        URL url = getClass().getResource(ruta);

        if (url == null) {
            System.err.println("No existe la imagen: " + ruta);
            return;
        }

        featuredImage.setStyle(
                "-fx-background-image: url('" + url.toExternalForm() + "');" +
                        "-fx-background-size: cover;" +
                        "-fx-background-position: center;" +
                        "-fx-background-radius: 16;"
        );

        Rectangle clip = new Rectangle();

        clip.widthProperty().bind(featuredImage.widthProperty());
        clip.heightProperty().bind(featuredImage.heightProperty());

        clip.setArcWidth(32);
        clip.setArcHeight(32);

        featuredImage.setClip(clip);
    }

    private void pintarEstrellas(double score) {

        hbxFeaturedMovieStars.getChildren().removeIf(node -> node instanceof ImageView);

        double nota = score / 2.0;

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
}