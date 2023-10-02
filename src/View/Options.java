package View;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Options {

    public Button Empty;
    public Button Simple;
    public Button Mymaze;
    public String TypeOfMaze="MyMazeGenerator";


    //CrateTheMaze
    public void ButtonEmptyMaze(ActionEvent actionEvent) {
        Empty.setStyle("-fx-background-color:#ffff66 ");
        Simple.setStyle("-fx-background-color:#cccccc ");
        Mymaze.setStyle("-fx-background-color:#cccccc ");
        TypeOfMaze="EmptyMazeGenerator";
    }

    public void ButtonSimpleMaze(ActionEvent actionEvent) {
        Empty.setStyle("-fx-background-color: #cccccc");
        Simple.setStyle("-fx-background-color:#ffff66 ");
        Mymaze.setStyle("-fx-background-color:#cccccc ");
        TypeOfMaze="SimpleMazeGenerator";
    }

    public void ButtonMyMaze(ActionEvent actionEvent) {
        Empty.setStyle("-fx-background-color:#cccccc ");
        Simple.setStyle("-fx-background-color:#cccccc ");
        Mymaze.setStyle("-fx-background-color:#ffff66 ");
        TypeOfMaze="MyMazeGenerator";
    }

    public Button BFS;
    public Button DFS;
    public Button bestFS;
    public String SolutionChoose="BreadthFirstSearch";


    //Solutions Button
    public void BFSButton(ActionEvent actionEvent) {
        BFS.setStyle("-fx-background-color:#ffff66");
        DFS.setStyle("-fx-background-color:#cccccc");
        bestFS.setStyle("-fx-background-color:#cccccc");
        SolutionChoose="BreadthFirstSearch";
    }

    public void DFSButton(ActionEvent actionEvent) {
        BFS.setStyle("-fx-background-color:#cccccc");
        DFS.setStyle("-fx-background-color:#ffff66");
        bestFS.setStyle("-fx-background-color:#cccccc");
        SolutionChoose="DepthFirstSearch";
    }

    public void BestFSButton(ActionEvent actionEvent) {
        BFS.setStyle("-fx-background-color:#cccccc");
        DFS.setStyle("-fx-background-color:#cccccc");
        bestFS.setStyle("-fx-background-color:#ffff66");
        SolutionChoose="BestFirstSearch";
    }


    public void BackButtonOptions(ActionEvent actionEvent) {
        try (OutputStream output = new FileOutputStream("resources/config.properties")) {
            Properties prop = new Properties();
            // set the properties value
            prop.setProperty("mazeGeneratingAlgorithm", TypeOfMaze);
            prop.setProperty("mazeSearchingAlgorithm", SolutionChoose);
            // save properties to project root folder
            prop.store(output, null);
            //Close The Screen
            Main.OptionsStage.hide();
            Main.CloseView();

        } catch (Exception io) {
            io.printStackTrace();
        }



    }
}
