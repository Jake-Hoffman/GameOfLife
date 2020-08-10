public class CellModel {

    private boolean living;

    CellModel (boolean living){

        this.living = living;
    }

    public void kill(){
        this.living = false;
    }

    public void revive(){
        this.living = true;
    }

    public boolean checkStatus() {
        return living;
    }

}
