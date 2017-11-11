import javafx.scene.paint.Color;

import java.util.Random;

public class Tetrimino {

    private static String[] shapes = {"Straight", "Square", "L", "J", "Z", "S", "T"};
    private static int[] orientations = {0,1,2,3};

    String shape;
    int orientation;
    private int[] center; // Each tetrimino has a particular square called center to better position them.
    private int[][] position;
    double speed;
    private Color color;
    Board mainBoard;
    BoardDisplay boardView;

    Tetrimino(Board board){
        speed = 1;
        shape = shapes[new Random().nextInt(7)];
        orientation = orientations[new Random().nextInt(4)];
        center = new int[] {1,5};
        position = new int[4][2];
        color = chooseColor();
        mainBoard = board;
        boardView = board.boardView;
    }

    int[] getCenter(){
        return center;
    }

    private void setCenter(int row, int col){
        center = new int[] {row, col};
    }

    private void updateCenter(int row, int col) {
        setCenter(row, col);
        updatePosition();
    }

    // Returns the position of the other squares relative the the center square
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
                        relativePosition = new int[][] {{0,0}, {-1,1}, {-1,0}, {0,1}};
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

    int[][] getPosition(){
        return position;
    }

    private void updatePosition() {
        int[][] relativePosition = getRelativePosition();
        for (int i = 0; i < 4; i++){
            int[] relPos = relativePosition[i];
            position[i] = new int[] {center[0] + relPos[0], center[1] + relPos[1]};
        }
    }

    // Methods moving the tetrimino

    // Update center after change in x and y
    void translate(int deltaRow, int deltaCol){
        // translating horizontally
        int col = center[1] + deltaCol;
        // Check whether the tetrimino is moving out of the board
        // TODO: this over limit the tetrimino depending on where the center is for each tetrimino
        if (col < 1){
            col = 1;
        } else if (col > mainBoard.NUMCOLUMN - 2) {
            col = mainBoard.NUMCOLUMN - 2;
        }
        // translating vertically
        int row = center[0];
        for (int i = 0; i < deltaRow; i++) {
            if (!landed()){
                row++;
            }

        }
        updateCenter(row, col);
    }

    void rotate(){
        orientation = (orientation + 1) % 4;
    }

    void fallByOneSquare() {
        translate(1, 0);
    }

    void fallToBottom() {
        // TODO: add in a private var that tracks the bottom most row and replace center[0] with it
        translate(mainBoard.NUMROW - center[0]-3,0);
    }

    // Check the status of the tetrimino: falling or landed

    boolean landed() {
        for (int[] aPosition : position) {
            int row = aPosition[0];
            int col = aPosition[1];
            if (row == mainBoard.NUMROW - 2) {
                return true;
            }
            if (!mainBoard.board[row + 1][col].isEmpty){
                return true;
            }
        }
        return false;
    }


    // Color related methods

    Color getColor() {
        return color;
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
}
