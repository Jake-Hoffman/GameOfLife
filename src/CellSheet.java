public class CellSheet {

    CellModel[][] cells;

    CellSheet(boolean[][] startingData) {

        int rows = startingData.length;
        int columns = startingData[0].length;

        cells = new CellModel[rows][columns];

        for (int xx = 0; xx < rows; xx++){
            for (int yy = 0; yy < columns; yy++){
                cells[xx][yy] = new CellModel(startingData[xx][yy]);
            }
        }
    }

    CellSheet() {

        int rows = 100;
        int columns = 175;

        cells = new CellModel[rows][columns];

        for (int xx = 0; xx < rows; xx++){
            for (int yy = 0; yy < columns; yy++){
                cells[xx][yy] = new CellModel(false);
            }
        }
    }

    public void gameTick(){

        CellModel[][] tempCells = new CellModel[cells.length][cells[0].length];

        for (int xx = 0; xx < cells.length; xx++) {
            for (int yy = 0; yy < cells[xx].length; yy++) {
                tempCells[xx][yy] = cellLogic(xx, yy);
            }
        }

        cells = tempCells;

    }

    public CellModel[][] getCells(){
        return cells;
    }

    public void setCells(CellModel[][] cells){
        this.cells = cells;
    }

    private CellModel cellLogic(int xPos, int yPos){

        CellModel cell = cells[xPos][yPos];

        int neighbors = 0;

        for (int xx = -1; xx < 2; xx++){
            for (int yy = -1; yy < 2; yy++){

                int toCheckX = xPos + xx;
                int toCheckY = yPos + yy;

                if (toCheckX < 0){
                    toCheckX = cells.length - 1;
                } else if (toCheckX >= cells.length) {
                    toCheckX = 0;
                }

                if (toCheckY < 0){
                    toCheckY = cells[0].length - 1;
                } else if (toCheckY >= cells[0].length) {
                    toCheckY = 0;
                }

                if (cells[toCheckX][toCheckY].checkStatus()){
                    neighbors++;
                }
            }
        }

        if (cell.checkStatus()){
            neighbors--;

            if (neighbors < 2 || neighbors > 3){
                return new CellModel(false);
            } else {
                return new CellModel(true);
            }
        } else {
            if (neighbors == 3){
                return new CellModel(true);
            } else {
                return new CellModel(false);
            }
        }



    }

}
