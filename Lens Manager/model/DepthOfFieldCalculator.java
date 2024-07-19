package cmpt276.as2.assignment2.Model;

public class DepthOfFieldCalculator {
    private static final  double COC = 0.029;
    private double aperture;
    private double distance;
    private Lens lens;

    public DepthOfFieldCalculator(){}

    public DepthOfFieldCalculator(Lens lens, double distance, double aperture) {
        this.aperture = aperture;
        this.distance = distance;
        this.lens = lens; }

    public void setAperture(double aperture) {
        this.aperture = aperture; }

    public void setLens(Lens lens) {
        this.lens = lens; }

    public void setDistance(double distance) {
        this.distance = distance; }

    public double hyperFocalDistance() {
        return Math.pow(this.lens.getFocal_length(),2) / ( 1000 * this.aperture * COC); }

    public double nearFocalPoint() {
        return (this.hyperFocalDistance() * this.distance) / (this.hyperFocalDistance() + (this.distance - lens.getFocal_length() / 1000) ); }

    public double farFocalPoint() {
        if(this.distance > this.hyperFocalDistance())
            return Double.POSITIVE_INFINITY;
        return (this.hyperFocalDistance() * this.distance) / (this.hyperFocalDistance() - (this.distance - lens.getFocal_length() / 1000) ); }

    public double DOF() {
        return this.farFocalPoint() - this.nearFocalPoint();}

    public double getAperture() {
        return aperture; }

    public double getDistance() {
        return distance; }
}