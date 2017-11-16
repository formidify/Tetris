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
        board.putTetrimino(currTetrimino);
        mainView.drawTetrimino(currTetrimino);
        Timer timer = new java.util.Timer();
        TimerTask timerTask = new java.util.TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        //mainView.drawTetrimino(currTetrimino);
                        if (!checkEndGame()) {
                            // TODO JUST FOR TESTING RN
                            //System.out.println(board.rowIsFull(23));
                            //if (board.rowIsFull(23)) {destroyRows();
                            if (!currTetrimino.landed()) {
                                board.removeTetrimino(currTetrimino);
                                moveTetriminoDown(currTetrimino);
                                board.putTetrimino(currTetrimino);
                                //System.out.println("falling");
                                // check full rows
                                mainView.updateScore(40);
                            } else {
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

    private void destroyRows(){

        List<Integer> listOfFullRows = board.fullRows();
        //System.out.println("------------full rows:  -------------");

        for(int i = 0; i < listOfFullRows.size(); i++) {
            //System.out.println(listOfFullRows.get(i));
            mainView.clearLine(listOfFullRows.get(i));
        }

//        for(int j = listOfFullRows.size() - 1; j >= 0; j--) {
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
        int[][] pos = currTetrimino.getPosition();
        for (int i = 0; i < pos.length; i++) {
            System.out.println(pos[i][0]);
            System.out.println(pos[i][1]);
        }
        board.putTetrimino(currTetrimino);
        mainView.drawTetrimino(currTetrimino);
    }

    void respondToKey(KeyCode keyPressed){
        if (currTetrimino != null){
            if (!currTetrimino.landed()) {
                board.removeTetrimino(currTetrimino);
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
                board.putTetrimino(currTetrimino);
                mainView.drawTetrimino(currTetrimino);
            }
        }
    }


}
