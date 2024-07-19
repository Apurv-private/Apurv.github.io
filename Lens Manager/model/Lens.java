package cmpt276.as2.assignment2.Model;

public class Lens  {
    private String lens;
    private double maximum_aperture;
    private double focal_length;
    private int iconID;



    public Lens(String lens, double maximum_aperture, double focal_length,int iconID) {
        this.lens = lens;
        this.maximum_aperture = maximum_aperture;
        this.focal_length = focal_length;
        this.iconID = iconID;
    }

    public void setLens(String lens) {
        this.lens = lens;
    }

    public void setMaximum_aperture(double maximum_aperture) {
        this.maximum_aperture = maximum_aperture; }

    public void setFocal_length(double focal_length) {
        this.focal_length = focal_length;
    }

    public String getLens() {
        return lens;
    }

    public int getIconID() {
        return iconID;
    }

    public double getMaximum_aperture() {
        return maximum_aperture;
    }

    public double getFocal_length() {
        return focal_length;
    }

    public String toString() {
        return this.lens + " " + this.focal_length +"mm F" + this.maximum_aperture;
    }





}