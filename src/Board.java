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
    }

    void putTetrimino(Tetrimino tetrimino) {
        int[][] position = tetrimino.getPosition();
        //just to check if one of the coordinates are invalid
        for (int[] pos : position) {
            if (board[pos[0]][pos[1]]){
                placeTetriminoOutOfBounds(tetrimino);
                return;
            }
        }
        for (int[] pos : position) {
            board[pos[0]][pos[1]] = true;
        }
        //boardView.updateBoardView(board);
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


    boolean rowIsFull(int row){
        for (int col = 0; col < NUMCOLUMN; col++){
            if (!board[row][col]){
                return false;
            }
        }
        return true;
    }


    List<Integer> fullRows(){
        List<Integer> fullRows = new ArrayList<Integer>();
        for (int row = 0; row < NUMROW; row++){
            if (rowIsFull(row)){
                fullRows.add(row);
                clearRow(row);
                moveRowsDown(row);
                //System.out.println("Destroyed row: " + row);
            }
        }
        return fullRows;
    }

    //We need to see that a row is full
    //add it to a list of full rows to pass to the controller
    //clear the row on the board so it's empty
    //move all above rows down

    //clears the board row so we don't run into any errors with checking the validity of a space in the grid
    private void clearRow(int rowIndex){
        for(int i = 0; i < board[rowIndex].length; i++) {
            board[rowIndex][i] = false;
        }
    }

    //Move everything above row down
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
            if (rowAboveBoard[0][col]){
                return true;
            }
        }
        return false;
    }
    /*
    class Square{

        boolean isEmpty;
        Color color;

        Square(){
            isEmpty = true;
            color = Color.WHITE;
        }
    }*/
}


