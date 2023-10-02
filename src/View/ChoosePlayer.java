package View;

import javafx.event.ActionEvent;

public class ChoosePlayer {
    public static int PlayerType=1;
    public void ButtonPlayer1(ActionEvent actionEvent) {
        PlayerType=3;
        Main.ChoosePlayerStage.hide();
        Main.GenInput();
    }

    public void ButtonPlayer2(ActionEvent actionEvent) {
        PlayerType=2;
        Main.ChoosePlayerStage.hide();
        Main.GenInput();
    }

    public void ButtonPlayer3(ActionEvent actionEvent) {
        PlayerType=1;
        Main.ChoosePlayerStage.hide();
        Main.GenInput();
    }
}
