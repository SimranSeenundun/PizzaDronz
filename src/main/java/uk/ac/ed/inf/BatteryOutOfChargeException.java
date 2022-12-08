package uk.ac.ed.inf;

public class BatteryOutOfChargeException extends Exception{
    public BatteryOutOfChargeException(){
        super();
    }

    @Override
    public String getMessage() {
        return "Battery is out of charge!";
    }
}
