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
    Tetrimino currTetrimino;
    Tetrimino nextTetrimino;

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
        if (currTetrimino != null){
            if (keyPressed == KeyCode.LEFT){
                mainView.undrawTetrimino(currTetrimino);
                currTetrimino.translate(0, -1);
                mainView.drawTetrimino(currTetrimino);
            } else if (keyPressed == KeyCode.RIGHT){
                mainView.undrawTetrimino(currTetrimino);
                currTetrimino.translate(0, 1);
                mainView.drawTetrimino(currTetrimino);
            } else if (keyPressed == KeyCode.DOWN){
                mainView.undrawTetrimino(currTetrimino);
                currTetrimino.translate(1, 0);
                mainView.drawTetrimino(currTetrimino);
            } else if (keyPressed == KeyCode.UP){
                mainView.undrawTetrimino(currTetrimino);
                currTetrimino.rotate();
                mainView.drawTetrimino(currTetrimino);
            }
        }

    }

    void startRound() {
        mainView.mainScene(primary, this);

        //while (!endGame()) {
            System.out.println("!endGame()");
            currTetrimino = new Tetrimino(board);
            Timer timer = new java.util.Timer();
            TimerTask timerTask = new java.util.TimerTask() {
                public void run() {
                    Platform.runLater(new Runnable() {
                        public void run() {
                            if (!currTetrimino.landed()) {
                                moveTetriminoDown(currTetrimino);
                                //System.out.println("falling");
                                // check full rows
                            } else {
                                System.out.println("landing");
                                currTetrimino = new Tetrimino(board);
                                board.putTetrimino(currTetrimino);

                            }
                        }
                    });
                }
            };
            timer.schedule(timerTask, 0, (long) (1000 / currTetrimino.speed));

        //}
        //System.out.println("out of while");
    }
}
