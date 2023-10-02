package View;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.scene.control.Button;

import java.awt.event.KeyListener;
import java.io.*;
import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;

import static View.Main.*;
import static javafx.application.ConditionalFeature.CONTROLS;
import static javafx.application.ConditionalFeature.SWT;

public class PlayView implements IView, Observer {

    @FXML
    public Pane Pane1;
    @FXML
    public  MazeDisplayer mazeDisplayer;
    @FXML
    public AnchorPane Anchor;
    public static boolean DisplayTheSolu;
    public static boolean DoNew;

    public void New(ActionEvent actionEvent) {
        Main.StartOver();
    }
    public void Save(ActionEvent actionEvent) {
        if(ViewModel.model.getMaze()==null){return;}
        try
        {
            // writing the soultion
            File dir = new File("resources/Saved");
            int count = dir.list().length;
            File FileToSave = new File("resources/Saved/Gamesave" + count);
            FileOutputStream myWriter = new FileOutputStream("resources/Saved/Gamesave" + count);
            ObjectOutputStream Save = new ObjectOutputStream(myWriter);
            Maze mazeToSave = ViewModel.model.getMaze();
            mazeToSave.SetNewStart(ViewModel.getCharPoRow(),ViewModel.getCharPoCol());
            Save.writeObject(mazeToSave);
            Save.close();
        }
        catch (Exception ex)

        {
            //TRY AGAIN
        }

    }

