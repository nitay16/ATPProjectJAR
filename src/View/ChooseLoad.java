package View;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class ChooseLoad {

    public Button but;

    public void BackButtonHelp(ActionEvent actionEvent) {
        Main.ChooseLoadStage.hide();
        Main.MainScreen.show();
    }
}
