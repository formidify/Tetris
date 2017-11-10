/**
 * Created by chens5 on 11/6/17.
 */

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.shape.*;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Duration;


import static javafx.scene.paint.Color.*;

public class BoardDisplay {
    Board gameBoard;
    Pane root;
    Rectangle[][] grid;
    KeyCode keyPressed;
    //TextArea status = new TextArea("");
    //Group gameGrid;

    public BoardDisplay(){
        root = new Pane();
        grid = new Rectangle[24][12];
   }

    public void mainScene(Stage primaryStage, Controller controller) {
        //BorderPane root = new BorderPane();

//        gameGrid = new Group();
//
//        FlowPane pane = new FlowPane();
//        status.setText("Status messages will appear here.");
//        status.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 14));
//        status.setWrapText(true);
//        status.setPrefColumnCount(20);
//        pane.getChildren().add(status);
//
//        root.setLeft(pane);
//        root.setCenter(gameGrid);

//
//        drawTetrimino();
//        clearLine(23);
//        moveRow(23);

        Scene scene = new Scene(root, 300, 600);

        /*
         * Sets the variable keyPressed to the key code. The controller will use keyPressed
         */
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if((key.getCode()== KeyCode.LEFT) ||
                    (key.getCode() == KeyCode.RIGHT) ||
                    (key.getCode() == KeyCode.UP) ||
                    (key.getCode() == KeyCode.DOWN)) {
               controller.respondToKey(key.getCode());
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();

    }


    /*
     * Draws a tetrimino in the pane using rectangles. We base the position of the rectangles on
     * the position given by the tetrimino class.
     * @param: tetrimino to draw
     */
    void drawTetrimino(Tetrimino tetrimino){
        int [][] position = tetrimino.getPosition();
        for (int i=0; i < 4; i++){
            int row = position[i][0];
            int col = position[i][1];
            int x = 25* col;
            int y = 25* row;
            grid[row][col] = new Rectangle(x, y, 25, 25);
            grid[row][col].setFill(BLUE);
            grid[row][col].setStroke(BLACK);
            root.getChildren().add(grid[row][col]);
        }
    }

    /*
     * Undraws the tetrimino.
     * @param: tetrimino to clear
     */
    void undrawTetrimino(Tetrimino tetrimino) {
        int [][] position = tetrimino.getPosition();
        for (int i=0; i < 4; i++){
            int row = position[i][0];
            int col = position[i][1];
            root.getChildren().remove(grid[row][col]);
            grid[row][col] = null;
        }
    }

    /*
     * Clears a row in the board
     * @param: row to clear
     */
     void clearLine(int row){
        for (int col =0; col <grid[0].length; col ++){
            if(grid[row][col] != null){
            root.getChildren().remove(grid[row][col]);}
        }
    }

    /*
     * Moves a row down in the board
     * @param: row to move down
     */
    void moveRow(int row) {
        for (int i = row - 1; i >= 0; i--) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] != null) {
                    grid[i+1][j] = grid[i][j];
                    root.getChildren().remove(grid[i][j]);
                    grid[i][j] = null;
                    grid[i+1][j].setY(grid[i+1][j].getY() + 25);
                    root.getChildren().add(grid[i+1][j]);
                }
            }
        }
    }


    void scoreDisplay(){


    }
    void nextTetriminoDisplay(){

    }

    void updateTetrimino(Tetrimino teris){

    }


    boolean isChanged(){
        return true;
    }

    void updateNewSquares(){

    }

    private void colorSquares(){

    }

    private void displayBlocks(){

    }

    public void startDisplay(){

    }

    public void endDisplay(){

    }





    public void updateBoardView(Board.Square[][] board) {

    }


}
