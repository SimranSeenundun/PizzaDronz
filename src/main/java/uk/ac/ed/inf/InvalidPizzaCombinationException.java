package uk.ac.ed.inf;

/**
 * Exception class for when there is an invalid pizza combination.
 */
public class InvalidPizzaCombinationException extends Exception {
    public InvalidPizzaCombinationException(){
        super();
    }

    @Override
    public String getMessage() {
        return "Invalid combination of pizzas";
    }
}
