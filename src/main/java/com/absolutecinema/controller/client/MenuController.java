package com.absolutecinema.controller.client;

import com.absolutecinema.model.Genre;
import com.absolutecinema.model.Movie;
import com.absolutecinema.repository.MovieRepository;
import com.absolutecinema.service.MovieService;
import com.absolutecinema.utils.GenreFormatter;
import com.absolutecinema.utils.Paths;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML private Button btnSearchFilters;
    @FXML private Button btnFeaturedShopTicket;
    @FXML private Button btnFeaturedViewDetails;
    @FXML private ComboBox<Genre> cmbGenres;
    @FXML private Region rgnFeaturedImage;
    @FXML private Label lblFeaturedTitle;
    @FXML private Label lblFeaturedMeta;
    @FXML private Label lblFeaturedSynopsis;
    @FXML private HBox hbxFeaturedMovieStars;
    @FXML private ScrollPane scrMain;
    @FXML private StackPane stkFeaturedImageSection;
    @FXML private Label lblScheduleSection;
    @FXML private Label lblNextReleaseSection;
    @FXML private Label lblNavHome;
    @FXML private Label lblNavSchedule;
    @FXML private Label lblNavNextRelease;
    @FXML private HBox hbxMovieCard;
    @FXML private HBox hbxMovieReleaseCard;
    @FXML private TextField txtSearchMovie;
    @FXML private Label lblUserWelcomeMessage;
    @FXML private Button btnMenuLogin;
    @FXML private Button btnMenuRegister;

    @FXML private AnchorPane loginOverlay;
    @FXML private AnchorPane registerOverlay;
    @FXML private LoginOverlayController loginOverlayController;
    @FXML private RegisterOverlayController registerOverlayController;

    private final MovieService movieService = new MovieService(new MovieRepository());
    private List<Label> navLabels;

    private List<Movie> originalNowShowing;
    private List<Movie> originalComingSoon;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hbxMovieCard.getChildren().clear();
        lblUserWelcomeMessage.setVisible(false);
        lblUserWelcomeMessage.setManaged(false);

        initOverlays();
        initNavigation();
        initFilterListeners();
        loadGenres();

        this.originalNowShowing = movieService.getNowShowing();
        this.originalComingSoon = movieService.getComingSoon();

        loadCatalog(originalNowShowing);
        loadUpcomingReleases(originalComingSoon);
    }

    public void openLoginOverlay() {
        if (registerOverlay != null) registerOverlay.setVisible(false);
        if (loginOverlay != null) loginOverlay.setVisible(true);
    }

    public void openRegisterOverlay() {
        if (loginOverlay != null) loginOverlay.setVisible(false);
        if (registerOverlay != null) registerOverlay.setVisible(true);
    }

    public void updateNavbarAfterLogin(String userName) {
        btnMenuLogin.setVisible(false);
        btnMenuLogin.setManaged(false);
        btnMenuRegister.setVisible(false);
        btnMenuRegister.setManaged(false);

        lblUserWelcomeMessage.setVisible(true);
        lblUserWelcomeMessage.setManaged(true);
        lblUserWelcomeMessage.setText("Hola, " + userName);
    }

    private void initOverlays() {
        btnMenuLogin.setOnAction(event -> loginOverlay.setVisible(true));
        btnMenuRegister.setOnAction(event -> registerOverlay.setVisible(true));

        loginOverlayController.setParentController(this);
        registerOverlayController.setParentController(this);
    }

    private void initNavigation() {
        navLabels = Arrays.asList(lblNavHome, lblNavSchedule, lblNavNextRelease);

        lblNavHome.getStyleClass().add("nav-button-active");
        lblNavSchedule.getStyleClass().add("nav-button");
        lblNavNextRelease.getStyleClass().add("nav-button");

        lblNavHome.setOnMouseClicked(event -> {
            updateActiveLabel(lblNavHome);
            animateScrollTo(scrMain, stkFeaturedImageSection);
        });

        lblNavSchedule.setOnMouseClicked(event -> {
            updateActiveLabel(lblNavSchedule);
            animateScrollTo(scrMain, lblScheduleSection);
        });

        lblNavNextRelease.setOnMouseClicked(event -> {
            updateActiveLabel(lblNavNextRelease);
            animateScrollTo(scrMain, lblNextReleaseSection);
        });
    }

    private void initFilterListeners() {
        btnSearchFilters.setOnAction(event -> applyFilters());
        txtSearchMovie.setOnAction(event -> applyFilters());
        cmbGenres.setOnAction(event -> applyFilters());
    }

    private void loadGenres() {

        cmbGenres.getItems().setAll(Genre.values());

        cmbGenres.setConverter(new StringConverter<>() {
            @Override
            public String toString(Genre genre) {
                return genre == null ? "" : GenreFormatter.format(genre);
            }

            @Override
            public Genre fromString(String string) {
                return Genre.TODOS;
            }
        });
        cmbGenres.getSelectionModel().select(Genre.TODOS);
    }

    private void applyFilters() {
        String searchText = txtSearchMovie.getText();
        Genre selectedGenre = cmbGenres.getValue();

        List<Movie> filteredCatalog = movieService.filterMovies(originalNowShowing, searchText, selectedGenre);
        loadCatalog(filteredCatalog);

        List<Movie> filteredUpcoming = movieService.filterMovies(originalComingSoon, searchText, selectedGenre);
        loadUpcomingReleases(filteredUpcoming);
    }

    private void loadCatalog(List<Movie> movies) {
        hbxMovieCard.getChildren().clear();
        try {
            for (Movie movie : movies) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths.MOVIE_CARD_VIEW));
                StackPane card = loader.load();

                MovieCardController controller = loader.getController();
                controller.setMovie(movie);

                hbxMovieCard.getChildren().add(card);
            }
        } catch (IOException e) {
            System.err.println("ERROR: El catálogo de películas no pudo completarse.");
        }
    }

    private void loadUpcomingReleases(List<Movie> movies) {
        hbxMovieReleaseCard.getChildren().clear();

        try {
            for (Movie movie : movies) {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource(Paths.MOVIE_PROX_VIEW)
                );
                StackPane card = loader.load();
                MovieReleaseCardController controller =
                        loader.getController();
                controller.setMovie(movie);
                hbxMovieReleaseCard.getChildren().add(card);
            }

        } catch (IOException e) {
            System.err.println("ERROR: El catálogo de próximas películas no pudo completarse.");
            e.printStackTrace();
        }
    }

    @FXML
    private void ignoreVerticalScroll(ScrollEvent event) {
        if (event.getDeltaY() != 0) {
            event.consume();
        }
    }

    private void animateScrollTo(ScrollPane scrollPane, Node targetSection) {
        double contentHeight = scrollPane.getContent().getBoundsInLocal().getHeight();
        double targetY = targetSection.getBoundsInParent().getMinY() - 50;
        double viewportHeight = scrollPane.getViewportBounds().getHeight();

        if (contentHeight <= viewportHeight) return;

        double targetVValue = targetY / (contentHeight - viewportHeight);
        targetVValue = Math.clamp(targetVValue, 0.0, 1.0);

        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(scrollPane.vvalueProperty(), targetVValue, Interpolator.EASE_BOTH);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(500), keyValue);

        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    private void updateActiveLabel(Label selectedLabel) {
        for (Label label : navLabels) {
            if (label == selectedLabel) {
                if (!label.getStyleClass().contains("nav-button-active")) {
                    label.getStyleClass().remove("nav-button");
                    label.getStyleClass().add("nav-button-active");
                }
            } else {
                label.getStyleClass().remove("nav-button-active");
                if (!label.getStyleClass().contains("nav-button")) {
                    label.getStyleClass().add("nav-button");
                }
            }
        }
    }
}