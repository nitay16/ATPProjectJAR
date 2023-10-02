package View;

import algorithms.mazeGenerators.Maze;
import algorithms.search.MazeState;
import algorithms.search.Solution;
import eu.hansolo.tilesfx.addons.Switch;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;

import static Model.MyModel.PlayerWay;

public class MazeDisplayer extends Canvas {

    protected Maze Mymaze;
    public static StringProperty wall = new SimpleStringProperty();


    public MazeDisplayer() {
        //Check if need to Re Draw the maze
        widthProperty().addListener(e -> redraw());
        heightProperty().addListener(e -> redraw());
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public double prefWidth(double v) {
        return super.prefWidth(v);
    }

    @Override
    public double prefHeight(double v) {
        return super.prefHeight(v);
    }

    //maze
    private Solution solu;
    private int[][] maze;
    private int CharRow;
    private int CharCol;
    private int SizeCol;
    private int SizeRow;

    public void setMaze(Maze maze) {
        this.Mymaze = maze;
        if(maze!=null){this.maze = maze.getGrid();}
        redraw();
    }

    public void setCharacterPosition(int row, int col) {
        CharCol = col;
        CharRow = row;
        redraw();
    }

    public void setSolu(Solution solution) {
        if(solution==null){return;}
        this.solu = solution;
        DrawTheSol();
    }


    public void redraw() {
        if (maze != null) {
            //get all the partameters for the canvas
            double canvasHe = getHeight();
            double canvasWi = getWidth();
            double cellHe = canvasHe / maze.length;
            double cellWi = canvasWi / maze[0].length;
            try {
                Image treasure = new Image(new File("resources/Images/treasure.png").toURI().toString());
                Image Wall = new Image(new File("resources/Images/Wall.png").toURI().toString());
                Image Char = new Image(new File(GetWayChar()).toURI().toString());
                Image Back = new Image(new File("resources/Images/lines1.png").toURI().toString());
                Image Empty = new Image(new File("resources/Images/empty.png").toURI().toString());
                GraphicsContext gc = getGraphicsContext2D();
                gc.clearRect(0, 0, getWidth(), getHeight());
                //DrawBackGround
                gc.drawImage(Back, 0, 0, getWidth(), getHeight());
                //Draw The Maze
                for (int row = 0; row < maze.length; row++) {
                    for (int col = 0; col < maze[row].length; col++) {
                        //check if there is a wall
                        if (maze[row][col] == 1) {
                            gc.drawImage(Wall, col * cellWi, row * cellHe, cellWi, cellHe);

                        }
                    }
                }
                // Draw the treasure
                gc.drawImage(treasure, Mymaze.getGoalPosition().getColumnIndex() * cellWi, Mymaze.getGoalPosition().getRowIndex() * cellHe, cellWi, cellHe);
                // Draw the charcter
                gc.drawImage(Char, CharCol * cellWi, CharRow * cellHe, cellWi, cellHe);
            } catch (Exception e) {
                System.out.println("Here");
            }
        }
    }
    public int getcellHe()
    {
        return SizeRow;
    }
    public int getcellWi()
    {
        return SizeCol;
    }

    public void Clear() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());
        }


    public String GetWayChar() {
        String Way = PlayerWay;
        int Type = ChoosePlayer.PlayerType;
        if (Way.equals("Up")) {
            return "resources/Images/PlayerUp" + String.valueOf(Type) + ".png";
        }
        if (Way.equals("Down")) {
            return "resources/Images/PlayerDown" + String.valueOf(Type) + ".png";
        }
        if (Way.equals("Left")) {
            return "resources/Images/PlayerLeft" + String.valueOf(Type) + ".png";
        }
        if (Way.equals("Right")) {
            return "resources/Images/PlayerRight" + String.valueOf(Type) + ".png";
        }
        return "/resources/Images/PlayerDown1.png";
    }

    public void DrawTheSol() {

        Image RedDot = new Image(new File("resources/Images/RedDot.png").toURI().toString());
        Image treasure = new Image(new File("resources/Images/treasure.png").toURI().toString());
        Image Char = new Image(new File(GetWayChar()).toURI().toString());
        //get all the partameters for the canvas
        double canvasHe = getHeight();
        double canvasWi = getWidth();
        double cellHe = canvasHe / maze.length;
        double cellWi = canvasWi / maze[0].length;
        GraphicsContext gc = getGraphicsContext2D();
        for (int i = 0; i < solu.getSolutionPath().size(); i++) {

            //get the position from the solution
            int row = ((MazeState) solu.getSolutionPath().get(i)).get_position().getRowIndex();
            int col = ((MazeState) solu.getSolutionPath().get(i)).get_position().getColumnIndex();

            //Check if we at the end point
            if (i == solu.getSolutionPath().size() - 1) {
                gc.drawImage(treasure, col * cellWi, row * cellHe, cellWi, cellHe);

            } else {
                if (i == 0) {
                    gc.drawImage(Char, CharCol * cellWi, CharRow * cellHe, cellWi, cellHe);
                } else {
                    gc.drawImage(RedDot, col * cellWi, row * cellHe, cellWi, cellHe);
                }

            }

        }

    }

}
