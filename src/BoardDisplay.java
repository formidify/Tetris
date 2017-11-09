/**
 * Created by chens5 on 11/6/17.
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

import static javafx.scene.paint.Color.*;

public class BoardDisplay {
    Board gameBoard;
    Group root;
    Rectangle[][] grid;
    //TextArea status = new TextArea("");
    //Group gameGrid;

    public BoardDisplay(){
        root = new Group();
        grid = new Rectangle[24][12];
   }

    public void mainScene(Stage primaryStage) {
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


        drawTetrimino();
        clearLine(23);
        moveRow(23);
        Scene scene = new Scene(root, 300, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //Waiting on tetrimino class. But once we have 100% go on that, tetrimino will be a parameter
    public void drawTetrimino(){
        //Rectangle[] rectArray = new Rectangle[4];
        int [][] position = new int[][] {{23,1}, {23,2}, {23,3}, {22,1}};
        for (int i=0; i <4; i++){
            int col = position[i][0];
            int row = position[i][1];
            int x = 25* row;
            int y = 25* col;
            grid[col][row] = new Rectangle(x, y, 25, 25);
            grid[col][row].setFill(BLUE);
            grid[col][row].setStroke(BLACK);
            root.getChildren().add(grid[col][row]);
        }

    }

    public void clearLine(int row){
        for (int col =0; col <grid[0].length; col ++){
            if(grid[row][col] != null){
            root.getChildren().remove(grid[row][col]);}
        }
    }

    public void moveRow(int row) {
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

    public void scoreDisplay(){


    }
    public void nextTetriminoDisplay(){

    }

    public void updateTetrimino(Tetrimino teris){

    }


    private boolean isChanged(){
        return true;
    }

    private void updateNewSquares(){

    }

    private void colorSquares(){

    }

    private void displayBlocks(){

    }

    public void startDisplay(){

    }

    public void endDisplay(){

    }



    public void undrawTetrimino(Tetrimino tetrimino) {

    }

    public void updateBoardView(Board.Square[][] board) {

    }


}
