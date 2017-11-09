public class Controller {
    BoardDisplay boardDisplay = new BoardDisplay();
    Board board = new Board(boardDisplay);

    public void landTetrimino(Board board, Tetrimino tetrimino){
        if (tetrimino.land()){
            board.putTetrimino(tetrimino);
            boardDisplay.undrawTetrimino(tetrimino);
        }
    }

    public static void main(String[] args){

        Controller controller = new Controller();

        // start screen
        // board display
        while (!controller.endGame()){
            // accept keyboard input
            // move tetrimino
            // checkFullRows
        }
    }

    private boolean endGame() {
        return board.reachBoardTop();
    }


}
