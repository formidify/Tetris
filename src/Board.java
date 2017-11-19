import java.util.*;

/**
 * Creates a Board class to handle the logic in the underlying grid for Tetris. This is
 * a subject for TetrisController and BoardDisplay. For instance, when the board updates
 * to remove a Tetrimino from the grid, it calls the BoardDisplay to undraw the Tetrimino from the
 * graphical display. In addition, it keeps track of all the Tetriminos in play. Finally,
 * the Tetris Controller observes the Board to keep track of whether or not to end the game.
 */

public class Board {
    public static final int NUMCOLUMN = 12;
    public static final int NUMROW = 24;

    boolean[][] board;
    boolean[][] rowAboveBoard;
    BoardDisplay boardView;


    Board(BoardDisplay boardDisplay){
         rowAboveBoard = new boolean[1][NUMCOLUMN];
         board = new boolean[NUMROW][NUMCOLUMN];
         clearBoard();
         boardView = boardDisplay;
    }

    /*
     * Clears/instantiates the board and the row above board
     */
    void clearBoard(){
        for (int i = 0; i < NUMCOLUMN; i++){
            for (int j = 0; j < NUMROW; j++){
                board[j][i] = false;
            }
            rowAboveBoard[0][i] = false;
        }
    }

    /*
     * Switches the places the Tetrimino previously occupied to false so
     * it removes the Tetrimino from the underlying Board grid and undraws it
     * from the BoardDisplay
     */
    void removeTetrimino(Tetrimino tetrimino){
        int[][] position = tetrimino.getPosition();
        for (int[] pos : position) {
            board[pos[0]][pos[1]] = false;
        }
        boardView.undrawTetrimino(tetrimino);
    }

    /*
     * Puts a tetrimino in the Board grid and draws it in the
     * BoardDisplay.
     */
    void putTetrimino(Tetrimino tetrimino) {
        int[][] position = tetrimino.getPosition();

        //just to check if one of the coordinates are invalid
        for (int[] pos : position) {
            if (board[pos[0]][pos[1]]){
                placeTetriminoOutOfBounds(tetrimino);
                boardView.drawTetrimino(tetrimino);
                return;
            }
        }

        for (int[] pos : position) {
            board[pos[0]][pos[1]] = true;
        }

        boardView.drawTetrimino(tetrimino);
    }

    /*
     * Helper function to draw one part of the tetrimino when the other part is out of the bound
     */
    private void placeTetriminoOutOfBounds(Tetrimino tetrimino){
        int[][] position = tetrimino.getPosition();
        int outOfGrid = 0;
        while (!canFitIntoGrid(position)) {
            for (int i = 0; i < position.length; i++) {
                position[i][0]--;
                if (position[i][0] < 0) {
                    outOfGrid++;
                }
            }
            //if all four are out of the grid, no need to elevate any more, just quit the while loop
            if (outOfGrid == position.length) {
                break;
            }
            //reset outOfGrid
            outOfGrid = 0;
        }
            //set it to tetrimino's new position
            tetrimino.position = updateTetriminoOutOfBounds(position);
        }

    /*
     * Helper function to update the position of an out-of-bound tetrimino on the board and the row above board
     */
    private int[][] updateTetriminoOutOfBounds(int[][] position){
            List<int[]> tempInsideGridArray = new ArrayList<int[]>();
            int insideGrid = 0;

            for (int j = 0; j < position.length; j++){
                if (position[j][0] == -1){
                    rowAboveBoard[0][position[j][1]] = true;
                }
                else if (position[j][0] >= 0){
                    insideGrid++;
                    board[position[j][0]][position[j][1]] = true;
                    int[] arrayElement = new int[] {position[j][0], position[j][1]};
                    tempInsideGridArray.add(arrayElement);
                }
            }
            int[][] insideGridArray = new int[insideGrid][2];
            for (int k = 0; k < insideGrid; k++){
                insideGridArray[k] = tempInsideGridArray.get(k);
            }
            return insideGridArray;
        }

    /*
     * Helper function to determine if all the elevated blocks can fit partially into the grid
     */
    private boolean canFitIntoGrid(int[][] pos) {
        for (int i = 0; i < pos.length; i++) {
            if (pos[i][0] >= 0) {
                if (board[pos[i][0]][pos[i][1]]) {
                    return false;
                }
            }
        }
        return true;
    }


     /*
     * Checks if the row is full
     */
    boolean isRowFull(int row){
        for (int col = 0; col < NUMCOLUMN; col++){
            if (!board[row][col]){
                return false;
            }
        }
        return true;
    }

    /*
     * returns a list of full rows, clears each row, and moves each board row down
     */
    List<Integer> manageFullRows(){
        List<Integer> listOfFullRows = new ArrayList<Integer>();

        for (int row = 0; row < NUMROW; row++){

            if (isRowFull(row)){
                listOfFullRows.add(row);
                clearRow(row);
                moveRowsDown(row);
            }
        }
        return listOfFullRows;
    }


    //clears the board row so we don't run into any errors with checking the validity of a space in the grid
    private void clearRow(int rowIndex){
        for(int i = 0; i < board[rowIndex].length; i++) {
            board[rowIndex][i] = false;
        }
    }

    //Move everything above a certain down a row
    //E.g. if we have row 23 with row 22 above, we move row 22 to 23
    private void moveRowsDown(int row){
        for (int i = row - 1; i >= 0; i--){
            for(int j = 0; j < NUMCOLUMN; j++){
                board[i + 1][j] = board[i][j];
            }
        }

    }

    /*
     * Checks if Tetriminos have exceeded the top of the board
     */
    boolean reachBoardTop() {
        for (int col = 0; col < NUMCOLUMN; col++){

            if (rowAboveBoard[0][col]){
                clearBoard();
                return true;
            }
        }
        return false;
    }
}


