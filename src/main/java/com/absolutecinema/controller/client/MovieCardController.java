package com.absolutecinema.controller.client;

import com.absolutecinema.application.App;
import com.absolutecinema.model.Genre;
import com.absolutecinema.model.Movie;
import com.absolutecinema.model.MovieStatus;
import com.absolutecinema.utils.GenreFormatter;
import com.absolutecinema.utils.Paths;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class MovieCardController {

    @FXML private HBox hbxFeaturedMovieStars;

    @FXML private Region featuredImage;

    @FXML private Label lblRatingNumerico;

    @FXML private Label lblTitulo;

    @FXML private Label lblGenero;

    @FXML private StackPane movieCard;

    public void setMovie(Movie movie) {
        setTitle(movie);
        setImage(movie);
        setGenre(movie);
        setScore(movie.getScore());
        movieCard.setOnMouseClicked(event -> {
            MovieDetailsController.selectedMovieData = movie;

            App.app.setTitle(" | " + movie.getTitle());
            App.app.setScene(Paths.MOVIE_DETAILS_VIEW);
        });
    }

    private void setTitle(Movie movie) {
        lblTitulo.setText(movie.getTitle().trim());
    }

    private void setGenre(Movie movie) {
        lblGenero.setText(GenreFormatter.format(movie.getGenre()));
    }

    private void setImage(Movie movie) {

        String path="/com/absolutecinema/images/"+movie.getImagePath();

        URL url=getClass().getResource(path);

        if(url==null){
            System.err.println("No existe "+path);
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

    private void setScore(double score) {

        hbxFeaturedMovieStars.getChildren().removeIf(node -> node instanceof ImageView);

        double points = score / 2.0;

        for (int i = 1; i <= 5; i++) {

            String path;

            if (points >= i) {
                path = "/com/absolutecinema/images/star-full.png";
            } else if (points >= i - 0.5) {
                path = "/com/absolutecinema/images/star-half.png";
            } else {
                path = "/com/absolutecinema/images/star-empty.png";
            }

            ImageView estrella = createStar(path);

            if (estrella != null) {

                int index = hbxFeaturedMovieStars.getChildren().size() - 1;

                if (index < 0) {
                    hbxFeaturedMovieStars.getChildren().add(estrella);
                } else {
                    hbxFeaturedMovieStars.getChildren().add(index, estrella);
                }
            }
        }

        lblRatingNumerico.setText(String.format("%.1f", points));
    }

    private ImageView createStar(String path) {

        InputStream stream = getClass().getResourceAsStream(path);

        if (stream == null) {
            System.err.println("No existe " + path);
            return null;
        }

        ImageView estrella = new ImageView(new Image(stream));

        estrella.setFitWidth(14);
        estrella.setFitHeight(14);
        estrella.setPreserveRatio(true);

        return estrella;
    }
}