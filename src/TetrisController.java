import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Creates the controller for Tetris. This is an observer of StartDisplay, BoardDisplay, RestartDisplay,
 * SettingsDisplay, Board, and Tetrimino. BoardDisplay, RestartDisplay, Board, and Tetrimino are also
 * observers of TetrisController as it contains the main logic behind the game.
 */

public class TetrisController {

    Stage primary;
    int gameSpeed;

    StartDisplay startView;
    BoardDisplay mainView;
    RestartDisplay restartView;

    Board board;
    Tetrimino currTetrimino;
    Tetrimino nextTetrimino;

    /*
     * Constructor for TetrisController
     */
    TetrisController(Stage primaryStage) {
        primary = primaryStage;
        gameSpeed = 1000;

        startView = new StartDisplay();
        mainView = new BoardDisplay();
        restartView = new RestartDisplay();

        board = new Board();
    }

    /*
     * starting the game using the StartDisplay class's event handlers
     */
    void startGame() {
        startView.startScene(primary, this);
    }

    private boolean checkEndGame() {
        return board.reachBoardTop();
    }

    private void endGame(){
        restartView.restartScene(primary, this, startView);
    }

    /*
     * Starts a round of Tetris. The timer is preset according to the user's preferred game speed.
     * Detects the end of the game and quits.
     */
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
                        if (!checkEndGame()) {

                            if (!currTetrimino.landed()) {

                                moveTetriminoDown(currTetrimino);
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

        timer.schedule(timerTask, 1000, (long) (gameSpeed / currTetrimino.speed));
    }

    /*
     * Destroys the full rows in the board and moves un-full rows down the board accordingly
     */
    private void destroyRows() {

        List<Integer> listOfFullRows = board.manageFullRows();

        for(int i = 0; i < listOfFullRows.size(); i++) {
            mainView.clearLine(listOfFullRows.get(i));
        }

        //update board score, defaults to 10 per line for now
        if (listOfFullRows.size()>0) {
            mainView.updateScore(10 * listOfFullRows.size());
        }

        for(int j : listOfFullRows) {
            mainView.moveRow(j);
        }

    }

    private void moveTetriminoDown(Tetrimino tetrimino) {
        board.removeTetrimino(tetrimino);
        mainView.undrawTetrimino(tetrimino);
        tetrimino.translateDown();
        board.putTetrimino(tetrimino);
        mainView.drawTetrimino(tetrimino);
    }

    private void changeToNextTetrimino() {

        currTetrimino = nextTetrimino;
        nextTetrimino = new Tetrimino(board);
        mainView.updateNextTetrimino(nextTetrimino);
        board.putTetrimino(currTetrimino);
        mainView.drawTetrimino(currTetrimino);
    }

    /*
     * Responds to key presses
     */
    void respondToKey(KeyCode keyPressed) {
        if (currTetrimino != null){
            if (!currTetrimino.landed()) {
                board.removeTetrimino(currTetrimino);
                mainView.undrawTetrimino(currTetrimino);
                if (keyPressed == KeyCode.LEFT) {
                    currTetrimino.translateLeft();
                } else if (keyPressed == KeyCode.RIGHT) {
                    currTetrimino.translateRight();
                } else if (keyPressed == KeyCode.DOWN) {
                    currTetrimino.translateDown();
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
