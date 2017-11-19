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
 * Creates a restart screen GUI for Tetris. The restart screen is a BorderPane that has a node for
 * an instruction phrase and several buttons as children. This class is called when end of game is
 * detected, and directs the user to start a new game or exit.
 */

public class RestartDisplay {

    private TetrisController controller;
    private StartDisplay newStartDisplay;
    private double buttonIconSize = 60;

    void restartScene(Stage primaryStage, TetrisController tetrisController, StartDisplay startView) {
        controller = tetrisController;
        newStartDisplay = startView;

        BorderPane root = new BorderPane();
        Node buttonPane = addButtons();
        Node directionsPane = addInstruction(new Text("Would you like to start a new game?"));

        root.setTop(directionsPane);
        root.setCenter(buttonPane);

        Scene scene = new Scene(root, 350, 250);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Node addButtons() {
        GridPane buttonPane = new GridPane();
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.setHgap(20);
        buttonPane.setPadding(new Insets(0, 10, 0, 10));

        // Create restart and exit buttons and add them to the grid
        Button newGame = new Button();
        Button exit = new Button();
        buttonPane.add(newGame, 0, 0);
        buttonPane.add(exit, 2, 0);

        // create icons and tooltips for restart and exit buttons
        makeIconButton("images/newGame.png", newGame, "Start a new game!");
        makeIconButton("images/exit.png", exit,"Exit!");

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

    private void makeIconButton(String fileName, Button button, String toolTipText) {
        Image buttonImage = new Image(getClass().getResourceAsStream(fileName));
        ImageView buttonView = new ImageView(buttonImage);
        buttonView.setFitHeight( buttonIconSize );
        buttonView.setFitWidth( buttonIconSize );
        button.setGraphic(buttonView);
        button.setTooltip(new Tooltip(toolTipText));
    }

    //Adding and formatting the instruction text
    private Node addInstruction(Text text) {
        FlowPane flowPane = new FlowPane();
        flowPane.setPrefHeight(100);
        flowPane.setPadding(new Insets(80, 10, 0, 10));
        flowPane.setAlignment(Pos.CENTER);
        text.setFont(Font.font("Chalkduster", FontWeight.NORMAL, 14));
        flowPane.getChildren().add(text);
        return flowPane;
    }
}
