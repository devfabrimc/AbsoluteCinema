package com.absolutecinema.controller.client;

import com.absolutecinema.model.Genre;
import com.absolutecinema.model.Movie;
import com.absolutecinema.utils.GenreFormatter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;

import java.net.URL;

public class MovieReleaseCardController {

    @FXML
    private Region featuredImage;

    @FXML
    private Label lblTitulo;

    @FXML
    private Label lblGenero;

    public void setMovie(Movie movie) {
        cargarTitulo(movie);
        lblGenero.setText(GenreFormatter.format(movie.getGenre()));
        cargarPortada(movie);
    }

    private void cargarTitulo(Movie movie) {
        lblTitulo.setText(movie.getTitle());
    }

    private String formatearGenero(Genre genre){

        return switch (genre){

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

    }

    private void cargarPortada(Movie movie){

        String ruta="/com/absolutecinema/images/"+movie.getImagePath();

        URL url=getClass().getResource(ruta);

        if(url==null){
            System.err.println("No existe "+ruta);
            return;
        }

        featuredImage.setStyle(
                "-fx-background-image:url('"+url.toExternalForm()+"');"+
                        "-fx-background-size:cover;"+
                        "-fx-background-position:center;"
        );

        Rectangle clip=new Rectangle();

        clip.widthProperty().bind(featuredImage.widthProperty());

        clip.heightProperty().bind(featuredImage.heightProperty());

        clip.setArcWidth(32);

        clip.setArcHeight(32);

        featuredImage.setClip(clip);

    }

}