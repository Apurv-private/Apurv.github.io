/* Name - Apurv Nerurkar
Student # - 301386528*/
package cmpt276.as1.assignment1;

import cmpt276.as1.assignment1.Model.LensManager;
import cmpt276.as1.assignment1.TextUI.CameraTextUI;

class main {
    public static void main(String[] args) {
        LensManager manager = new LensManager();
        CameraTextUI uI = new CameraTextUI(manager);
        uI.show(manager);
    }
}