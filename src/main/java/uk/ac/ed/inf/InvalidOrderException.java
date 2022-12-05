package uk.ac.ed.inf;

public class InvalidOrderException extends Exception{
    OrderOutcome reason;
    public InvalidOrderException(OrderOutcome reason){
        super();
        this.reason = reason;
    }

    @Override
    public String getMessage() {
        return "Invalid order: " + reason.toString();
    }

    public OrderOutcome getReason() {
        return reason;
    }
}
