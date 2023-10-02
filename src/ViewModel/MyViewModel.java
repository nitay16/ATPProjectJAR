package ViewModel;

import Model.IModel;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;

import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {

    public IModel model;
    private Solution Solu;
    private int CharPoRow;
    private int CharPoCol;

    public MyViewModel(IModel model) {

        this.model = model;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o==model)
        {
            CharPoRow=model.getCharacterPositionRow();
            CharPoCol=model.getCharacterPositionColumn();
            Solu =model.getSolution();
            setChanged();
            notifyObservers();
        }

    }
    public int getCharPoRow(){return CharPoRow;}
    public void setCharPoRow(int row){CharPoRow=row;}

    public int getCharPoCol(){return CharPoCol;}
    public void setCharPoCol(int col){ CharPoCol=col;}
    public Boolean moveCharacter(KeyCode move)
    {
        return model.moveCharacter(move);
    }

    public Solution getSolution() {
        return Solu;
    }
    public Maze getMaze(){return model.getMaze();}
    public void generateMaze(int rows,int cols){model.generateMaze(rows,cols);}
    public void setMaze(Maze maze){model.SetTheMaze(maze);}
    public void solve(){model.solve();}
    public void setPos(int row,int col)
    {
        CharPoCol=col;
        CharPoRow=row;
        model.setPos(row,col);}

}
