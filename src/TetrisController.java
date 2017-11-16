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
<<<<<<< HEAD
        board.putTetrimino(currTetrimino);
=======

>>>>>>> 46fd1c4c2735396b3a7b8ab9d8d8a1ad0455ac97
        Timer timer = new java.util.Timer();
        TimerTask timerTask = new java.util.TimerTask() {
            public void run() {

                Platform.runLater(new Runnable() {

                    public void run() {
                        if (!checkEndGame()) {
<<<<<<< HEAD
=======

>>>>>>> 46fd1c4c2735396b3a7b8ab9d8d8a1ad0455ac97
                            if (!currTetrimino.landed()) {

                                moveTetriminoDown(currTetrimino);
<<<<<<< HEAD
                            } else {
=======
                                mainView.updateScore(40);

                            } else {

                                board.putTetrimino(currTetrimino);
>>>>>>> 46fd1c4c2735396b3a7b8ab9d8d8a1ad0455ac97
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
<<<<<<< HEAD
        timer.schedule(timerTask, 1000, (long) (1000 / currTetrimino.speed));
=======

        timer.schedule(timerTask, 3000, (long) (1000 / currTetrimino.speed));
>>>>>>> 46fd1c4c2735396b3a7b8ab9d8d8a1ad0455ac97
    }


    /*
     * Destroys the full rows in the board and moves un-full rows down the board accordingly
     */
    private void destroyRows(){

        List<Integer> listOfFullRows = board.fullRows();

        for(int i = 0; i < listOfFullRows.size(); i++) {
            mainView.clearLine(listOfFullRows.get(i));
        }
<<<<<<< HEAD
        //update board score, defaults to 10 per line for now
        if (listOfFullRows.size()>0) {
            mainView.updateScore(10 * listOfFullRows.size());
        }
//        for(int j = listOfFullRows.size() - 1; j >= 0; j--) {
=======

>>>>>>> 46fd1c4c2735396b3a7b8ab9d8d8a1ad0455ac97
        for(int j : listOfFullRows) {
            mainView.moveRow(j);
        }

    }

    private void moveTetriminoDown(Tetrimino tetrimino){
        board.removeTetrimino(tetrimino);
        tetrimino.newTranslateDown();
        board.putTetrimino(tetrimino);
    }

    private void changeToNextTetrimino() {


        currTetrimino = nextTetrimino;
        nextTetrimino = new Tetrimino(board);
        mainView.updateNextTetrimino(nextTetrimino);
        board.putTetrimino(currTetrimino);
    }

    /*
     * Responds to key presses
     */
    void respondToKey(KeyCode keyPressed){
        if (currTetrimino != null){
            if (!currTetrimino.landed()) {
                board.removeTetrimino(currTetrimino);
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
                board.putTetrimino(currTetrimino);
            }
        }
    }


}
