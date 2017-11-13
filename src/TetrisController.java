import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;

import java.util.Timer;
import java.util.TimerTask;

public class TetrisController {

    Stage primary;

    StartDisplay startView;
    BoardDisplay mainView;
    RestartDisplay restartView;

    Board board;
    Tetrimino currTetrimino;
    Tetrimino nextTetrimino;

    TetrisController(Stage primaryStage){
        primary = primaryStage;

        startView = new StartDisplay();
        mainView = new BoardDisplay();
        restartView = new RestartDisplay();

        board = new Board(mainView);
    }

    void startGame() {
        startView.startScene(primary, this);
    }

    private boolean checkEndGame() {
        return board.reachBoardTop();
    }

    private void endGame(){
        restartView.restartScene(primary, this);
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
                        if (!checkEndGame()) {
                            if (!currTetrimino.landed()) {
                                moveTetriminoDown(currTetrimino);
                                //System.out.println("falling");
                                // check full rows
                                mainView.updateScore(40);
                            } else {
                                //System.out.println("landing");
                                changeToNextTetrimino();
                            }
                        } else {
                            cancel();
                            endGame();
                        }
                    }
                });
            }
        };
        timer.schedule(timerTask, 3000, (long) (1000 / currTetrimino.speed));

    }

    private void moveTetriminoDown(Tetrimino tetrimino){
        mainView.undrawTetrimino(tetrimino);
        tetrimino.fallByOneSquare();
        mainView.drawTetrimino(tetrimino);
    }

    private void changeToNextTetrimino() {
        board.putTetrimino(currTetrimino);
        currTetrimino = nextTetrimino;
        nextTetrimino = new Tetrimino(board);
        mainView.updateNextTetrimino(nextTetrimino);
    }

    void respondToKey(KeyCode keyPressed){
        if (currTetrimino != null){
            if (!currTetrimino.landed()) {
                mainView.undrawTetrimino(currTetrimino);
                if (keyPressed == KeyCode.LEFT) {
                    currTetrimino.newTranslateLeft();
                } else if (keyPressed == KeyCode.RIGHT) {
                    currTetrimino.newTranslateRight();
                } else if (keyPressed == KeyCode.DOWN) {
                    currTetrimino.newTranslateDown();
                } else if (keyPressed == KeyCode.UP) {
                    currTetrimino.rotate();
                } else if (keyPressed == KeyCode.SPACE) {
                    currTetrimino.fallToBottom();
                }
                mainView.drawTetrimino(currTetrimino);
            }
        }
    }


}
