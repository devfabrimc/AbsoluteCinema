package com.absolutecinema.controller.client;

import com.absolutecinema.application.App;
import com.absolutecinema.model.Movie;
import com.absolutecinema.utils.DurationFormatter;
import com.absolutecinema.utils.GenreFormatter;
import com.absolutecinema.utils.Paths;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class MovieDetailsController implements Initializable {

    @FXML private Label lblTitle;
    @FXML private Region imgCover;
    @FXML private Region imgBanner;
    @FXML private Label lblScore;
    @FXML private Label lblMeta;
    @FXML private Label lblSynopsis;
    @FXML private Accordion functionsAccordion;;

    public static Movie selectedMovieData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Movie movie = selectedMovieData;

        if (movie != null) {
            lblTitle.setText(movie.getTitle().toUpperCase());
            setImage(movie);
            lblSynopsis.setText(movie.getSynopsis());
            lblScore.setText(movie.getScore() + "/10");
            lblMeta.setText(GenreFormatter.format(movie.getGenre()) + " • " + movie.getRating() + " • " + DurationFormatter.format(movie.getDurationMinutes()));
        }
    }

    private void setImage(Movie movie) {
        String path="/com/absolutecinema/images/"+movie.getImagePath();

        URL url=getClass().getResource(path);

        if(url==null){
            System.err.println("No existe "+path);
            return;
        }

        imgCover.setStyle(
                "-fx-background-image:url('"+url.toExternalForm()+"');"+
                        "-fx-background-size: cover;"+
                        "-fx-background-position: center;"
        );

        Rectangle clip=new Rectangle();

        clip.widthProperty().bind(imgCover.widthProperty());

        clip.heightProperty().bind(imgCover.heightProperty());

        clip.setArcWidth(32);

        clip.setArcHeight(32);

        imgCover.setClip(clip);
    }

    @FXML
    void btnBackOnAction() {
        App.app.setScene(Paths.MENU_VIEW);
        App.app.setTitle(" | Menú Principal");
    }
}