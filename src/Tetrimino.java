import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.Random;

/**
 * Creates the Tetrimino for Tetris. This controls the shape, orientation, and color of the Tetrimino.
 * This also handles all Tetrimino movements. This is an observer of the Board class as the Tetrimino
 * uses the Board to calculate position and check if it is in a valid space in the Board.
 */

public class Tetrimino {

    private static String[] shapes = {"Straight", "Square", "L", "J", "Z", "S", "T"};
    private static int[] orientations = {0,1,2,3};

    String shape;
    int orientation;
    private int[] center;
    int[][] position;
    double speed;
    private Color color;
    Board mainBoard;
    BoardDisplay boardView;
    boolean landing;

    Tetrimino(Board board){
        speed = 1;
        shape = shapes[new Random().nextInt(7)];
        orientation = 0;
        center = new int[] {1,5};
        position = new int[4][2];
        setPosition();
        color = chooseColor();
        mainBoard = board;
        boardView = board.boardView;
        landing = false;

    }

    private Color chooseColor() {
        Color tetriminoColor = Color.BLACK;
        switch (shape) {
            case "Straight":
                tetriminoColor = Color.LIGHTBLUE;
                break;
            case "Square":
                tetriminoColor = Color.GOLD;
                break;
            case "L":
                tetriminoColor = Color.ORANGE;
                break;
            case "J":
                tetriminoColor = Color.BLUE;
                break;
            case "Z":
                tetriminoColor = Color.GREEN;
                break;
            case "S":
                tetriminoColor = Color.RED;
                break;
            case "T":
                tetriminoColor = Color.PURPLE;
                break;
            default:
                break;
        }
        return tetriminoColor;
    }

