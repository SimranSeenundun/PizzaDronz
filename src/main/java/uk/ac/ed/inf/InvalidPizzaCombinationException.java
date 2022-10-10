package uk.ac.ed.inf;

public class InvalidPizzaCombinationException extends Exception {
    public InvalidPizzaCombinationException(){
        super();
    }

    @Override
    public String getMessage() {
        return "Invalid combination of pizzas";
    }
}
