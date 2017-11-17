/**
 * Created by chens5 on 11/6/17.
 */

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.scene.shape.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.layout.*;


import static javafx.scene.paint.Color.*;

public class BoardDisplay {

    private static final double SCENE_WIDTH = 700;
    private static final double SCENE_HEIGHT = 800;
    private static final int TETRIMINO_DIM = 25;
    private static final double NEXTTETRIMINO_DIM = 50;
    private static final double BOARD_WIDTH = TETRIMINO_DIM * Board.NUMCOLUMN;
    private static final double BOARD_HEIGHT = TETRIMINO_DIM * Board.NUMROW;

    BorderPane root;
    Pane gameGrid;
    BorderPane score;
    BorderPane nextTetrimino;
    Rectangle[][] grid;
    int currentScore;



    public void mainScene(Stage primaryStage, TetrisController tetrisController) {

        //necessary to instantiate all these variables here because otherwise when
        //restart function is called, the old root and score and never cleared
        root = new BorderPane();
        score = new BorderPane();
        nextTetrimino = new BorderPane();
        gameGrid = new Pane();
        currentScore = 0;
        grid = new Rectangle[Board.NUMROW][Board.NUMCOLUMN];



        BorderStroke borderStroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, BorderWidths.DEFAULT);
        Border gameBorder = new Border(borderStroke);

        gameGrid.setMaxSize(BOARD_WIDTH, BOARD_HEIGHT);
        gameGrid.setMinSize(BOARD_WIDTH, BOARD_HEIGHT);
        gameGrid.setBorder(gameBorder);

        drawGrid();

        HBox title = addTitle();
        score = addScoreDisplay();
        nextTetrimino = addNextTetriminoDisplay();

        root.setCenter(gameGrid);
        root.setLeft(nextTetrimino);
        root.setRight(score);
        root.setTop(title);


        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

