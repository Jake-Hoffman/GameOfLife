public class CellMain {

    public static void main (String[] args){

        CellSheet sheet = new CellSheet();
        DisplayCells display = new DisplayCells();

        new Controller(sheet, display);
    }
}
