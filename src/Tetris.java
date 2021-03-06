import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Runnable file for Tetris
 */

public class Tetris extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {

        TetrisController tetrisController = new TetrisController(primaryStage);
        tetrisController.startGame();
    }

    public static void main(String[] args){
        launch(args);
    }
}
