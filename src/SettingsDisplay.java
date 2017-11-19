import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Creates a settings screen GUI for Tetris...
 *
 */

public class SettingsDisplay{
    private static final int SLOW_SPEED = 800;
    private static final int MED_SPEED = 400;
    private static final int FAST_SPEED = 100;
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

        slow.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        med.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        fast.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        ok.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        // create icons and tooltips for the buttons
        StartDisplay.makeIconButton(new Image(getClass().getResourceAsStream("images/slow.png")), slow,
                "slow mode", new int[] {70, 70});
        StartDisplay.makeIconButton(new Image(getClass().getResourceAsStream("images/med.png")), med,
                "medium mode", new int[] {60, 60});
        StartDisplay.makeIconButton(new Image(getClass().getResourceAsStream("images/fast.png")), fast,
                "fast mode", new int[] {60, 110});
        StartDisplay.makeIconButton(new Image(getClass().getResourceAsStream("images/ok.png")), ok,
                "Done with settings!", new int[] {40, 40});

        // create handlers for the buttons
        slow.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) { controller.gameSpeed = SLOW_SPEED; }
        });

        med.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) { controller.gameSpeed = MED_SPEED; }
        });

        fast.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) { controller.gameSpeed = FAST_SPEED; }
        });

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