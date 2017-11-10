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

    void putTetrimino(Tetrimino tetrimino) {
        int[][] position = tetrimino.getPosition();
        for (int[] pos : position) {
            Square square = board[pos[0]][pos[1]];
            square.color = tetrimino.getColor();
            square.isEmpty = false;
        }
        //boardView.updateBoardView(board);
    }

    boolean rowIsFull(int row){
        for (int col = 0; col < NUMCOLUMN; col++){
            if (board[row][col].isEmpty){
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
            }
        }
        return fullRows;
    }

    boolean reachBoardTop() {
        // TODO: wrong algorithm, should return true only when the tetris exceed the top
        for (int col = 0; col < NUMCOLUMN; col++){
            if (! board[0][col].isEmpty){
                return true;
            }
        }
        return false;
    }

    class Square{

        boolean isEmpty;
        Color color;

        Square(){
            isEmpty = true;
            color = Color.WHITE;
        }
    }
}


