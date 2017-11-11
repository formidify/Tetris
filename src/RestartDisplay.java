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


public class RestartDisplay {
    public static final double MIN_BUTTON_WIDTH = 30;
    private Text directions = new Text("Would you like to start a new game?");

    private TetrisController controller;

    void restartScene(Stage primaryStage, TetrisController tetrisController) {
        controller = tetrisController;

        BorderPane root = new BorderPane();
        Node buttonPane = addButtons();
        Node directionsPane = addText(directions, FontWeight.NORMAL, 14);

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

        // create icon for new game button, add popup tooltip
        Image imageNewG = new Image(getClass().getResourceAsStream("newGame.png"));
        ImageView newgameView = new ImageView(imageNewG);
        newgameView.setFitHeight(60);
        newgameView.setFitWidth(60);
        newGame.setGraphic(newgameView);
        newGame.setTooltip(new Tooltip("Start a new game!"));

        newGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.startRound();
            }
        });

        // create icon for exit button, add popup tooltip
        Image imageExit = new Image(getClass().getResourceAsStream("exit.png"));
        ImageView exitView = new ImageView(imageExit);
        exitView.setFitHeight(60);
        exitView.setFitWidth(60);
        exit.setGraphic(exitView);
        exit.setTooltip(new Tooltip("Exit!"));

        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });

        return buttonPane;
    }

    // TODO: THIS FUNCTION SHOULD GO SOMEWHERE ELSE! IT'S USEFUL BUT REDUNDANT!
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