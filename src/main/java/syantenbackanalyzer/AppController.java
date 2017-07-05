package syantenbackanalyzer;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import org.xml.sax.SAXException;
import tenhouvisualizer.BoardControl;
import tenhouvisualizer.Scene;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AppController implements Initializable {
    public ProgressBar progressBar;
    public MenuItem openMenuItem;
    @FXML
    private BorderPane root;
    @FXML
    private Label label;
    @FXML
    private Label label2;
    @FXML
    private ListView<Scene> listView;
    @FXML
    private BoardControl boardControl;

    private File lastSelectedFile = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.boardControl.drawScene();
//        this.label2.textProperty().bind(Bindings.concat(
//                Bindings.convert(Bindings.size(this.listView.getItems())),
//                new SimpleStringProperty("/"),
//                new SimpleStringProperty("NaN")) );
        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                this.boardControl.drawScene(newScene);
                this.label.setText(newScene.toString());
            }
        });
    }

//    @FXML
//    public void onExit(ActionEvent actionEvent) {
//        Platform.exit();
//    }

    @FXML
    public void analyzeZIP(ActionEvent e) throws IOException, ParserConfigurationException, SAXException {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(lastSelectedFile == null ? new File(".") : lastSelectedFile);
        File selectedFile = fc.showOpenDialog(root.getScene().getWindow());

        if (selectedFile != null) {
            lastSelectedFile = new File(selectedFile.getParent());

            Task<List<Scene>> task = new AnalyzeZipTask(selectedFile, listView, label2);
            this.progressBar.progressProperty().bind(task.progressProperty());
            task.setOnSucceeded(a -> this.openMenuItem.setDisable(false));
            task.setOnRunning(a -> this.openMenuItem.setDisable(true));
            new Thread(task).start();
        }
    }

    @FXML
    public void analyzeDB(ActionEvent actionEvent) {
        Task<List<Scene>> task = new AnalyzeDBTask(listView, label2);
        this.progressBar.progressProperty().bind(task.progressProperty());
        task.setOnSucceeded(a -> this.openMenuItem.setDisable(false));
        task.setOnRunning(a -> this.openMenuItem.setDisable(true));
        new Thread(task).start();
    }
}