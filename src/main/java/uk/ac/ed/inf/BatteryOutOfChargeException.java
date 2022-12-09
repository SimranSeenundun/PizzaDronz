package uk.ac.ed.inf;

/**
 * Thrown when the drone runs ou of charge
 */
public class BatteryOutOfChargeException extends Exception{
    public BatteryOutOfChargeException(){
        super();
    }

    @Override
    public String getMessage() {
        return "Battery is out of charge!";
    }
}
