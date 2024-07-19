package cmpt276.as1.assignment1.Model;

public class Lens {
    private String lens;
    private double maximum_aperture;
    private double focal_length;

    //Constructors
    public Lens(String lens, double maximum_aperture, double focal_length) {
        this.lens = lens;
        this.maximum_aperture = maximum_aperture;
        this.focal_length = focal_length; }


    //Getters
    public String getLens() {
        return lens;
    }

    public double getMaximum_aperture() {
        return maximum_aperture;
    }

    public double getFocal_length() {
        return focal_length;
    }

    //toString method
    public String toString(int i) {
        return i+". "+this.getLens()+" "+this.getFocal_length()+"mm F"+this.getMaximum_aperture(); }
}
