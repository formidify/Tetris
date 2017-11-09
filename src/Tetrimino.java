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
        shape = shapes[0];
        orientation = orientations[0];
        center = new int[] {5,0};
        position = getPosition();
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
        updatePostion();
    }

    public int[][] getPosition(){
        return position;
    }

    private void updatePostion() {
        int[][] relativePosition = getRelativePosition();
        for (int i = 0; i < position.length; i++){
            int[] relPos = relativePosition[i];
            position[i] = new int[] {center[0] + relPos[0], center[1] + relPos[1]};
        }
        // Tell view
    }

    public void translate(int[] deltaXY){
        updateCenter(deltaXY);
    }

    private void updateCenter(int[] deltaXY) {
        int[] newCenter = new int[] {center[0] + deltaXY[0], center[1] + deltaXY[1]};
        setCenter(newCenter);
    }

    public boolean land() {
        for (int[] aPosition : position) {
            int row = aPosition[0];
            int col = aPosition[1];
            if (row == mainBoard.NUMROW - 1 || !mainBoard.board[row + 1][col].isEmpty) {
                return true;
            }
        }
        return false;
    }

    public void fall() {
        // TODO: to be fixed after the timer is added in
        translate(new int[] {0, (int) speed});
    }

    double[] getColor() {
        return color;
    }
}
