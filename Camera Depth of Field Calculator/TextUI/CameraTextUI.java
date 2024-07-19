package cmpt276.as1.assignment1.TextUI;

import java.text.DecimalFormat;
import java.util.Scanner;

import cmpt276.as1.assignment1.Model.DepthOfFieldCalculator;
import cmpt276.as1.assignment1.Model.Lens;
import cmpt276.as1.assignment1.Model.LensManager;

import static java.lang.String.format;

public class CameraTextUI {
    private Scanner sc = new Scanner(System.in);

    public CameraTextUI(LensManager manager){
        manager.add("Canon",1.8,50);
        manager.add("Tamron",2.8,90);
        manager.add("Sigma",2.8,200);
        manager.add("Nikon",4.0,200);
    }


    public void show(LensManager manager) {
        boolean done = false;
        DepthOfFieldCalculator dof = new DepthOfFieldCalculator();

        int i = 0;
        double distance = 0;
        double f;

        //Creating the list and asking for user input
        while (!done) {
            System.out.println("Lenses to pick from:");
            for (int index = 0; index < 4; index++)
                System.out.println(manager.getIndex(index));
            System.out.println("(-1 to exit)");
            System.out.print(": ");
            i = sc.nextInt();

            if (i == -1) {
                System.out.print("Exiting...");
                return;
            } else if (i > 3 || i < 0) {
                System.out.print("Error: Invalid lens index");
            } else
                done = true;
        }
        //setting the lens in dof object selected by the user
        dof.setLens(manager.getLens(i));

        //Asking for the aperture input
        do {
            System.out.print("Aperture [the F number]: ");
            f = sc.nextDouble();
            if (f < 0) System.out.println("Aperture not possible type again");
        } while (f < 0);
        if (f < manager.getLens(i).getMaximum_aperture())
            return;
            dof.setAperture(f);

        //Asking for the distance to subject input
        do {
            System.out.print("Distance to subject [m]: ");
            distance = sc.nextDouble();
            if (distance < 0) System.out.println("Distance cannot be negative type again");
        } while (distance < 0);
        dof.setDistance(distance);

        //Printing all the required fields as per user input
        System.out.println("\tIn focus: " + roundOffTo2DecPlaces(dof.nearFocalPoint()) + "m to " + roundOffTo2DecPlaces(dof.farFocalPoint()) + "m [DoF = " + roundOffTo2DecPlaces(dof.DOF()) + "m]");
        System.out.println("\tHyperfocal point: " + roundOffTo2DecPlaces(dof.hyperFocalDistance()) + "m");
    }

    //Rounding off double values up to 2 decimal places
    private String roundOffTo2DecPlaces(double val) {
        return String.format("%.2f", val); }
}

