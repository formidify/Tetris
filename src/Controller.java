import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import java.util.Observer;

public class Controller {
    Stage primary;
    StartGame startView;
    BoardDisplay mainView;
    Board board;

    Controller(Stage primaryStage){
        primary = primaryStage;
        startView = new StartGame();
        mainView = new BoardDisplay();
        board = new Board(mainView);
    }

    public void landTetrimino(Tetrimino tetrimino){
        mainView.undrawTetrimino(tetrimino);
        tetrimino.fallByOneSquare();
        mainView.drawTetrimino(tetrimino);
    }

    private boolean endGame() {
        return board.reachBoardTop();
    }

    void startGame() {
        startView.startScene(primary, this);
    }

    void respondToKey(KeyCode keyPressed){

    }

    void startRound() {
        mainView.mainScene(primary, this);

        while (!endGame()){
            Tetrimino tetrimino = new Tetrimino(board);
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            Platform.runLater(new Runnable(){
                                @Override
                                public void run() {
                                    if (!tetrimino.landed()){
                                        landTetrimino(tetrimino);
                                        // check full rows
                                    } else {
                                        cancel();
                                    }
                                }
                            });
                        }
                    },
                    0,
                    (long) (1000/tetrimino.speed)
            );
            board.putTetrimino(tetrimino);
            mainView.undrawTetrimino(tetrimino);
        }
    }
}
