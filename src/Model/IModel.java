package Model;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;

public interface IModel {

    //Server
    void Start();
    void stopSerevers();

    //Maze Functions
    void generateMaze(int width,int height);
    Maze getMaze();
    Solution getSolution();
    void SetTheMaze(Maze maze);
    void solve();

    //All Charcher Func
    int getCharacterPositionRow();
    int getCharacterPositionColumn();
    Boolean moveCharacter(KeyCode move);
    void setPos(int rowIndex,int columnIndex);


    void CreateByMaze(Maze mazeThatSend);
}
