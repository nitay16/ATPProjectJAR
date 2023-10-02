package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.awt.*;

public class MyViewController{


    public void StartButtonMyView(ActionEvent actionEvent) {
        Main.StartOver();
    }

    public void ExitButtonMyView(ActionEvent actionEvent) {
        Main.ExitFromGame();
    }
}
