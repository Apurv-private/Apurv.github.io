package cmpt276.as2.assigment3.Logic;

/**
 * Represents the logic of the game
 * Data includes rules of the game
 */
public class GameLogic {

    public String gameBasics(){
        return "The user first selects the board size and the number of viruses from the options screen then starts the game, if not then default settings are applied";
    }

    public  String gameRules(){
        return "The user clicks on a button if there is no virus on that button then a scan is started in that row and column and it shows how many viruses are there and that number appears on the button, if the clicked button is a virus then it is revealed";
    }

    public String endGame(){
        return "When all the viruses are found by the player, the player wins the game. But winning with the fewest scans is a challenge";
    }

    public String tactics(){
        return "One of the important strategy of the game is you can start a scan by click on a mine which is already revealed but revealing a virus doesn't count as a scan";
    }
}
