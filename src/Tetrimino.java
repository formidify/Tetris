import javafx.scene.paint.Color;

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
        shape = shapes[new Random().nextInt(7)];
        orientation = 0;
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

    int[] getCenter(){
        return center;
    }

    private void setCenter(int row, int col){
        center = new int[] {row, col};
    }

    int[][] getPosition(){
        return position;
    }

    private void setPosition(){
        for(int i = 0; i < position.length; i++)
        {
            position[i][0] = getRelativePosition()[i][0] + 1;
            position[i][1] = getRelativePosition()[i][1] + 5;
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
     * Shifting to right based on the position
     */
    boolean newTranslateRight()
    {
        //shifting to right
        for(int i = 0; i < 4; i++) {
            if (!checkValid(position[i][0], 1+position[i][1])) {
                return false;
            }
            position[i][1]++;
        }
        return true;
    }

    boolean newTranslateLeft()
    {
        //shifting to right
        for(int i = 0; i < 4; i++) {
            if (!checkValid(position[i][0], 1+position[i][1])) {
                return false;
            }
            position[i][1]--;
        }
        return true;
    }

    void newTranslateDown()
    {
        for(int i = 0; i < 4; i++) {
            if (checkValid(position)) {
                position[i][0]++;
            }
        }
    }

    boolean  checkValid(int[][] posArray)
    {
        for(int i = 0; i < 4; i++)
        {
            if(posArray[i][0] < 0 || posArray[i][0] > 23 || posArray[i][1] <0 || posArray[i][1] > 11){
                return false;
            }
            if (!mainBoard[posArray[i][0]][posArray[i][1]].isEmpty){
                return false;
            }
        }
        return true;
    }

    void rotate(){
        orientation = (orientation + 1) % 4;
    }

    void oldFallByOne(){translate(0, 1);}

    void fallByOneSquare() {
        newTranslateDown();
    }

    void fallToBottom() {
        // TODO: add in a private var that tracks the bottom most row and replace center[0] with it
        translate(Board.NUMROW - center[0]-3,0);
    }

    /*
     * TODO: Fix landing because it doesn't work for most tetriminos
     */
    boolean landed() {
        for (int[] aPosition : position) {
            int row = aPosition[0];
            int col = aPosition[1];
            if (row == Board.NUMROW - 2) {
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
