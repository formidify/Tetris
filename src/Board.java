import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.paint.Color;
public class Board {
    public static final int NUMCOLUMN = 12;
    public static final int NUMROW = 24;

    Square[][] board;
    BoardDisplay boardView;


    Board(BoardDisplay boardDisplay){
         board = new Square[NUMROW][NUMCOLUMN];
         for (int i = 0; i < NUMROW; i++){
             for (int j = 0; j < NUMCOLUMN; j++){
                 board[i][j] = new Square();
             }
         }
         boardView = boardDisplay;
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
        for (int[] pos : position) {
            Square square = board[pos[0]][pos[1]];
            square.color = tetrimino.getColor();
            square.isEmpty = false;
        }
        //boardView.updateBoardView(board);
    }

    /*
     * Checks if the row is full
     */
    boolean rowIsFull(int row){
        for (int col = 0; col < NUMCOLUMN; col++){
            if (board[row][col].isEmpty){
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
            board[rowIndex][i] = new Square();
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
            if (! board[0][col].isEmpty){
                clearBoard();
                return true;
            }
        }
        return false;
    }

    //Do we really need this?
    class Square{

        boolean isEmpty;
        Color color;

        Square(){
            isEmpty = true;
            color = Color.WHITE;
        }
    }
}


