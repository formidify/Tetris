import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by yuq on 11/7/17.
 */


public class StartGame {
    public static final double MIN_BUTTON_WIDTH = 30;
    private Text directions = new Text("TETRIS");

    private Controller controller;
    private Stage primary;

    void startScene(Stage primaryStage, Controller control) {
        controller = control;
        primary = primaryStage;

        BorderPane root = new BorderPane();
        Node buttonPane = addButtons();
        Node directionsPane = addText(directions, FontWeight.BOLD, 60);

        root.setTop(directionsPane);
        root.setCenter(buttonPane);

        Scene scene = new Scene(root, 300, 600);
        primaryStage.setTitle("Tetris");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private Node addButtons() {
        GridPane buttonPane = new GridPane();
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.setHgap(20);
        buttonPane.setVgap(20);
        buttonPane.setPadding(new Insets(0, 10, 0, 10));
        //buttonPane.setStyle("-fx-background-image: url('images/background.png');-fx-background-size: stretch;-fx-background-position:center top;");

        // Create start, setting, help buttons and add them to the grid
        Button play = new Button();
        Button settings = new Button();
        Button help = new Button();

        buttonPane.add(play, 0, 0, 2,2);
        buttonPane.add(settings, 0, 2);
        buttonPane.add(help, 1, 2);

        // Handle event of the buttons
        play.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.startRound();
            }
        });

        // create icon for play button, set its size, add popup tooltip
        play.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        Image imagePlay = new Image(getClass().getResourceAsStream("play.png"));
        ImageView playView = new ImageView(imagePlay);
        playView.setFitHeight( 2 * MIN_BUTTON_WIDTH );
        playView.setFitWidth( 2 * MIN_BUTTON_WIDTH );
        play.setGraphic(playView);
        play.setTooltip(new Tooltip("Play a new game!"));

        // create icon for settings button, set its size, add popup tooltip
        Image imageSettings = new Image(getClass().getResourceAsStream("settings.png"));
        ImageView settingsView = new ImageView(imageSettings);
        settingsView.setFitHeight(MIN_BUTTON_WIDTH);
        settingsView.setFitWidth(MIN_BUTTON_WIDTH);
        settings.setGraphic(settingsView);
        settings.setTooltip(new Tooltip("settings"));

        // create icon for help button, set its size, add popup tooltip
        Image imageHelp = new Image(getClass().getResourceAsStream("help.png"));
        ImageView helpView = new ImageView(imageHelp);
        helpView.setFitHeight(MIN_BUTTON_WIDTH);
        helpView.setFitWidth(MIN_BUTTON_WIDTH);
        help.setGraphic(helpView);
        help.setTooltip(new Tooltip("help"));

        return buttonPane;
    }

    private Node addText(Text text, FontWeight fontWeight, int fontSize) {
        FlowPane flowPane = new FlowPane();
        flowPane.setPrefHeight(100);
        flowPane.setPadding(new Insets(80, 10, 0, 10));
        flowPane.setAlignment(Pos.CENTER);
        text.setFont(Font.font("Chalkduster", fontWeight, fontSize));
        flowPane.getChildren().add(text);
        return flowPane;
    }

}