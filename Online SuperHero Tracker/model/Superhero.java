package ca.cmpt213.a3.onlinesuperherotracker.model;

/**
 *  Represents a Superhero Profile
 *  Data includes id,name, superpower, height in cm and civilians saved.
 *  By Apurv Nerurkar, 301386528, anerurka@sfu.ca
 */
public class Superhero {

    //Fields
    private long id;
    private String name;
    private double heightInCm; //in centimeters
    private int civilianSaveCount;
    private String superPower;

    public Superhero(String name, int civilians,long id) {
        this.name = name;
        this.civilianSaveCount = civilians;
        this.id = id;
    }

    @Override
    public String toString() {
        return "superhero{" +
                "name='" + name + '\'' +
                ", superpower='" + superPower + '\'' +
                ", height=" + heightInCm +
                ", noOfCiviliansSaved=" + civilianSaveCount +
                '}'; }

    public void toString(Superhero hero) {
        System.out.println("MarvelHero{name='" + hero.getName() + "', heightInCm="+hero.getHeightInCm()+", civilianSaveCount=" + hero.getCivilianSaveCount()+", superPower='"+hero.getSuperPower()+"'}"); }

    //Constructors
    public Superhero() {
        this.name = "";
        this.superPower = "";
        this.heightInCm = 0;
    }

    public Superhero(String name, String superPower, double h, long id) {
        this.name = name;
        this.superPower = superPower;
        this.heightInCm = h;
        this.civilianSaveCount = 0;
        this.id = id;
    }

    //Setters
    void setNoOfCiviliansSave(int civilians) {
        this.civilianSaveCount = civilians;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSuperPower(String superPower) {
        this.superPower = superPower;
    }

    public void setHeightInCm(double heightInCm) {
        this.heightInCm = heightInCm;
    }

    public void setCivilianSaveCount(int civilianSaveCount) {
        this.civilianSaveCount = civilianSaveCount;
    }

    public void setId(long id) {
        this.id = id;
    }

    //Getters
    public String getName() {
        return name; }

    public String getSuperPower() {
        return superPower;
    }

    public double getHeightInCm() {
        return heightInCm;
    }

    public int getCivilianSaveCount() {
        return civilianSaveCount;
    }

    public long getId() {
        return id;
    }

    //Printing Methods
    void printForTopThree() {
        System.out.println(this.name + " has saved " + this.civilianSaveCount +" civilians");
    }

    public void print(int number) {
        System.out.println(number + ".Hero name: " + this.name + ",height: " + this.heightInCm + ",superpower: " + this.superPower + ",saved " + this.civilianSaveCount + " civilians."); }

}
