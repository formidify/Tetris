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
        shape = shapes[2];
        orientation = orientations[0];
        center = new int[] {0,5};
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
            case "L":
                switch (orientation) {
                    case 0:
                        relativePosition = new int[][] {{-1,-1}, {-1,0}, {0,0}, {1,0}};
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

    private void setCenter(int[] xy){
        center = new int[] {xy[0], xy[1]};
        updatePosition();
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

    public void translate(int[] deltaXY){
        updateCenter(deltaXY);
    }

    private void updateCenter(int[] deltaRowCol) {
        int[] newCenter = new int[] {center[0] + deltaRowCol[0], center[1] + deltaRowCol[1]};
        setCenter(newCenter);
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

    public void fall() {
        // TODO: to be fixed after the timer is added in
        translate(new int[] {1,0});
    }

    double[] getColor() {
        return color;
    }
}
