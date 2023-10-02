package View;

import Model.MyModel;
import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.transform.Scale;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import javax.swing.event.ChangeListener;
import java.io.File;


public class Main extends Application {
    public static MyModel model;
    public static MyViewModel ViewModel;
    public static Maze maze;
    public static Solution solution;
    //music
    public static MediaPlayer BackGroundPlayer;
    public static MediaPlayer WiningPlayer;
    //For each var we careate Parent for the Fxml and Scene to build
    public static Parent AboutFxml;
    public static Scene About;
    public static Parent AlertFxml;
    public static Scene Alert;
    public static Stage AlertStage;
    public static Parent GenInputsFxml;
    public static Scene GenInputs;
    public static Stage GenInputsStage;
    public static Parent HelpFxml;
    public static Scene Help;

    public static Parent MenuFxml;
    public static Scene Menu;

    public static Parent MyViewFxml;
    public static Scene MyviewController;

    public static Parent OptionsFxml;
    public static Scene Options;
    public static Stage OptionsStage;
    public static Parent ChoosePlayerFxml;
    public static Scene ChoosePlayer;
    public static Stage ChoosePlayerStage;
    public static Parent PlayViewFxml;
    public static Scene Playview;

    public static Parent WonFxml;
    public static Scene Won;
    public static Stage WonStage;
    public static FXMLLoader fxml;
    public static StackPane ChooseLoadStakPane;
    public static Scene ChooseLoad;
    public static Stage ChooseLoadStage;

    public static PlayView StatPlayView;


    public static Stage MainScreen;



    //Create Hiding

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    public static void main(String args)
    {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
         AlertFxml = FXMLLoader.load(getClass().getResource("Alert.fxml"));
         AboutFxml = FXMLLoader.load(getClass().getResource("About.fxml"));
         GenInputsFxml = FXMLLoader.load(getClass().getResource("GenInputs.fxml"));
         HelpFxml = FXMLLoader.load(getClass().getResource("Help.fxml"));
         MenuFxml = FXMLLoader.load(getClass().getResource("Menu.fxml"));
         MyViewFxml = FXMLLoader.load(getClass().getResource("MyView.fxml"));
         OptionsFxml = FXMLLoader.load(getClass().getResource("Options.fxml"));
         fxml = new FXMLLoader(getClass().getResource("PlayView.fxml"));
         PlayViewFxml = fxml.load();
         StatPlayView = fxml.getController();

         ChoosePlayerFxml = FXMLLoader.load(getClass().getResource("ChoosePlayer.fxml"));
         MainScreen = stage;
         MainScreen.getIcons().add(new Image("/Images/icon.png"));
         MainScreen.initStyle(StageStyle.UNDECORATED);
         About =  new Scene(AboutFxml,800,550);
         Alert =  new Scene(AlertFxml,500,200);
         ChoosePlayer =  new Scene(ChoosePlayerFxml,500,200);
         GenInputs =  new Scene(GenInputsFxml,550,380);
         Help =  new Scene(HelpFxml,800,550);
         Menu =  new Scene(MenuFxml,795,550);
         Options =  new Scene(OptionsFxml,800,550);
         Playview =  new Scene(PlayViewFxml,950,700);
         MainScreen.setTitle("Find The Treasure");
         MyviewController =  new Scene(MyViewFxml,795,550);
         //WonScreen
        WonFxml = FXMLLoader.load(Main.class.getResource("Won.fxml"));
        Won =  new Scene(WonFxml,550,380);
        WonStage = new Stage();
        WonStage.initStyle(StageStyle.UNDECORATED);
        WonStage.setTitle("Find The Treasure");
        WonStage.getIcons().add(new Image("/Images/icon.png"));
        WonStage.setScene(Won);

         //Create the model with viewModel , and turn it on
         model = new MyModel();
         model.Start();
         ViewModel = new MyViewModel(model);
         model.addObserver(ViewModel);
         //Activate
         BackGoundSong();
         MainScreen.initStyle(StageStyle.UNDECORATED);
         MainScreen.setScene(MyviewController);
        MainScreen.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                Platform.exit();
                System.exit(0);
            };});

        MainScreen.show();

         }
    public static void CloseView()
    {
        MainScreen.setScene(Main.Playview);
        MainScreen.show();
    }
    public static void StartOver()
    {
        MainScreen.hide();
        MainScreen = new Stage();
        try {
            fxml = new FXMLLoader(Main.class.getResource("PlayView.fxml"));
            PlayViewFxml = fxml.load();
            StatPlayView = fxml.getController();
            maze=null;
            Playview =  new Scene(PlayViewFxml,950,700);
        }catch (Exception ex){}
        MainScreen.setScene(Playview);
        MainScreen.setTitle("Find The Treasure");
        MainScreen.getIcons().add(new Image("/Images/icon.png"));
        MainScreen.initStyle(StageStyle.DECORATED);
        MainScreen.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                Platform.exit();
                System.exit(0);
            };});
        BackGroundPlayer.stop();
        BackGoundSong();
        MainScreen.show();
    }

    //Func That Exit the game
    public static void ExitFromGame()
    {
        ViewModel.model.stopSerevers();
        Platform.exit();
        System.exit(0);
    }


    public static void ChoosePlayer()
    {
        ChoosePlayerStage = new Stage();
        ChoosePlayerStage.setScene(ChoosePlayer);
        ChoosePlayerStage.initStyle(StageStyle.UNDECORATED);
        ChoosePlayerStage.initModality(Modality.APPLICATION_MODAL);
        ChoosePlayerStage.getIcons().add(new Image("/Images/icon.png"));
        ChoosePlayerStage.show();
    }
    public static void GenInput()
    {
        GenInputsStage = new Stage();
        GenInputsStage.setScene(GenInputs);
        GenInputsStage.initStyle(StageStyle.UNDECORATED);
        GenInputsStage.initModality(Modality.APPLICATION_MODAL);
        GenInputsStage.getIcons().add(new Image("/Images/icon.png"));
        GenInputsStage.show();
    }

    public static void error(){
        AlertStage = new Stage();
        AlertStage.setScene(Alert);
        AlertStage.initStyle(StageStyle.UNDECORATED);
        AlertStage.initModality(Modality.APPLICATION_MODAL);
        AlertStage.getIcons().add(new Image("/Images/icon.png"));

        AlertStage.show();
    }

    public static void BackGoundSong()
    {
        File Wins = new File("resources/Music/Main.mp3");
        Media Song = new Media((Wins.toURI().toString()));
        BackGroundPlayer = new MediaPlayer(Song);
        BackGroundPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                BackGroundPlayer.seek(Duration.ZERO);
                BackGroundPlayer.play();
            }
        });
        BackGroundPlayer.play();
    }

    //Music Part
    public static void TheWonShow()
    {
        BackGroundPlayer.stop();
        File Wins = new File("resources/Music/Won.mp3");
        Media Song = new Media(Wins.toURI().toString());
        WiningPlayer = new MediaPlayer(Song);
        WiningPlayer.play();
        MainScreen.hide();
        WonStage.show();

    }



}
