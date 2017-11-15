import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.List;
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
                                mainView.updateScore(40);

                            } else {

                                board.putTetrimino(currTetrimino);
                                destroyRows();
                                changeToNextTetrimino();
                            }
                        }
                        else {
                            cancel();
                            endGame();
                        }
                    }
                });
            }
        };

        timer.schedule(timerTask, 3000, (long) (1000 / currTetrimino.speed));
    }


    /*
     * Destroys the full rows in the board and moves un-full rows down the board accordingly
     */
    private void destroyRows(){

        List<Integer> listOfFullRows = board.fullRows();

        for(int i = 0; i < listOfFullRows.size(); i++) {
            mainView.clearLine(listOfFullRows.get(i));
        }

        for(int j : listOfFullRows) {
            mainView.moveRow(j);
        }

    }

    private void moveTetriminoDown(Tetrimino tetrimino){
        mainView.undrawTetrimino(tetrimino);
        tetrimino.newTranslateDown();
        mainView.drawTetrimino(tetrimino);
    }

    private void changeToNextTetrimino() {


        currTetrimino = nextTetrimino;
        nextTetrimino = new Tetrimino(board);
        mainView.updateNextTetrimino(nextTetrimino);
    }

    /*
     * Responds to key presses
     */
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
