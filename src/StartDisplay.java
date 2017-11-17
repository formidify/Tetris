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
import javafx.scene.text.*;
import javafx.stage.Stage;

/**
 * Created by yuq on 11/7/17.
 * Creates the starting screen for tetris.
 */


public class StartDisplay {
    public static final double MIN_BUTTON_WIDTH = 30;
    private Text directions = new Text("TETRIS");

    private TetrisController controller;

    void startScene(Stage primaryStage, TetrisController tetrisController) {
        controller = tetrisController;
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

    /*
    * Creating an overall grid pane for the starting screen and populating pane with buttons
    * for starting the game, starting the tutorial, or more options for game look and control.
    * Currently, we have not yet implemented the tutorial or the more options as we are 
    * yet unsure of what they will look like or what aspects of the game they will have control over.
    */
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

        help.setOnAction(this::handleTutorial);

        return buttonPane;
    }

    private void handleTutorial(ActionEvent event) {
        tutorial().showAndWait();
    }

    private Stage tutorial(){
        Stage tutorialStage = new Stage();

        Text explanationText = new Text("\nTetris is a tile matching puzzle video game, originally designed by Alexey Pajitnov.");
        Text objectivesText = new Text("\nThe objective of Tetris is to destroy as many rows of blocks (called tetriminos) as possible without topping out of the screen!");

        Text directionsHeading = new Text("\n\nDirections:");
        directionsHeading.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

        Text upText = new Text("\nUp: Rotate the tetrimino");
        Text downText  = new Text("\nDown: Speed up tetrimino");
        Text rightLeftText = new Text ("\nRight/Left: Move the tetrimino left and right on the screen");
        Text spaceText = new Text("\nSpace: Teleport tetrimino to the bottom of the screen");
        TextFlow textFlow = new TextFlow();

        textFlow.setPadding(new Insets(10, 10, 10, 10));
        textFlow.getChildren().addAll(explanationText, objectivesText, directionsHeading, upText, downText, rightLeftText, spaceText);
        textFlow.setLineSpacing(2.0);

        Scene tutorialScene = new Scene(textFlow, 300, 300);
        tutorialStage.setScene(tutorialScene);

        return tutorialStage;
    }

    /*
    * Method for adding text with correct font and padding. Weight and size are specified by user.
    */
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