    /*
     * Returns a 2D array detailing the relative position of a Tetrimino
     * The relative position dictates the shape of the Tetrimino and is used to
     * calculate the actual position of the Tetrimino in the grid.
     */
    int[][] getRelativePosition() {
        int[][] relativePosition = new int[4][2];
        switch (shape) {
            case "Straight":
                switch (orientation) {
                    case 0: case 2:
                        relativePosition = new int[][] {{0,0}, {-1,0}, {1,0}, {2,0}};
                        break;
                    case 1: case 3:
                        relativePosition = new int[][] {{0,0}, {0,-1}, {0,1}, {0,2}};
                        break;
                    default:
                        break;
                }
                break;
            case "Square":
                switch (orientation) {
                    case 0: case 1: case 2: case 3:
                        relativePosition = new int[][] {{0,0}, {-1,-1}, {-1,0}, {0,-1}};
                        break;
                    default:
                        break;
                }
                break;
            case "L":
                switch (orientation) {
                    case 0:
                        relativePosition = new int[][] {{0,0}, {-1,-1}, {-1,0}, {1,0}};
                        break;
                    case 1:
                        relativePosition = new int[][] {{0,0}, {0,-1}, {0,1}, {-1,1}};
                        break;
                    case 2:
                        relativePosition = new int[][] {{0,0}, {-1,0}, {1,0}, {1,1}};
                        break;
                    case 3:
                        relativePosition = new int[][] {{0,0}, {0,-1}, {1,-1}, {0,1}};
                        break;
                    default:
                        break;
                }
                break;
            case "J":
                switch (orientation) {
                    case 0:
                        relativePosition = new int[][] {{0,0}, {-1,1}, {-1,0}, {1,0}};
                        break;
                    case 1:
                        relativePosition = new int[][] {{0,0}, {0,-1}, {0,1}, {1,1}};
                        break;
                    case 2:
                        relativePosition = new int[][] {{0,0}, {1,-1}, {1,0}, {-1,0}};
                        break;
                    case 3:
                        relativePosition = new int[][] {{0,0}, {-1,-1}, {0,-1}, {0,1}};
                        break;
                    default:
                        break;
                }
                break;
            case "Z":
                switch (orientation) {
                    case 0: case 2:
                        relativePosition = new int[][] {{0,0}, {-1,-1}, {-1,0}, {0,1}};
                        break;
                    case 1: case 3:
                        relativePosition = new int[][] {{0,0}, {-1,1}, {0,1}, {1,0}};
                        break;
                    default:
                        break;
                }
                break;
            case "S":
                switch (orientation) {
                    case 0: case 2:
                        relativePosition = new int[][] {{0,0}, {-1,1}, {-1,0}, {0,-1}};
                        break;
                    case 1: case 3:
                        relativePosition = new int[][] {{0,0}, {-1,-1}, {0,-1}, {1,0}};
                        break;
                    default:
                        break;
                }
                break;
            case "T":
                switch (orientation) {
                    case 0:
                        relativePosition = new int[][] {{0,0}, {-1,0}, {0,-1}, {0,1}};
                        break;
                    case 1:
                        relativePosition = new int[][] {{0,0}, {-1,0}, {0,1}, {1,0}};
                        break;
                    case 2:
                        relativePosition = new int[][] {{0,0}, {1,0}, {0,-1}, {0,1}};
                        break;
                    case 3:
                        relativePosition = new int[][] {{0,0}, {-1,0}, {0,-1}, {1,0}};
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        return relativePosition;

    }

    int[][] getPosition(){

        return position;
    }

    /*
     * setting position in the beginning
     */
    private void setPosition(){
        for(int i = 0; i < position.length; i++)
        {
            position[i][0] = getRelativePosition()[i][0] + 1;
            position[i][1] = getRelativePosition()[i][1] + 5;
        }
    }


    /*
     * Shifting to right based on the position
     */
    void translateRight() {
        // instantiate a new array for new coordinates responding to right key
        int[][] tempArray = new int[4][2];
        for (int i = 0; i < 4; i++) {
            tempArray[i][0] = position[i][0];
            tempArray[i][1] = position[i][1]+1;
        }

        for (int i = 0; i < 4; i++) {
            if (!checkValid(tempArray[i][0], tempArray[i][1])) {
                return;
            }
        }
        if (center[1] < 9){
            center[1]++;
        }
        position = tempArray;
    }

    /*
     * Shifting to left based on the position
     */
    void translateLeft() {
        // instantiate a new array for new coordinates responding to left key
        int[][] tempArray = new int[4][2];
        for (int i = 0; i < 4; i++) {
            tempArray[i][0] = position[i][0];
            tempArray[i][1] = position[i][1]-1;
        }

        for (int i = 0; i < 4; i++) {
            if (!checkValid(tempArray[i][0], tempArray[i][1])) {
                //System.out.println(position[i][0] +  " " + position[i][1]);
                return;
            }
        }
        if (center[1] > 1){
            center[1]--;
        }
        position = tempArray;
    }

    /*
     * Shifting down based on the position
     */
    void translateDown() {
        int[][] tempArray = new int[4][2];
        for (int i = 0; i < 4; i++) {
            tempArray[i][0] = position[i][0]+1;
            tempArray[i][1] = position[i][1];
        }
        for (int i = 0; i < 4; i++) {
            if (!checkValid(tempArray[i][0], tempArray[i][1])) {
                return;
            }
        }
        center[0]++;
        position = tempArray;
    }

    /*
    * rotation works for all tetriminos.
    */
    void rotate(){
        orientation = (orientation + 1) % 4;
        updatePosition();
    }

    /*
     * Updating the position based on the center.
     * Used after rotation.
     */
    private void updatePosition() {
        int[][] relativePosition = getRelativePosition();
        for (int i = 0; i < 4; i++){
            int[] relPos = relativePosition[i];
            position[i] = new int[] {center[0] + relPos[0], center[1] + relPos[1]};
        }
    }

    /*
    * Automatic fall to bottom with space button
    */
    void fallToBottom() {
        while (!landed()) {
            translateDown();
        }
    }

    /*
     * Checks if the Tetrimino is in a valid space
     */
    private boolean checkValid(int x, int y) {
        if (x < 0 || x > Board.NUMROW - 1 || y < 0 || y > Board.NUMCOLUMN - 1) {
            return false;
        }
        if (mainBoard.board[x][y]) {
            return false;
        }
        return true;
    }

    /*
     * Check if a Tetrimino is is either at the bottom of the grid
     * or on top of another Tetrimino.
     */
    boolean landed() {
        for (int[] aPosition : position) {
            int row = aPosition[0];
            int col = aPosition[1];
            if (row == Board.NUMROW - 1) {
                return true;
            }
            if (!ifSameTetrimino(row + 1, col) && mainBoard.board[row + 1][col]){
                return true;
            }
        }
        return false;
    }

    /*
     * Helper method that checks if the block below another is also part of the same tetrimino
     */
    private boolean ifSameTetrimino(int row, int col){
        for (int[] aPosition : position) {
            if (aPosition[0] == row && aPosition[1] == col){
                return true;
            }
        }
        return false;
    }

    Color getColor() {
        return color;
    }

}