        /*
         * Updates the tetrisController whenever a key is pressed
         */
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if((key.getCode()== KeyCode.LEFT) ||
                    (key.getCode() == KeyCode.RIGHT) ||
                    (key.getCode() == KeyCode.UP) ||
                    (key.getCode() == KeyCode.DOWN) ||
                    (key.getCode() == KeyCode.SPACE)) {
               tetrisController.respondToKey(key.getCode());
            }
        });

        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void drawGrid(){

        for(int i = 0; i < Board.NUMCOLUMN; i++){
            Line colLine = new Line();
            colLine.setStartX(TETRIMINO_DIM * i);
            colLine.setStartY(0);
            colLine.setEndX(TETRIMINO_DIM * i);
            colLine.setEndY(BOARD_HEIGHT);
            colLine.setFill(Color.rgb(200, 200, 200));
            colLine.setStrokeWidth(0.6);

            gameGrid.getChildren().add(colLine);
        }

        for(int i = 0; i < Board.NUMROW; i++){
            Line rowLine = new Line();
            rowLine.setStartX(0);
            rowLine.setStartY(TETRIMINO_DIM * i);
            rowLine.setEndX(BOARD_WIDTH);
            rowLine.setEndY(TETRIMINO_DIM * i);
            rowLine.setFill(Color.rgb(200, 200, 200));
            rowLine.setStrokeWidth(0.6);

            gameGrid.getChildren().add(rowLine);
        }
    }

    /*
     * Draws a tetrimino in the pane using rectangles. We base the position of the rectangles on
     * the position given by the tetrimino class.
     * @param: tetrimino to draw
     */
    void drawTetrimino(Tetrimino tetrimino){
        int [][] position = tetrimino.getPosition();
        for (int i=0; i < position.length; i++){
            int row = position[i][0];
            int col = position[i][1];
            int x = TETRIMINO_DIM * col;
            int y = TETRIMINO_DIM * row;
            grid[row][col] = new Rectangle(x, y, TETRIMINO_DIM, TETRIMINO_DIM);
            grid[row][col].setFill(tetrimino.getColor());
            grid[row][col].setStroke(BLACK);
            gameGrid.getChildren().add(grid[row][col]);
        }
    }

    /*
     * Undraws the tetrimino.
     * @param: tetrimino to clear
     */
    void undrawTetrimino(Tetrimino tetrimino) {
        int[][] position = tetrimino.getPosition();
        for (int i=0; i < position.length; i++){
            int row = position[i][0];
            int col = position[i][1];
            gameGrid.getChildren().remove(grid[row][col]);
            grid[row][col] = null;
        }
    }

    /*
     * Clears a row in the board
     * @param: row to clear
     */
     void clearLine(int row){
         for (int col = 0; col < Board.NUMCOLUMN; col++){
             if(grid[row][col] != null){
                 gameGrid.getChildren().remove(grid[row][col]);
                 grid[row][col] = null;
             }
         }
     }

    /*
     * Moves a row above given parameter down in the board
     * @param: row to move down
     */
    void moveRow(int row) {
        for (int i = row - 1; i >= 0; i--) {
            for (int j = 0; j < Board.NUMCOLUMN; j++) {
                if (grid[i][j] != null) {

                    if (grid[i+1][j] != null){
                        grid[i+1][j] = null;
                        gameGrid.getChildren().remove(grid[i+1][j]);
                    }

                    grid[i+1][j] = grid[i][j];
                    gameGrid.getChildren().remove(grid[i][j]);
                    grid[i][j] = null;
                    grid[i+1][j].setY(grid[i+1][j].getY() + 25);
                    gameGrid.getChildren().add(grid[i+1][j]);

                }
            }
        }


    }

    /*
     * Constructing score displayer
     * @return: BorderPane with top set to the title "SCORE" and the center containing the value of the score
     */
    BorderPane addScoreDisplay(){
        BorderPane scoreBox = new BorderPane();
        scoreBox.setPadding(new Insets(30,30,10,0));

        Text scoreTitle = new Text("SCORE");
        Text score = new Text("0");
        score.setFont(Font.font("Chalkduster", FontWeight.BOLD, 60));
        scoreTitle.setFont(Font.font("Chalkduster", FontWeight.BOLD, 40));

        scoreBox.setTop(scoreTitle);
        scoreBox.setCenter(score);

        return scoreBox;
    }

    /*
     * Constructing the next tetrimino display
     * @return: BorderPane with top set to the title "NEXT" and the center containing the next tetrimino
     */
    BorderPane addNextTetriminoDisplay(){
        BorderPane tetriminoBox = new BorderPane();
        tetriminoBox.setPadding(new Insets(30,0,10,50));

        Text title = new Text("NEXT");
        title.setFont(Font.font("Chalkduster", FontWeight.BOLD, 40));
        tetriminoBox.setTop(title);

        Pane next = new Pane();
        next.setMaxSize(NEXTTETRIMINO_DIM, NEXTTETRIMINO_DIM);
        next.setMinSize(NEXTTETRIMINO_DIM, NEXTTETRIMINO_DIM);
        tetriminoBox.setCenter(next);

        return tetriminoBox;

    }

    /*
     * Constructing the title
     * @return: HBox containing title
     */
    HBox addTitle(){
        HBox titleBox = new HBox();
        titleBox.setPadding(new Insets(30,0,0,0));
        titleBox.setAlignment(Pos.CENTER);
        Text title  = new Text("TETRIS");
        title.setFont(Font.font("Chalkduster", FontWeight.BOLD, 60));
        titleBox.getChildren().add(title);

        return titleBox;
    }

    /*
     * Update the display for next tetrimino
     * @param: a tetrimino object
     */
    void updateNextTetrimino(Tetrimino tetrimino){

        int[][] coord = tetrimino.getRelativePosition();
        Pane next = (Pane) nextTetrimino.getChildren().get(1);
        if (!next.getChildren().isEmpty()){
            next.getChildren().clear();
        }


        for (int i = 0; i < coord.length; i++){            
            int blockSize = (int) NEXTTETRIMINO_DIM / 2;

            int x = blockSize * (1 + coord[i][1]);
            int y = blockSize * (1 + coord[i][0]);
            Rectangle rec = new Rectangle(x, y, blockSize, blockSize);
            rec.setFill(tetrimino.getColor());
            rec.setStroke(BLACK);
            next.getChildren().add(rec);
        }
    }


    /*
     * Update the display of the new score
     * @param: new score
     */
    void updateScore(int newScore){
        currentScore += newScore;
        Text change = (Text) score.getChildren().get(1);
        change.setText(Integer.toString(currentScore));
    }


}
