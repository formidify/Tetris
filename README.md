final-project-final-project-c created by GitHub Classroom

Sam Chen, Weijia Ma, James Yang, Qimeng Yu

This is the game of Tetris. Tetris is a tile-matching puzzle game originally designed by Alexey Pajitnov. In Tetris, several types of blocks, called Tetriminos, fall from the top of the screen and the user tries to fill an entire row of Tetriminos. The objective of the game is to fill and clear as many rows as possible before the Tetriminos fill up the screen. 

Game controls:
UP rotates the Tetrimino counterclockwise
DOWN speeds up the Tetrimino's movement down
LEFT shifts the Tetrimino left
RIGHT shifts the Tetrimino right
SPACE drops the Tetrimino all the way to the bottom

This game follows the MVC paradigm. There are several display classes that communicate to the controller class which in turn modifies the model classes. The model contains the underlying game logic of Tetris and controls the attributes of each Tetrimino. The view contains the graphical elements of the game and the controller mediates between the view and the model.  

Run Tetris.java to execute the game. 

KNOWN BUGS: Sometimes if there is a overhanging Tetrimino and you try to rotate the currently falling Tetrimino underneath it, it will jump to the top of the overhanging Tetrimino. This rarely occurs. 
