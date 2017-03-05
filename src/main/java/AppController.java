/**
 * Created by m-yamamt on 2017/03/04.
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.ResourceBundle;

public class AppController implements Initializable {
    @FXML private Button btn;
    @FXML private Button btn2;
    @FXML private Button btn3;
    @FXML private Button btn4;
    @FXML private Label label;
    @FXML private ListView<String> listview;
    @FXML private Canvas canvas;

    private File selectedFile;
    private GraphicsContext gc;

    private Image[] images = new Image[34];
    private Random random = new Random();

    @FXML
    public void onBtnClicked(ActionEvent e) {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File("./src"));
        selectedFile = fc.showOpenDialog(null);

        if (selectedFile != null) {
            label.setText(selectedFile.toString());
        }
    }

    @FXML
    public void onBtn2Clicked(ActionEvent e) {
        if (selectedFile != null) {
            listview.getItems().add(selectedFile.getName());
        } else {
            System.out.println("file is not valid");
        }
    }

    private void draw(ActionEvent e) {
        int x, y;

        x = 200;
        y = 400;
        for (int i = 0; i < 15; i++) {
            gc.drawImage(images[getId()], x, y);

            if (i % 6 == 5) {
                x = 200;
                y += 45;
            } else {
                x += 32;
            }
        }

        x = 70;
        y = 555;
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            list.add(getId());
        }
        Collections.sort(list);
        for (int i = 0; i < 13; i++) {
            gc.drawImage(images[list.get(i)], x, y);
            x += 32;
        }
    }

    private int getId() {
        int ret = 1;
        while (ret >= 1 && ret <= 7) ret = random.nextInt(34);
        return ret;
    }

    private void rotate(ActionEvent e) {
        //gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        //gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.rotate(-90);
        gc.translate(-600, 0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 0; i < 34; i++) {
            images[i] = new Image("img/" + i + ".png");
        }

        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.GREEN);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.valueOf("#cccccc"));
        gc.fillRect(200, 200, 200, 200);

        for (int i = 0; i < 3; i++) {
            draw(new ActionEvent());
            rotate(new ActionEvent());
        }
    }
}