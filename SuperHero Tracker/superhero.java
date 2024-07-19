package Assignment1.src;

public class superhero  {

    //Fields
    private String name;
    private String superpower;
    private double height; //in centimeters
    private int noOfCiviliansSaved;

    public superhero(String name, int civilians) {
        this.name = name;
        this.noOfCiviliansSaved = civilians;
    }

    @Override
    public String toString() {
        return "superhero{" +
                "name='" + name + '\'' +
                ", superpower='" + superpower + '\'' +
                ", height=" + height +
                ", noOfCiviliansSaved=" + noOfCiviliansSaved +
                '}'; }

    public void toString(superhero hero) {
        System.out.println("MarvelHero{name='" + hero.getName() + "', heightInCm="+hero.getHeight()+", civilianSaveCount=" + hero.getNoOfCiviliansSaved()+", superPower='"+hero.getSuperPower()+"'}"); }

    //Constructors
    public superhero() {
        this.name = "";
        this.superpower = "";
        this.height = 0; }

    public superhero(String name, String superpower, double h) {
        this.name = name;
        this.superpower = superpower;
        this.height = h;
        this.noOfCiviliansSaved = 0;}

    //Setters
    void setNoOfCiviliansSave(int civilians) {
        this.noOfCiviliansSaved = civilians;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSuperpower(String superpower) {
        this.superpower = superpower;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setNoOfCiviliansSaved(int noOfCiviliansSaved) {
        this.noOfCiviliansSaved = noOfCiviliansSaved;
    }

    //Getters
    public String getName() {
        return name; }

    public String getSuperPower() {
        return superpower;
    }

    public double getHeight() {
        return height;
    }

    public int getNoOfCiviliansSaved() {
        return noOfCiviliansSaved;
    }

    //Printing Methods
    void printForTopThree() {
        System.out.println(this.name + " has saved " + this.noOfCiviliansSaved +" civilians");
    }

    public void print(int number) {
        System.out.println(number + ".Hero name: " + this.name + ",height: " + this.height + ",superpower: " + this.superpower + ",saved " + this.noOfCiviliansSaved + " civilians."); }


}


