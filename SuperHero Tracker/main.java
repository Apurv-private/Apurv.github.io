/*Name = Apurv Nerurkar
Student# - 301386528
Email - anerurka@sfu.ca*/

package Assignment1.src;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class main {
    private static final Type REVIEW_TYPE = new TypeToken<List<superhero>>(){}.getType();

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        List<superhero> superH = new ArrayList<superhero>();

        text menuOption = new text("SuperHero Tracker", 7);
        menuOption.setMenuOption(0, "List all superheroes");
        menuOption.setMenuOption(1, "Add a new superhero");
        menuOption.setMenuOption(2, "Remove a superhero");
        menuOption.setMenuOption(3, "Update number of civilians saved by a superhero");
        menuOption.setMenuOption(4, "List Top 3 superheroes");
        menuOption.setMenuOption(5, "Debug Dump(toString)");
        menuOption.setMenuOption(6, "Exit");

        //Uses Gson and JsonReader to read from the file
        //Taken from https://gist.github.com/julianbonilla/2784293/revisions
        Gson gsonRead = new Gson();
         JsonReader reader = null;

            File file = new File("./superHero_Tracker.json");
            if(file.length() != 0) {
                try {
                    reader = new JsonReader(new FileReader("superHero_Tracker.json"));
                    superH = gsonRead.fromJson(reader, REVIEW_TYPE);
                } catch (FileNotFoundException | JsonIOException | JsonSyntaxException e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        boolean forCheckingSuperHeroList = false;
        boolean forCheckingAddSuperHero = false;
        boolean forCheckingRemoveSuperHero = false;
        boolean forCheckingUpdateCount = false;
        while (!forCheckingSuperHeroList) {
            menuOption.show(); //Prints the menuTitle and menuOptions, the menuTitle can be changed by user by using setter

            int index = 0;
            int counter = 0;

            String name = "";
            double height = 0;
            superhero hero = null;
            String superpower = "";

            System.out.print("Enter >> ");
            int i = Integer.parseInt(sc.nextLine());
            if (i > 7 || i <= 0) break;
            
            switch (i) {
                case 1: //Prints the list of superheroes, if empty prints no heroes present in the list
                    for (int j = 0; j < superH.size(); j++) {
                        superH.get(j).print(j + 1);
                        if (superH.size() == 0)
                            System.out.println("No heroes present in the list. Add more..");
                    }
                    break;

                case 2: //Adds a superhero with user defined name,height and superpower and adds it using .add(object)
                    System.out.print("Enter Hero's name: ");
                    name = sc.nextLine();

                    //To check if the height entered is positive
                    do {
                        System.out.print("Enter Hero's height in cm: ");
                        height = Double.parseDouble(String.format("%.2f", Double.parseDouble(sc.nextLine())));
                        if (height > 0)
                            forCheckingAddSuperHero = true;
                        else
                            System.out.println("Height cannot be a negative number");
                    } while (forCheckingAddSuperHero = false);

                    System.out.print("Enter Hero's Superpower: ");
                    superpower = sc.nextLine();
                    hero = new superhero(name, superpower, height);
                    superH.add(hero);

                    System.out.println(name + " has been added to the list of superheroes.");
                    break;

                case 3: //Removes a superhero from the list printed by user input, removes object at 'n' by copying 'n+1' over it and so on till object.size()
                    for (int j = 0; j < superH.size(); j++) {
                        superH.get(j).print(j + 1);
                    }
                    System.out.println("Enter Hero number to be removed or Enter 0 to cancel");
                    System.out.print("Enter >> ");
                    index = Integer.parseInt(sc.nextLine());

                    //To check if the user has entered a valid input
                    do {
                        if (index > 0 && index < superH.size()) {
                            forCheckingRemoveSuperHero = true;
                            break;
                        }
                        System.out.println("Error input, Back to menu");
                        forCheckingRemoveSuperHero = true;
                    } while (forCheckingRemoveSuperHero = false);

                    System.out.println(superH.get(index - 1).getName() + " has been removed from the list of superheroes");
                    superH.remove(index - 1);
                    break;

                case 4://Changes the civilian count according to the user
                    for (int j = 0; j < superH.size(); j++) {
                        superH.get(j).print(j + 1);
                    }
                    if (superH.size() == 0) break;

                    System.out.println("Enter Hero number to update civilian counter or Enter 0 to cancel");
                    do {
                        System.out.print("Enter >> ");
                        counter = Integer.parseInt(sc.nextLine());
                        if (index == 0) break;
                        else if (index > 0 && index < superH.size())
                        {
                            forCheckingUpdateCount = true;
                            break;
                        }
                        System.out.println("Error input, You need to enter again");
                    } while (forCheckingUpdateCount = false);

                    System.out.print("Enter new civilian save count:");
                    int civilians = Integer.parseInt(sc.nextLine());

                    System.out.println("Number of civilians saved by " + superH.get(counter - 1).getName() + " has been updated from " + superH.get(counter - 1).getNoOfCiviliansSaved() + " to " + civilians);
                    superH.get(counter - 1).setNoOfCiviliansSave(civilians);
                    break;

                case 5://First prints temp(which has the index of the superhero with most civilian saved score) then prints second highest i.etemp1(such that temp1!=temp)
                    //Then prints temp2 (such that temp2!=temp1,temp)
                    int getFirst = 0;
                    int getSecond = 0;
                    int getThird = 0;
                    int civiliansTracker = 0;
                    if (superH.size() == 0) {
                        System.out.println("The list of superheroes is empty, add superheroes first");
                        break;
                    }

                    if (superH.size() < 3) {
                        System.out.println("There is not enough superheros in the list. Please add more.");
                        break;
                    }

                    for (int w = 0; w < superH.size(); w++) {
                        if (superH.get(w).getNoOfCiviliansSaved() != 0)
                            civiliansTracker++; }
                    if (civiliansTracker == 0) {
                        System.out.println("The superheroes have not saved enough civilians");
                        break;
                    }

                    //To get first superHero
                    for (int x = 0; x < superH.size() - 1; x++) {
                        if (superH.get(x).getNoOfCiviliansSaved() > superH.get(x + 1).getNoOfCiviliansSaved())
                            getFirst = x;
                        else
                            getFirst = x + 1;
                    }
                    superH.get(getFirst).printForTopThree();
                    //To get second superhero
                    for (int y = 0; y < superH.size() - 1; y++) {
                        if (superH.get(y).getNoOfCiviliansSaved() > superH.get(y + 1).getNoOfCiviliansSaved() && superH.get(getFirst) != superH.get(y))
                            getSecond = y;
                        else
                            getSecond = y + 1;
                    }
                    superH.get(getSecond).printForTopThree();
                    //To get Third superhero
                    for (int z = 0; z < superH.size() - 1; z++) {
                        if (superH.get(z).getNoOfCiviliansSaved() > superH.get(z + 1).getNoOfCiviliansSaved() && superH.get(getFirst) != superH.get(z) && superH.get(getSecond) != superH.get(z))
                            getThird = z;
                        else
                            getThird = z + 1;
                    }
                    superH.get(getThird).printForTopThree();
                    break;

                case 6://Uses toString() method to print the list of superheroes
                    for (int k = 0; k < superH.size(); k++) {
                        superH.get(k).toString(superH.get(k));
                    }
                    break;

                case 7://Uses Gson to store the superH list
                    try{
                        Gson gsonWrite = new GsonBuilder().setPrettyPrinting().create();
                        Writer write = Files.newBufferedWriter(Paths.get("superHero_Tracker.json"));
                        gsonWrite.toJson(superH,write);
                        write.close();
                        forCheckingSuperHeroList = true;
                        break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                default:
                    //If user inputs something apart from the MenuOptions this error is displayed
                    System.out.println("Error input, enter again");
                    break;
            }
        }
    }
}


