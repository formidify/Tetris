import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.Random;

public class Tetrimino {

    private static String[] shapes = {"Straight", "Square", "L", "J", "Z", "S", "T"};
    private static int[] orientations = {0,1,2,3};

    String shape;
    int orientation;
    private int[] center;
    private int[][] position;
    double speed;
    private Color color;
    Board mainBoard;
    BoardDisplay boardView;
    boolean landing; // vs falling

    Tetrimino(Board board){
        speed = 1;
        shape = shapes[0];
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
                tetriminoColor = Color.YELLOW;
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

    int[][] getRelativePosition() {
        int[][] relativePosition = new int[4][2];
        switch (shape) {
            case "Straight":
                switch (orientation) {
                    case 0:
                        relativePosition = new int[][] {{0,0}, {-1,0}, {1,0}, {2,0}};
                        break;
                    case 1:
                        relativePosition = new int[][] {{0,0}, {0,-1}, {0,1}, {0,2}};
                        break;
                    case 2:
                        relativePosition = new int[][] {{0,0}, {-1,0}, {1,0}, {2,0}};
                        break;
                    case 3:
                        relativePosition = new int[][] {{0,0}, {0,-1}, {0,1}, {0,2}};
                        break;
                    default:
                        break;
                }
                break;
            case "Square":
                switch (orientation) {
                    case 0:
                        relativePosition = new int[][] {{0,0}, {-1,-1}, {-1,0}, {0,-1}};
                        break;
                    case 1:
                        relativePosition = new int[][] {{0,0}, {-1,-1}, {-1,0}, {0,-1}};
                        break;
                    case 2:
                        relativePosition = new int[][] {{0,0}, {-1,-1}, {-1,0}, {0,-1}};
                        break;
                    case 3:
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
                    case 0:
                        relativePosition = new int[][] {{0,0}, {-1,-1}, {-1,0}, {0,1}};
                        break;
                    case 1:
                        relativePosition = new int[][] {{0,0}, {-1,1}, {0,1}, {1,0}};
                        break;
                    case 2:
                        relativePosition = new int[][] {{0,0}, {-1,-1}, {-1,0}, {0,1}};
                        break;
                    case 3:
                        relativePosition = new int[][] {{0,0}, {-1,1}, {0,1}, {1,0}};
                        break;
                    default:
                        break;
                }
                break;
            case "S":
                switch (orientation) {
                    case 0:
                        relativePosition = new int[][] {{0,0}, {-1,1}, {-1,0}, {0,-1}};
                        break;
                    case 1:
                        relativePosition = new int[][] {{0,0}, {-1,-1}, {0,-1}, {1,0}};
                        break;
                    case 2:
                        relativePosition = new int[][] {{0,0}, {-1,1}, {-1,0}, {0,-1}};
                        break;
                    case 3:
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

    int[] getCenter(){
        return center;
    }

    private void setCenter(int row, int col){
        center = new int[] {row, col};
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
    void newTranslateRight() {
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
        center[1]++;
        position = tempArray;
    }

    /*
     * Shifting to left based on the position
     */
    void newTranslateLeft() {
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
        center[1]--;
        position = tempArray;
    }

    void newTranslateDown() {
        // instantiate a new array for new coordinates responding to left key
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

    boolean checkValid(int x, int y) {
        if (x < 0 || x > 23 || y < 0 || y > 11) {
            return false;
        }
        if (!mainBoard.board[x][y].isEmpty) {
            return false;
        }
        return true;
    }

    /*
     * fixed automatic fall to bottom with space button
     */
    void fallToBottom() {
        while (!landed()) {
            newTranslateDown();
        }
    }

    /*
     * rotation works for all tetriminos.
     */
    void rotate(){
        orientation = (orientation + 1) % 4;
        updatePosition();
    }

    private void updatePosition() {
        int[][] relativePosition = getRelativePosition();
        for (int i = 0; i < 4; i++){
            int[] relPos = relativePosition[i];
            position[i] = new int[] {center[0] + relPos[0], center[1] + relPos[1]};
        }
    }


    // Update center after change in x and y
    void translate(int deltaRow, int deltaCol){
        // translating horizontally
        int col = center[1] + deltaCol;
        // Check whether the tetrimino is moving out of the board
        // TODO: this over limit the tetrimino depending on where the center is for each tetrimino
        if (col < 1){
            col = 1;
        } else if (col > Board.NUMCOLUMN - 2) {
            col = Board.NUMCOLUMN - 2;
        }

        // translating vertically
        int row = center[0];
        for (int i = 0; i < deltaRow; i++) {
            if (!landed()){
                row++;
            }

        }
    }



    /*
     * TODO: Fix landing because it doesn't work for most tetriminos
     */
    boolean landed() {
        System.out.println("landed called");
        for (int[] aPosition : position) {
            int row = aPosition[0];
            int col = aPosition[1];
            if (row == Board.NUMROW - 1) {
                return true;
            }
            if (!mainBoard.board[row + 1][col].isEmpty){
                return true;
            }
        }
        return false;
    }

    Color getColor() {
        return color;
    }

}