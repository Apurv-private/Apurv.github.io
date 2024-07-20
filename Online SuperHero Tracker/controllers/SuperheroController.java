package ca.cmpt213.a3.onlinesuperherotracker.controllers;

/**
 *  Represents a Rest API Controllers.
 *  Data includes superhero array and nextId.
 *  Methods include add,remove,listall,update,getwelcomemessage
 *  By Apurv Nerurkar, 301386528, anerurka@sfu.ca
 */
import ca.cmpt213.a3.onlinesuperherotracker.model.Superhero;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class SuperheroController {
    private List<Superhero> superH = new ArrayList<>();
    private AtomicLong nextId = new AtomicLong();

    //Prints a Welcome message
    @GetMapping("/WelcomeScreen")
    public String getWelcomeMessage(){
        return "Hello welcome to Superhero Tracker Online!";
    }

    //Adds the given hero to the superhero list
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Superhero addHero(@RequestBody Superhero superhero){
        //Checking for negative values
        if(superhero.getHeightInCm() < 0 || superhero.getCivilianSaveCount() < 0)
            throw new IllegalArgumentException();

        //Set superhero to have next ID:
        superhero.setId(nextId.incrementAndGet());
        superH.add(superhero);
        return superhero;
    }

    //Prints all the superheroes in the list
    @GetMapping("/ListAll")
    public List<Superhero> getAllHeroes(){
        return superH;
    }

    //Removes the hero given by the id
    @PostMapping("/remove/{id}")
    public List<Superhero> removeHero(@PathVariable("id") long heroId){
        for(Superhero hero : superH){
            if(hero.getId() == heroId){
                superH.remove(hero);
                return superH;
            }
        }
        throw new SuperheroNotFoundException("Invalid ID");
    }

    //Updates the information of the hero given by the id
    @PostMapping("/update/{id}")
    public List<Superhero> updateHero(@PathVariable("id") long heroId,
                                  @RequestBody Superhero newSuperHero)
    {
        if(newSuperHero.getHeightInCm() < 0 || newSuperHero.getCivilianSaveCount() < 0)
            throw new IllegalArgumentException();

        for(Superhero hero : superH)
        {
            if(hero.getId() == heroId)
            {
                superH.remove(hero);
                newSuperHero.setId(heroId);
                superH.add(newSuperHero);
                return superH;
            }
        }
        throw new SuperheroNotFoundException("Hero Not found for the given id");
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST,
                    reason = "Invalid inputs.")
    @ExceptionHandler(IllegalArgumentException.class)
    public void badInputsHandler(){
    }
}
