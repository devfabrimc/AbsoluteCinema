package com.absolutecinema.controller.client;

import com.absolutecinema.application.App;
import com.absolutecinema.model.Movie;
import com.absolutecinema.model.Showtime;
import com.absolutecinema.repository.MovieRepository;
import com.absolutecinema.repository.RoomRepository;
import com.absolutecinema.repository.ShowtimeRepository;
import com.absolutecinema.service.ShowtimeService;
import com.absolutecinema.utils.DurationFormatter;
import com.absolutecinema.utils.GenreFormatter;
import com.absolutecinema.utils.Paths;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

    private MovieRepository movieRepository;
    private RoomRepository roomRepository;
    private ShowtimeRepository showtimeRepository;
    private ShowtimeService showtimeService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Movie movie = selectedMovieData;

        if (movie != null) {
            lblTitle.setText(movie.getTitle().toUpperCase());
            setImage(movie);
            lblSynopsis.setText(movie.getSynopsis());
            lblScore.setText(movie.getScore() + "/10");
            lblMeta.setText(GenreFormatter.format(movie.getGenre()) + "  •  " + movie.getRating() + "  •  " + DurationFormatter.format(movie.getDurationMinutes()));
        }
        movieRepository = new MovieRepository();
        roomRepository = new RoomRepository();
        showtimeRepository = new ShowtimeRepository();
        showtimeService = new ShowtimeService(movieRepository, roomRepository, showtimeRepository);

        loadShowTimes();
    }

    private TitledPane createTitledPane(String title) {
        TitledPane pane = new TitledPane();
        pane.setText(title);
        pane.setExpanded(true);
        return pane;
    }

    private void loadShowTimes() {
        // 1. Limpiamos el acordeón
        functionsAccordion.getPanes().clear();

        var showtimes = showtimeService.getShowtimeByMovie(selectedMovieData.getId());

        for (var showtime : showtimes) {
            String date = showtime.getDate();

            // 2. Buscamos si ya creamos un TitledPane para esta fecha
            TitledPane existingPane = findPaneByTitle(date);

            if (existingPane == null) {
                // Si no existe, lo creamos y lo añadimos al acordeón
                existingPane = createTitledPane(date);
                functionsAccordion.getPanes().add(existingPane);
            }

            // 3. Aquí deberías agregar el horario específico (ej. "14:30")
            // al contenido visual de ese "existingPane" (como un VBox o ListView interno)
            addShowtimeToPaneContent(existingPane, showtime);
        }
    }

    // Método auxiliar para buscar si ya existe el panel de esa fecha
    private TitledPane findPaneByTitle(String title) {
        for (TitledPane pane : functionsAccordion.getPanes()) {
            if (pane.getText().equals(title)) {
                return pane;
            }
        }
        return null;
    }

    private void addShowtimeToPaneContent(TitledPane pane, Showtime showtime) {
        VBox container;
        if (pane.getContent() instanceof VBox) {
            container = (VBox) pane.getContent();
        } else {
            container = new VBox();
            container.setSpacing(10);
            container.getStyleClass().add("showtime-container");
            pane.setContent(container);
        }

        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);
        row.setSpacing(10);
        row.getStyleClass().add("showtime-row");

        ImageView screenIcon = null;
        try{
            screenIcon = new ImageView(new Image(new FileInputStream(Paths.SCREEN_IMAGE)));
            screenIcon.setFitHeight(24);
            screenIcon.setFitWidth(24);
        } catch (FileNotFoundException e) {
            System.err.println("ERROR: No se encontró la ruta:  " + Paths.SCREEN_IMAGE);
        }
        screenIcon.getStyleClass().add("showtime-icon");

        String infoText = roomRepository.findById(showtime.getRoomId()).getName() + "  -  " + showtime.getTime();
        Label infoLabel = new Label(infoText);
        infoLabel.getStyleClass().add("showtime-info-label");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button buyButton = new Button();
        ImageView ticketButton = new ImageView();
        try {
            Image image = new Image(new FileInputStream(Paths.TICKET_IMAGE));
            ticketButton = new ImageView(image);
        }catch (FileNotFoundException e){
            System.err.println("Error: No se encontró la imagen.");
        }
        ticketButton.setFitHeight(25);
        ticketButton.setFitWidth(25);
        ticketButton.setPreserveRatio(true);
        buyButton.setGraphic(ticketButton);
        buyButton.setText("Comprar");
        buyButton.getStyleClass().add("buy-button");

        buyButton.setOnAction(e -> {
            handleBuyAction(showtime);
        });

        // 7. Armamos la fila y la agregamos al contenedor
        row.getChildren().addAll(screenIcon, infoLabel, spacer, buyButton);
        container.getChildren().add(row);
    }

    private void handleBuyAction(Showtime showtime) {
        System.out.println("Comprando para la función: " + showtime.getId());
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