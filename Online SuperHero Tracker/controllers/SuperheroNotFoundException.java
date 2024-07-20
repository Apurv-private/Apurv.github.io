package ca.cmpt213.a3.onlinesuperherotracker.controllers;
/**
 *  Represents a Exception Handler class.
 *  By Apurv Nerurkar, 301386528, anerurka@sfu.ca
 */
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SuperheroNotFoundException extends RuntimeException {

    SuperheroNotFoundException(String message) {
        super(message);
    }
}
