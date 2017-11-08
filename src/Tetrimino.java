public class Tetrimino {

    static String[] shapes = {"Straight", "Square", "L", "J", "Z", "S", "T"};
    static int[] orientations = {0,1,2,3};

    String shape;
    int orientation;
    private int[] center;
    private int[][] position;
    double speed;
    private double[] color;

    Tetrimino(){
        speed = 1;
        shape = shapes[0];
        orientation = orientations[0];
        center = new int[] {5,0};
        position = getPosition();
        color = new double[] {0,0,0,0};
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

    public int[] getCenter(){
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
        // Tell controller
    }

    public void translate(int[] deltaXY){
        updateCenter(deltaXY);
    }

    private void updateCenter(int[] deltaXY) {
        int[] newCenter = new int[] {center[0] + deltaXY[0], center[1] + deltaXY[1]};
        setCenter(newCenter);
    }

    boolean land() {
        // Tell controller
        return true;
    }

    public double[] getColor() {
        return color;
    }
}
