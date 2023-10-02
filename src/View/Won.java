package View;

import javafx.event.ActionEvent;

public class Won {
    public void StartOver(ActionEvent actionEvent) {
        Main.WonStage.hide();
        Main.StartOver();
        //Generate new maze and run over the page
    }

    public void ExitGame(ActionEvent actionEvent) {
        Main.ExitFromGame();
        //ExitFromThe Game
    }
}
