package tenhouvisualizer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tenhouvisualizer.domain.service.DatabaseService;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {

    public static DatabaseService databaseService;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        try {
            Main.databaseService = new DatabaseService(new File("./tenhouvisualizer.sqlite"));
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        Parent root = FXMLLoader.load(getClass().getResource("/app.fxml"));
        root.getStylesheets().add(this.getClass().getResource("/darcula.css").toExternalForm());
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Tenhou Visualizer");
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        Main.databaseService.close();
    }
}