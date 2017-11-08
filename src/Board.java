import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    public static final int NUMCOLUMN = 10;
    public static final int NUMROW = 20;

    Square[][] board;

    Board(){
         board = new Square[NUMCOLUMN][NUMROW];
    }

    void putTetrimino(Tetrimino tetrimino) {
        int[][] position = tetrimino.getPosition();
        for (int[] pos : position) {
            Square square = board[pos[0]][pos[1]];
            square.color = tetrimino.getColor();
            square.isEmpty = false;
        }
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

    public boolean reachBoardTop() {
        // TODO: wrong algorithm, should return true only when the tetris exceed the top
        for (int col = 0; col < NUMCOLUMN; col++){
            if (! board[0][col].isEmpty){
                return true;
            }
        }
        return false;
    }

    private class Square{

        boolean isEmpty;
        double[] color;

        Square(){
            isEmpty = true;
            color = new double[] {1,1,1,1};
        }
    }
}


