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
 * Creates a starting screen GUI for Tetris. The starting screen is a BorderPane that has a Title
 * Pane and several buttons as children. The TetrisController is an observer of this class as it is
 * passed in as a parameter for startScene. The game speed is set in this class and passed to the
 * controller. In addition, the controller starts the round after it is passed an event handler
 * from the starting screen.
 */

public class StartDisplay {
    private static final double MIN_BUTTON_WIDTH = 30;
    private Text directions = new Text("TETRIS");

    private TetrisController controller;

    void startScene(Stage primaryStage, TetrisController tetrisController) {
        controller = tetrisController;
        BorderPane root = new BorderPane();
        Node buttonPane = addButtons();
        Node directionsPane = addTitleText(directions);

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
    * for starting the game, starting the tutorial, or more options for game difficulty.
    */
    private Node addButtons() {
        GridPane buttonPane = new GridPane();
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.setHgap(20);
        buttonPane.setVgap(20);
        buttonPane.setPadding(new Insets(0, 10, 0, 10));

        // Create start, setting, help buttons and add them to the grid
        Button play = new Button();
        Button settings = new Button();
        Button help = new Button();
        buttonPane.add(play, 0, 0, 2,2);
        buttonPane.add(settings, 0, 2);
        buttonPane.add(help, 1, 2);

        play.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        // create icon for play, settings and help button, set their sizes, add popup tooltips
        makeIconButton("images/play.png", play, "Play a new game!");
        makeIconButton("images/settings.png", settings, "Settings");
        makeIconButton("images/help.png", help, "Help");

        //Handle event of the buttons
        play.setOnAction(this::handlePlay);
        settings.setOnAction(this::handleSettings);
        help.setOnAction(this::handleTutorial);

        return buttonPane;
    }

    /*
     * Make buttons with pictures
     */
    private void makeIconButton(String fileName, Button button, String toolTipText) {
        Image buttonImage = new Image(getClass().getResourceAsStream(fileName));
        ImageView buttonView = new ImageView(buttonImage);

        if(fileName.equals("images/play.png")){
            buttonView.setFitHeight( 2 * MIN_BUTTON_WIDTH );
            buttonView.setFitWidth( 2 * MIN_BUTTON_WIDTH );
        } else{
            buttonView.setFitHeight(MIN_BUTTON_WIDTH );
            buttonView.setFitWidth(MIN_BUTTON_WIDTH );
        }

        button.setGraphic(buttonView);
        button.setTooltip(new Tooltip(toolTipText));
    }

    private void handlePlay(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
        controller.startRound();
    }
    
    private void handleSettings(ActionEvent event) {
        Stage newStage = new Stage();
        SettingsDisplay settings = new SettingsDisplay();
        settings.settingStart(newStage, controller);
    }

    private void handleTutorial(ActionEvent event) {
        tutorial().showAndWait();
    }

    /*
     * Adding tutorial pane
     */
    private Stage tutorial() {
        Stage tutorialStage = new Stage();

        Text explanationText = new Text("\nTetris is a tile matching puzzle video game, " +
                                             "originally designed by Alexey Pajitnov.");
        Text objectivesText = new Text("\nThe objective of Tetris is to destroy as many rows of blocks " +
                                            "(called tetriminos) as possible without topping out of the screen!");

        Text directionsHeading = new Text("\n\nDirections:");
        directionsHeading.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

        Text upText = new Text("\nUp: Rotate the tetrimino");
        Text downText  = new Text("\nDown: Speed up tetrimino");
        Text rightLeftText = new Text ("\nRight/Left: Move the tetrimino left and right on the screen");
        Text spaceText = new Text("\nSpace: Teleport tetrimino to the bottom of the screen");
        TextFlow textFlow = new TextFlow();

        textFlow.setPadding(new Insets(10, 10, 10, 10));
        textFlow.getChildren().addAll(explanationText, objectivesText, directionsHeading, upText, downText,
                                      rightLeftText, spaceText);
        textFlow.setLineSpacing(2.0);

        Scene tutorialScene = new Scene(textFlow, 300, 300);
        tutorialStage.setScene(tutorialScene);

        return tutorialStage;
    }

    /*
     * Adding title text
     */
    private Node addTitleText(Text text) {
        FlowPane flowPane = new FlowPane();
        flowPane.setPrefHeight(100);
        flowPane.setPadding(new Insets(80, 10, 0, 10));
        flowPane.setAlignment(Pos.CENTER);
        text.setFont(Font.font("Chalkduster", FontWeight.BOLD, 60));
        flowPane.getChildren().add(text);
        return flowPane;
    }

}