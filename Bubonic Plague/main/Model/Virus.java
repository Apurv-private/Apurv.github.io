package cmpt276.as2.assigment3.Model;

/**
 * Represents the virus cell
 * Data includes row,column , status , variable to check if virus
 */
class Virus {
    private int row;
    private int column;
    private String status ;
    private String isVirus ;

    public Virus(int row, int column, String status, String isVirus) {
        this.row = row;
        this.column = column;
        this.status = status;
        this.isVirus = isVirus;
    }
    //tells  if the virus is revealed or not
    public String getStatus() {
        return status;
    }

    //sets the status of the virus
    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsVirus() {
        return isVirus;
    }

    //sets if the given button is virus or not
    public void setIsVirus(String isVirus) {
        this.isVirus = isVirus;
    }


}
