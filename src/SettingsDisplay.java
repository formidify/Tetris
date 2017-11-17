import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.application.Application;

/**
 * Created by yuq on 11/7/17.
 * Creates the starting screen for tetris.
 */


public class SettingsDisplay{
    private Text directions = new Text("Set your game speed!");
    private TetrisController controller;


    void settingStart(Stage primaryStage, TetrisController controller) {
        this.controller = controller;
        BorderPane root = new BorderPane();
        Node buttonPane = addButtons();
        Node directionsPane = addText(directions);

        root.setTop(directionsPane);
        root.setCenter(buttonPane);

        Scene scene = new Scene(root, 300, 600);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private Node addButtons() {
        GridPane buttonPane = new GridPane();
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.setVgap(10);
        buttonPane.setPadding(new Insets(0, 10, 0, 10));

        // Create game mode buttons and add them to the grid
        Button slow = new Button();
        Button med = new Button();
        Button fast = new Button();
        Button ok = new Button();

        buttonPane.add(slow, 0, 0,1,1);
        buttonPane.add(med, 0, 1,1,1);
        buttonPane.add(fast, 0, 2,1,1);
        buttonPane.add(ok, 0, 5,1,1);

        // create icon for the slow mode, add popup tooltip
        slow.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        Image imageSlow = new Image(getClass().getResourceAsStream("slow.png"));
        ImageView slowView = new ImageView(imageSlow);
        slowView.setFitHeight(70);
        slowView.setFitWidth(70);
        slow.setGraphic(slowView);
        slow.setTooltip(new Tooltip("slow mode"));

        slow.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) { controller.gameSpeed = 800; }
        });


        // create icon for the medium mode, add popup tooltip
        med.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        Image imageMed = new Image(getClass().getResourceAsStream("med.png"));
        ImageView medView = new ImageView(imageMed);
        medView.setFitHeight(60);
        medView.setFitWidth(60);
        med.setGraphic(medView);
        med.setTooltip(new Tooltip("medium mode"));

        med.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) { controller.gameSpeed = 500; }
        });

        // create icon for the fast mode, add popup tooltip
        fast.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        Image imageFast = new Image(getClass().getResourceAsStream("fast.png"));
        ImageView fastView = new ImageView(imageFast);
        fastView.setFitHeight(60);
        fastView.setFitWidth(110);
        fast.setGraphic(fastView);
        fast.setTooltip(new Tooltip("fast mode"));

        fast.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) { controller.gameSpeed = 200; }
        });

        // create icon for ok button, add popup tooltip
        ok.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        Image imageOK = new Image(getClass().getResourceAsStream("ok.png"));
        ImageView okView = new ImageView(imageOK);
        okView.setFitHeight(40);
        okView.setFitWidth(40);
        ok.setGraphic(okView);
        ok.setTooltip(new Tooltip("Done with settings!"));

        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ((Node)(event.getSource())).getScene().getWindow().hide();
            }
        });

        return buttonPane;
    }

    private Node addText(Text text) {
        FlowPane flowPane = new FlowPane();
        flowPane.setPrefHeight(100);
        flowPane.setPadding(new Insets(80, 10, 0, 10));
        flowPane.setAlignment(Pos.CENTER);
        text.setFont(Font.font("Chalkduster", FontWeight.BOLD, 14));
        flowPane.getChildren().add(text);
        return flowPane;
    }



}