    public void Load(ActionEvent actionEvent) {
        try {
            File dir = new File("resources/Saved");
            int count = dir.list().length;
            int locationInPane = 0;
            boolean Oposet = false;
            Button[] saved = new Button[count];
            for(int i=0 ; i<count;i++) {
                saved[i] = new Button("Game Saved Number: " + String.valueOf(i));
                int now = i;
                saved[i].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        try {
                            FileInputStream myReader = new FileInputStream("resources/Saved/Gamesave"  + String.valueOf(now));
                            ObjectInputStream Read = new ObjectInputStream(myReader);
                            Maze MazeFromSave = (Maze) Read.readObject();
                            Read.close();
                            mazeDisplayer.Clear();
                            DisplayTheSolu=false;
                            DoNew=false;
                            ViewModel.setPos(MazeFromSave.getStartPosition().getRowIndex(),MazeFromSave.getStartPosition().getColumnIndex());
                            ViewModel.setMaze(MazeFromSave);
                            solution = ViewModel.getSolution();
                            maze = ViewModel.getMaze();
                            displayMaze(MazeFromSave);
                            ChooseLoadStage.hide();
                            BackGroundPlayer.stop();
                            BackGoundSong();
                            MainScreen.show();
                            CreateMaze(0,0,MazeFromSave);
                        } catch (Exception ex) {
                        }
                    }
                });
                saved[i].setStyle("-fx-background-color: #ffff66;");
                saved[i].setTranslateY(locationInPane);
                if(Oposet){locationInPane=-(locationInPane+30);Oposet=false;}
                else {locationInPane=(locationInPane+30);Oposet=true;}
            }
                ChooseLoadStakPane = FXMLLoader.load(getClass().getResource("ChooseLoad.fxml"));
                ChooseLoad =  new Scene(ChooseLoadStakPane,800,550);
                ChooseLoadStakPane.getChildren().addAll(saved);
                if(count==0){ChooseLoadStakPane.getChildren().add(new Label("There Isn't Any Saved Games"));}
                Button back = new Button("Back");
                back.setStyle("-fx-background-color: #ffff66;");
                back.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        Main.ChooseLoadStage.hide();
                        Main.MainScreen.show();
                    }
                });
                back.setTranslateY(250);
                ChooseLoadStakPane.getChildren().add(back);
                ChooseLoadStage = new Stage();
                ChooseLoadStage.setScene(ChooseLoad);
                ChooseLoadStage.initStyle(StageStyle.UNDECORATED);
                MainScreen.hide();
                ChooseLoadStage.show();
            }
        catch (Exception ex){}
        }

    public void Properties(ActionEvent actionEvent) {
        OptionsStage = new Stage();
        OptionsStage.initStyle(StageStyle.UNDECORATED);
        OptionsStage.setScene(Main.Options);
        Main.MainScreen.hide();
        OptionsStage.show();

    }

    public void HelpButton(ActionEvent actionEvent) {
        Main.MainScreen.setScene(Main.Help);
        Main.MainScreen.show();
    }

    public void About(ActionEvent actionEvent) {
        Main.MainScreen.setScene(Main.About);
        Main.MainScreen.show();
    }

    public void Exit(ActionEvent actionEvent) {
        Main.ExitFromGame();
    }
    @Override
    public  void displayMaze(Maze maze)
    {
        if(mazeDisplayer.getcellWi()!=0)
        {
            new save(mazeDisplayer.getcellHe(),mazeDisplayer.getcellWi());
        }
      mazeDisplayer.setCharacterPosition(Main.ViewModel.getCharPoRow(),Main.ViewModel.getCharPoCol());
      mazeDisplayer.setMaze(maze);
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o == Main.ViewModel)
        {
            if(DoNew||maze==null){return;}
            displayMaze(ViewModel.getMaze());
            Main.solution = ViewModel.getSolution();
            EndGame();
        }
        }



    public void EndGame()
    {
        if(mazeDisplayer.Mymaze.getGoalPosition().getColumnIndex()== ViewModel.getCharPoCol()&&mazeDisplayer.Mymaze.getGoalPosition().getRowIndex()==ViewModel.getCharPoRow()) {
            TheWonShow();
        }
    }


    public void Solve(ActionEvent actionEvent) {
        if(maze==null){return;}
        DisplayTheSolu=true;
        maze.SetNewStart(ViewModel.getCharPoRow(),ViewModel.getCharPoCol());
        ViewModel.setMaze(maze);
        ViewModel.solve();
        solution=ViewModel.getSolution();
        mazeDisplayer.setSolu(solution);
        mazeDisplayer.requestFocus();
    }



    public void CreateMaze(int row,int col,Maze mazeThatSend) {
        try {
            DoNew=false;
            DisplayTheSolu=false;
            PlayView view = fxml.getController();
            ViewModel.addObserver(view);
            if(mazeThatSend!=null){
                ViewModel.model.CreateByMaze(mazeThatSend);
            }
            else {
                ViewModel.generateMaze(row,col);
            }
            Pane1.prefHeightProperty().bind(Anchor.heightProperty().subtract(50));
            Pane1.prefWidthProperty().bind(Anchor.widthProperty());
            mazeDisplayer.heightProperty().bind(Pane1.heightProperty());
            mazeDisplayer.widthProperty().bind(Pane1.widthProperty());
            maze = ViewModel.getMaze();
            Anchor.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    if(DoNew){return;}
                    ViewModel.moveCharacter(keyEvent.getCode());
                }
            });
            displayMaze(maze);
            mazeDisplayer.requestFocus();

        }catch (Exception ex ){}

    }


    public void Generate(ActionEvent actionEvent) {
        ChoosePlayer();
    }

    public void mouseClicked(MouseEvent mouseEvent) {
    }

    public void Move(javafx.scene.input.KeyEvent keyEvent) {
        if(maze==null){return;}
        else {ViewModel.setCharPoCol(0);ViewModel.setCharPoRow(0);}
        if(DoNew){return;}
        if(mazeDisplayer==null){return;}
        Main.ViewModel.moveCharacter(keyEvent.getCode());
        keyEvent.consume();
        Main.ViewModel.getMaze().SetNewStart(Main.ViewModel.getCharPoRow(),Main.ViewModel.getCharPoCol());
        ViewModel.setPos(Main.ViewModel.getCharPoRow(),Main.ViewModel.getCharPoCol());
        displayMaze(ViewModel.getMaze());
        if(DisplayTheSolu)
        {
            ViewModel.solve();
            solution =ViewModel.getSolution();
            mazeDisplayer.setSolu(solution);
        }
    }
}

