import java.util.Random;

public class Tetrimino {

    private static String[] shapes = {"Straight", "Square", "L", "J", "Z", "S", "T"};
    private static int[] orientations = {0,1,2,3};

    String shape;
    int orientation;
    private int[] center;
    private int[][] position;
    double speed;
    private double[] color;
    Board mainBoard;
    BoardDisplay boardView;
    boolean landing; // vs falling

    Tetrimino(Board board){
        speed = 1;
        shape = shapes[new Random().nextInt(8)];
        orientation = orientations[0];
        center = new int[] {1,5};
        position = new int[4][2];
        color = new double[] {0,0,0,0};
        mainBoard = board;
        boardView = board.boardView;
        landing = false;

    }

    private int[][] getRelativePosition() {
        int[][] relativePosition = new int[4][2];
        switch (shape) {
            case "Straight":
                switch (orientation) {
                    case 0:
                        relativePosition = new int[][] {{-1,0}, {0,0}, {1,0}, {2,0}};
                        break;
                    default:
                        break;
                }
                break;
            case "Square":
                switch (orientation) {
                    case 0:
                        relativePosition = new int[][] {{-1,-1}, {-1,0}, {0,-1}, {0,-0}};
                        break;
                    default:
                        break;
                }
                break;
            case "L":
                switch (orientation) {
                    case 0:
                        relativePosition = new int[][] {{-1,-1}, {-1,0}, {0,0}, {1,0}};
                        break;
                    default:
                        break;
                }
                break;
            case "J":
                switch (orientation) {
                    case 0:
                        relativePosition = new int[][] {{-1,1}, {-1,0}, {0,0}, {1,0}};
                        break;
                    default:
                        break;
                }
                break;
            case "Z":
                switch (orientation) {
                    case 0:
                        relativePosition = new int[][] {{-1,-1}, {-1,0}, {0,0}, {0,1}};
                        break;
                    default:
                        break;
                }
                break;
            case "S":
                switch (orientation) {
                    case 0:
                        relativePosition = new int[][] {{-1,1}, {-1,0}, {0,0}, {-1,0}};
                        break;
                    default:
                        break;
                }
                break;
            case "T":
                switch (orientation) {
                    case 0:
                        relativePosition = new int[][] {{-1,0}, {0,-1}, {0,0}, {0,1}};
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

    public int[][] getPosition(){
        return position;
    }

    private void updatePosition() {
        int[][] relativePosition = getRelativePosition();
        for (int i = 0; i < 4; i++){
            int[] relPos = relativePosition[i];
            position[i] = new int[] {center[0] + relPos[0], center[1] + relPos[1]};
        }
        // Tell view
    }

    // Update center after change in x and y
    public void translate(int deltaRow, int deltaCol){
        int row = center[0] + deltaRow;
        int col = center[1] + deltaCol;
        // Check whether the tetrimino is moving out of the board
        // TODO: this over limit the tetrimino depending on where the center is for each tetrimino
        if (row < 2){
            row = 2;
        } else if (row > mainBoard.NUMCOLUMN - 3) {
            row = mainBoard.NUMCOLUMN - 3;
        }

        updateCenter(row, col);
    }

    public void rotate(){
    }

    public void fallByOneSquare() {
        translate(1, 0);
    }

    private void updateCenter(int row, int col) {
        setCenter(row, col);
        updatePosition();
    }

    public boolean landed() {
        for (int[] aPosition : position) {
            int row = aPosition[0];
            int col = aPosition[1];
            if (row == mainBoard.NUMROW - 1) {
                return true;
            }
            if (!mainBoard.board[row + 1][col].isEmpty){
                return true;
            }
        }
        return false;
    }



    double[] getColor() {
        return color;
    }
}
