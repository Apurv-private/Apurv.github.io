package cmpt276.as1.assignment1.Model;

public class DepthOfFieldCalculator {
    private static final  double COC = 0.029;
    private Lens lens;
    private double distance;
    private double aperture;

    //Constructors
    public DepthOfFieldCalculator(){
        this.lens = null;
        this.aperture = 0;
        this.distance = 0;
    }

    public DepthOfFieldCalculator(Lens lens, double distance, double aperture) {
        this.lens = lens;
        this.distance = distance;
        this.aperture = aperture;
    }

    //Setters
    public void setAperture(double aperture) {
        this.aperture = aperture; }

    public void setLens(Lens lens) {
        this.lens = lens; }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    //Methods
    //Calculates the hyperfocal point
    public double hyperFocalDistance() {
        return Math.pow(this.lens.getFocal_length(),2) / ( 1000 * this.aperture * COC);
    }

     //Calculates the near Focal Point
    public double nearFocalPoint() {
        return (this.hyperFocalDistance() * this.distance) / (this.hyperFocalDistance() + (this.distance - lens.getFocal_length() / 1000) );
    }

    //Calculates the far Focal Point
    public double farFocalPoint() {
        if(this.distance > this.hyperFocalDistance())
            return Double.POSITIVE_INFINITY;
        return (this.hyperFocalDistance() * this.distance) / (this.hyperFocalDistance() - (this.distance - lens.getFocal_length() / 1000) );
    }

     //Calculates the depth of field
    public double DOF() {
        return this.farFocalPoint() - this.nearFocalPoint();
    }

}