public class Controller {

    private CellSheet sheet;
    private DisplayCells display;

    Controller (CellSheet sheet, DisplayCells display){
        this.sheet = sheet;
        this.display = display;
        display.setSheet(sheet);
        display.setController(this);

        setupAndRun();
    }

    Controller ( DisplayCells display){
        this.sheet = new CellSheet();
        this.display = display;

    }

    public void setupAndRun(){
        display.setupCells(sheet.getCells());
        display.createAndShowGUI();
    }
}
