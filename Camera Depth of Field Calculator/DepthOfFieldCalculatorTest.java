package cmpt276.as1.assignment1.Model;

import static org.junit.jupiter.api.Assertions.*;

class DepthOfFieldCalculatorTest {
    Lens l1 = new Lens ("Sigma",1.8,50);
    Lens l2 = new Lens("Tamron",2.8,90);
    Lens l3 = new Lens("Canon",2.8,200);
    DepthOfFieldCalculator d1 = new DepthOfFieldCalculator(l1,1.1,1.8);
    DepthOfFieldCalculator d2 = new DepthOfFieldCalculator(l2,1,8);
    DepthOfFieldCalculator d3 = new DepthOfFieldCalculator(l3,11,15);

    @org.junit.jupiter.api.Test
    void hyperFocalDistance() {
        assertEquals(47.89,d1.hyperFocalDistance(),0.01);}

    @org.junit.jupiter.api.Test
    void nearFocalPoint() {
        assertEquals(1.08,d1.nearFocalPoint(),0.01);}

    @org.junit.jupiter.api.Test
    void farFocalPoint() {
        assertEquals(1.12,d1.farFocalPoint(),0.01);}

    @org.junit.jupiter.api.Test
    void DOF() {
        assertEquals(0.05,d1.DOF(),1);}
}