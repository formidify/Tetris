import javafx.scene.Scene;
import javafx.stage.Stage;

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

    public void landTetrimino(Board board, Tetrimino tetrimino){
        if (tetrimino.land()){
            board.putTetrimino(tetrimino);
            mainView.undrawTetrimino(tetrimino);
        }
    }

//    public static void main(String[] args){
//        // start screen
//        // board display
//        while (!controller.endGame()){
//            // accept keyboard input
//            // move tetrimino
//            // checkFullRows
//        }
//    }

    private boolean endGame() {
        return board.reachBoardTop();
    }


    void startGame() {
        startView.startScene(primary, this);
    }

    void startRound() {
        mainView.mainScene(primary);
    }
}
