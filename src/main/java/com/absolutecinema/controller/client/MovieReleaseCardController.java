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

    @FXML private Region featuredImage;

    @FXML private Label lblTitulo;

    @FXML private Label lblGenero;

    @FXML private Label lblScore;

    public void setMovie(Movie movie) {
        setTitle(movie);
        setGenre(movie);
        setImage(movie);
        setGrade(movie);
    }

    private void setTitle(Movie movie) {
        lblTitulo.setText(movie.getTitle().trim());
    }

    private void setGenre(Movie movie){
        lblGenero.setText(GenreFormatter.format(movie.getGenre()));
    }

    private void setGrade(Movie movie) {
        if(movie.getScore() == 0.0) {
            lblScore.setText("S/N");
        }else{
            lblScore.setText(Double.toString(movie.getScore()));
        }
    }

    private void setImage(Movie movie){

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