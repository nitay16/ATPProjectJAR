package Model;

import Client.Client;
import Client.IClientStrategy;
import IO.MyDecompressorInputStream;
import Server.Server;
import Server.ServerStrategyGenerateMaze;
import Server.ServerStrategySolveSearchProblem;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

import javafx.scene.input.KeyCode;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Observable;

import static java.awt.event.KeyEvent.*;

public class MyModel  extends Observable implements IModel, MouseListener {

    //Filde Servers
    private Server MazeGenServer;
    private Server SolverServer;

    // maze
    private Solution solution;
    public Maze maze;

    //Character
    private int CharPoRow;
    private int CharPoCol;
    private String gen;
    private int rows;
    private int cols;


    public MyModel() {
        //Create The Servers
        MazeGenServer = new Server(5400,1000,new ServerStrategyGenerateMaze());
        SolverServer  = new Server(5401,1000,new ServerStrategySolveSearchProblem());
    }
    //func that start the servers
    @Override
    public void Start()
    {
        MazeGenServer.start();
        SolverServer.start();
    }
    //func that stop the servers
    @Override
    public void stopSerevers() {
        MazeGenServer.stop();
        SolverServer.stop();
    }

    private void generate(int row,int col)
    {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        int[] mazeDimensions = new int[]{row, col};
                        toServer.writeObject(mazeDimensions);
                        toServer.flush();
                        byte[] compressedMaze = (byte[])fromServer.readObject();
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        byte[] decompressedMaze = new byte[(row*col) +10];
                        is.read(decompressedMaze);
                        maze = new Maze(decompressedMaze);
                        SetTheMaze(maze);
                        //Get The Value
                        SetCharPos(maze.getStartPosition().getRowIndex(),maze.getStartPosition().getColumnIndex());
                    } catch (Exception var10) {
                        var10.printStackTrace();
                        ServerStrategyGenerateMaze.logger_ServerStrategyGenerateMaze.error("Can't Generate Maze.");
                    }

                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException var1) {
            var1.printStackTrace();
        }

    }
    @Override
    public void CreateByMaze(Maze Get)
    {
        SetTheMaze(Get);
        SetCharPos(Get.getStartPosition().getRowIndex(),Get.getStartPosition().getColumnIndex());
        setChanged();
        notifyObservers();
    }


    private void SetCharPos(int row,int col)
    {
        CharPoRow=row;
        CharPoCol=col;
        setChanged();
        notifyObservers();
    }

    @Override
    public void SetTheMaze(Maze maze) {
        this.maze = maze;
        setChanged();
        notifyObservers();
    }
    @Override
    public void solve()
    {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        toServer.writeObject(maze);
                        toServer.flush();
                        Solution mazeSolution =(Solution)fromServer.readObject();
                        setTheSolution(mazeSolution);
                    } catch (Exception var10) {
                        ServerStrategySolveSearchProblem.logger_ServerStrategySolveSearchProblem.error("Can't Create Solution");
                        var10.printStackTrace();
                    }

                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException var1) {
            var1.printStackTrace();
        }
    }

    private void setTheSolution(Solution mazeSolution) {
        this.solution=mazeSolution;
        setChanged();
        notifyObservers();

    }


    @Override
    public void generateMaze(int width, int height) {
        generate(width,height);
        setChanged();
        notifyObservers();

    }

    @Override
    public Maze getMaze() {
        return maze;
    }


    @Override
    public Solution getSolution() {
        return solution;
    }
    public static String PlayerWay="Down";
    @Override
    public Boolean moveCharacter(KeyCode move) {
        Boolean Check= false;
        switch (move)
        {
            //Down
            case NUMPAD2 :
            case DOWN:
                if(Islegal(CharPoRow+1,CharPoCol))
                {CharPoRow++;}
                PlayerWay="Down";
                Check=true;
                break;
            //Up
            case NUMPAD8 :
            case UP:
                if(Islegal(CharPoRow-1,CharPoCol))
                {CharPoRow--;}
                PlayerWay="Up";
                Check=true;
                break;
            //Left
            case NUMPAD4 :
            case LEFT:
                if(Islegal(CharPoRow,CharPoCol-1))
                {CharPoCol--;}
                PlayerWay="Left";
                Check=true;
                break;
            //Right
            case NUMPAD6 :
            case RIGHT:
                if(Islegal(CharPoRow,CharPoCol+1))
                {CharPoCol++;}
                PlayerWay="Right";
                Check=true;
                break;
            //Down Right
            case NUMPAD3 :
                if(Islegal(CharPoRow+1,CharPoCol+1)&&(Islegal(CharPoRow+1,CharPoCol)||Islegal(CharPoRow,CharPoCol+1)))
                {CharPoRow++;CharPoCol++;}
                PlayerWay="Down";
                Check=true;
                break;
            //Down Left
            case NUMPAD1 :
                if(Islegal(CharPoRow+1,CharPoCol-1)&&(Islegal(CharPoRow,CharPoCol-1)||Islegal(CharPoRow+1,CharPoCol)))
                {CharPoRow++;CharPoCol--;}
                PlayerWay="Down";
                Check=true;
                break;
            //Up Right
            case NUMPAD9 :
                if(Islegal(CharPoRow-1,CharPoCol+1)&&(Islegal(CharPoRow,CharPoCol+1)||Islegal(CharPoRow-1,CharPoCol)))
                {CharPoRow--;CharPoCol++;}
                PlayerWay="Up";
                Check=true;
                break;
            //Up Left
            case NUMPAD7 :
                if(Islegal(CharPoRow-1,CharPoCol-1)&&(Islegal(CharPoRow-1,CharPoCol)||Islegal(CharPoRow,CharPoCol-1)))
                {CharPoRow--;CharPoCol--;}
                PlayerWay="Up";
                Check=true;
                break;
        }
        setChanged();
        notifyObservers();
        return Check;

    }
    private boolean Islegal(int row ,int col)
    {
        return maze.getvalue(row, col) == 0;
    }


    @Override
    public int getCharacterPositionRow() {
        return CharPoRow;
    }

    @Override
    public int getCharacterPositionColumn() {
        return CharPoCol;
    }

    @Override
    public void setPos(int rowIndex, int columnIndex) {
        CharPoRow=rowIndex;
        CharPoCol=columnIndex;
        setChanged();
        notifyObservers();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
