package com.absolutecinema.application;

import com.absolutecinema.utils.Paths;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    public static App app;
    private Stage stageWindow;
    private static final String prefix = "AbsoluteCinema";

    @Override
    public void start(Stage stage) {
        app = this;
        stageWindow = stage;
        setScene(Paths.MENU_VIEW);
    }

    public void setTitle(String title) {
        stageWindow.setTitle(prefix + title);
    }

    public void setScene(String path) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        try {
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);
            stageWindow.setScene(scene);
            stageWindow.show();
            stageWindow.setMaximized(true);
            setTitle(" | Menú Principal");
        } catch (IOException e) {
            System.err.println("ERROR: ¡No se pudo cargar el archivo!");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
