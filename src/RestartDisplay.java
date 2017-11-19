import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.*;
import javafx.stage.Stage;

/**
 * Creates a restart screen GUI for Tetris. The restart screen is a BorderPane that has a node for
 * an instruction phrase and several buttons as children. This class is called when end of game is
 * detected, and directs the user to start a new game or exit.
 */

public class RestartDisplay {
    private static final int SCENE_WIDTH = 400;
    private static final int SCENE_HEIGHT = 400;
    private TetrisController controller;
    private StartDisplay newStartDisplay;

    void restartScene(Stage primaryStage, TetrisController tetrisController, StartDisplay startView) {
        controller = tetrisController;
        newStartDisplay = startView;

        BorderPane root = new BorderPane();
        Node buttonPane = addButtons();
        Node directionsPane = addInstruction(new Text("Would you like to start a new game?"));
        TextFlow scorePane = addScore(controller.score);

        root.setTop(scorePane);
        root.setCenter(directionsPane);
        root.setBottom(buttonPane);

        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Node addButtons() {
        GridPane buttonPane = new GridPane();
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.setHgap(20);
        buttonPane.setPadding(new Insets(0, 10, 30, 10));

        // Create restart and exit buttons and add them to the grid
        Button newGame = new Button();
        Button exit = new Button();
        buttonPane.add(newGame, 0, 0);
        buttonPane.add(exit, 2, 0);

        // create icons and tooltips for restart and exit buttons
        StartDisplay.makeIconButton(new Image(getClass().getResourceAsStream("images/newGame.png")), newGame,
                                    "Start a new game!", new int[] {60, 60});
        StartDisplay.makeIconButton(new Image(getClass().getResourceAsStream("images/exit.png")), exit,
                                    "Exit!", new int[] {60, 60});

        // By pressing newGame button, a new round is initiated and this restart window is closed.
        newGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage newStage = new Stage();
                newStartDisplay.startScene(newStage, controller);
                ((Node)(event.getSource())).getScene().getWindow().hide();
            }
        });

        // By pressing exit button, the user exits the game.
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });

        return buttonPane;
    }
    
    private TextFlow addScore(int score) {
        TextFlow textFlow = new TextFlow();
        textFlow.setPrefHeight(100);
        textFlow.setPadding(new Insets(10, 10, 0, 10));
        textFlow.setTextAlignment(TextAlignment.CENTER);
        textFlow.setLineSpacing(0);
        Text messageText;
        if(score < 100){
            messageText = new Text("Go practice more! You only scored: ");
        }
        else if (score < 200){
            messageText = new Text("Good job! You scored: ");
        }
        else {
            messageText = new Text("Wow, you're an expert! You scored: ");
        }
        messageText.setFont(Font.font("Chalkduster", FontWeight.NORMAL, 14));

        Text scoreText = new Text("\n" + Integer.toString(score));
        scoreText.setFont(Font.font("Chalkduster", FontWeight.NORMAL, 70));
        textFlow.getChildren().add(messageText);
        textFlow.getChildren().add(scoreText);
        return textFlow;
    }

    private Node addInstruction(Text text) {
        FlowPane flowPane = new FlowPane();
        flowPane.setPrefHeight(100);
        flowPane.setPadding(new Insets(0, 10, 20, 10));
        flowPane.setAlignment(Pos.CENTER);
        text.setFont(Font.font("Chalkduster", FontWeight.NORMAL, 14));
        flowPane.getChildren().add(text);
        return flowPane;
    }
}
