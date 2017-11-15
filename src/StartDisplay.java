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
import javafx.scene.text.TextFlow;

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

        help.setOnAction(this::handleButtonAction);

        return buttonPane;
    }

    private void handleButtonAction(ActionEvent event) {
        tutorial().showAndWait();
    }

    private Stage tutorial(){
        Stage newStage = new Stage();
        //FlowPane tutorialPane = new FlowPane();
        Text explanationText = new Text("Tetris is a tile matching puzzle video game, originally designed by Alexey Pajitnov")
        Text objectivesText = new Text("The objective of Tetris is to destroy as many rows of blocks (called tetriminos) as possible without topping out of the screen!");

        Text text2 = new Text("Directions:");

        Text upText = new Text("Up: Rotate the tetrimino");
        Text downText  = new Text("Down: Speed up tetrimino");
        Text rightLeftText = new Text ("Right/Left: Move the tetrimino left and right on the screen");
        Text spaceText = new Text("Space: Teleport tetrimino to the bottom of the screen")
        TextFlow textFlow = new TextFlow(explanationText, objectivesText, text2, upText, downText, rightLeftText, spaceText);
        Scene scene2 = new Scene(TextFlow, 300, 300);
        newStage.setScene(scene2);
        return newStage
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