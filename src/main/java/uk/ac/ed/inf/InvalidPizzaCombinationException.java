package uk.ac.ed.inf;

/**
 * Exception class for when there is an invalid pizza combination.
 */
public class InvalidPizzaCombinationException extends InvalidOrderException {
    OrderOutcome reason;
    public InvalidPizzaCombinationException(OrderOutcome reason){
        super(reason);
    }

    @Override
    public String getMessage() {
        return "Invalid combination of pizzas: " + reason.toString();
    }
}
