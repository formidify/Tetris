import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {
    Stage primary;
    StartDisplay startView;
    BoardDisplay mainView;
    Board board;
    Tetrimino currTetrimino;
    Tetrimino nextTetrimino;

    Controller(Stage primaryStage){
        primary = primaryStage;
        startView = new StartDisplay();
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
            if (!currTetrimino.landed()) {
                if (keyPressed == KeyCode.LEFT) {
                    mainView.undrawTetrimino(currTetrimino);
                    currTetrimino.translate(0, -1);
                    mainView.drawTetrimino(currTetrimino);
                } else if (keyPressed == KeyCode.RIGHT) {
                    mainView.undrawTetrimino(currTetrimino);
                    currTetrimino.translate(0, 1);
                    mainView.drawTetrimino(currTetrimino);
                } else if (keyPressed == KeyCode.DOWN) {
                    mainView.undrawTetrimino(currTetrimino);
                    currTetrimino.translate(1, 0);
                    mainView.drawTetrimino(currTetrimino);
                } else if (keyPressed == KeyCode.UP) {
                    mainView.undrawTetrimino(currTetrimino);
                    currTetrimino.rotate();
                    mainView.drawTetrimino(currTetrimino);
                } else if (keyPressed == KeyCode.SPACE) {
                    mainView.undrawTetrimino(currTetrimino);
                    currTetrimino.fallToBottom();
                    mainView.drawTetrimino(currTetrimino);
                }
            }
        }

    }

    void startRound() {
        mainView.mainScene(primary, this);
        currTetrimino = new Tetrimino(board);
        nextTetrimino = new Tetrimino(board);
        mainView.updateNextTetrimino(nextTetrimino);
        Timer timer = new java.util.Timer();
        TimerTask timerTask = new java.util.TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        if (!currTetrimino.landed()) {
                            moveTetriminoDown(currTetrimino);
                            //System.out.println("falling");
                            // check full rows
                            mainView.updateScore(40);
                        } else {
                            System.out.println("landing");
                            board.putTetrimino(currTetrimino);
                            currTetrimino = nextTetrimino;
                            nextTetrimino = new Tetrimino(board);
                            mainView.updateNextTetrimino(nextTetrimino);
                        }
                    }
                });
            }
        };
        timer.schedule(timerTask, 0, (long) (1000 / currTetrimino.speed));
    }
}
