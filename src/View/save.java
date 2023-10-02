package View;

public class save {
    public static int saveRow;
    public static int saveCol;

    public save(int row, int col) {
        if(row==0){}
        else{saveRow = row;}
        if(col==0){}
        else {saveCol = col;}
    }

    public int getSaveRow() {
        return saveRow;
    }

    public int getSaveCol() {
        return saveCol;
    }
}
