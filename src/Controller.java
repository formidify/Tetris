import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

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

    public void moveTetriminoDown(Tetrimino tetrimino){
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

        while (!endGame()) {
            System.out.println("!endGame()");
            Tetrimino tetrimino = new Tetrimino(board);
            Timer timer = new java.util.Timer();
            TimerTask timerTask = new java.util.TimerTask() {
                public void run() {
                    Platform.runLater(new Runnable() {
                        public void run() {
                            if (!tetrimino.landed()) {
                                moveTetriminoDown(tetrimino);
                                System.out.println("falling");
                                // check full rows
                            } else {
                                board.putTetrimino(tetrimino);
                                System.out.println("landing");
                            }
                        }
                    });
                }
            };
            timer.schedule(timerTask, 0, (long) (1000 / tetrimino.speed));

        }
        System.out.println("out of while");
    }
}
