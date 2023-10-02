package View;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import static View.Main.*;

public class GenInputs extends Observable {
    public TextField RowsInput;
    public TextField ColsInput;
    public static int rowToGenerate;
    public static int colToGenerate;

    public void GenerateRandom(ActionEvent actionEvent) {

        GenInputsStage.hide();
        Random r = new Random();
        rowToGenerate = r.nextInt(25);
        colToGenerate = r.nextInt(25);
        if(rowToGenerate>colToGenerate){colToGenerate=rowToGenerate;}
        else {rowToGenerate=colToGenerate;}
        if(rowToGenerate<15)
        {
            colToGenerate=15;
            rowToGenerate=15;
        }
        GenInputsStage.hide();
        StatPlayView.CreateMaze(rowToGenerate,colToGenerate,null);    }

    public void GenerateByInput(ActionEvent actionEvent) {
        boolean Sucess = false;
        try {

            rowToGenerate = Integer.parseInt(RowsInput.getText());
            colToGenerate = Integer.parseInt(ColsInput.getText());
            if (rowToGenerate < 2 || colToGenerate < 2) {
                error()
                ;
                return;
            }
            Sucess = true;

        } catch (Exception e) {
            error();
        }
        if (!Sucess) {
            return;
        }
        // exit and genrate maze
        GenInputsStage.hide();
        GenInputsStage.hide();
        StatPlayView.CreateMaze(rowToGenerate,colToGenerate,null);
    }
}

