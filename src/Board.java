import java.awt.*;
import java.util.*;
import java.util.List;

import javafx.scene.paint.Color;
public class Board {
    public static final int NUMCOLUMN = 12;
    public static final int NUMROW = 24;

    boolean[][] board;
    boolean[][] rowAboveBoard;
    BoardDisplay boardView;


    Board(BoardDisplay boardDisplay){
        rowAboveBoard = new boolean[1][NUMCOLUMN];
         board = new boolean[NUMROW][NUMCOLUMN];
         for (int i = 0; i < NUMROW; i++){
             for (int j = 0; j < NUMCOLUMN; j++){
                 board[i][j] = false;
                 if (i == 0){
                     rowAboveBoard[i][j] = false;
                 }
             }
         }
         boardView = boardDisplay;
    }
    void removeTetrimino(Tetrimino tetrimino){
        int[][] position = tetrimino.getPosition();
        for (int[] pos : position) {
            board[pos[0]][pos[1]] = false;
        }
        boardView.undrawTetrimino(tetrimino);
    }

    void clearBoard(){
        for (int i = 0; i < NUMROW; i++){
            for (int j = 0; j < NUMCOLUMN; j++){
                board[i][j] = new Square();
            }
        }
    }

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
        while (!canFit(position)){
            for (int i = 0; i < position.length; i++){
                position[i][0]--;
                if (position[i][0] < 0){
                    outOfGrid++;
                }
            }
            //if all four are out of the grid, no need to elevate any more, just quit the while loop
            if (outOfGrid == position.length){
                break;
            }
            //reset outOfGrid
            outOfGrid = 0;
        }
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
        //set it to tetrimino's new position
        tetrimino.position = insideGridArray;
    }

    /*
<<<<<<< HEAD
     * Helper function to determine if all the elevated blocks can fit partially into the grid
     */
    private boolean canFit(int[][] pos) {
        for (int i = 0; i < pos.length; i++) {
            if (pos[i][0] >= 0) {
                if (board[pos[i][0]][pos[i][1]]) {
                    return false;
                }
            }
        }
        return true;
    }


=======
     * Checks if the row is full
     */
>>>>>>> 46fd1c4c2735396b3a7b8ab9d8d8a1ad0455ac97
    boolean rowIsFull(int row){
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
    List<Integer> fullRows(){
        List<Integer> listOfFullRows = new ArrayList<Integer>();

        for (int row = 0; row < NUMROW; row++){

            if (rowIsFull(row)){
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

    boolean reachBoardTop() {
        // TODO: wrong algorithm, should return true only when the tetris exceed the top
        for (int col = 0; col < NUMCOLUMN; col++){
<<<<<<< HEAD
            if (rowAboveBoard[0][col]){
=======
            if (! board[0][col].isEmpty){
                clearBoard();
>>>>>>> 46fd1c4c2735396b3a7b8ab9d8d8a1ad0455ac97
                return true;
            }
        }
        return false;
    }
<<<<<<< HEAD
    /*
=======

    //Do we really need this?
>>>>>>> 46fd1c4c2735396b3a7b8ab9d8d8a1ad0455ac97
    class Square{

        boolean isEmpty;
        Color color;

        Square(){
            isEmpty = true;
            color = Color.WHITE;
        }
    }*/
}


