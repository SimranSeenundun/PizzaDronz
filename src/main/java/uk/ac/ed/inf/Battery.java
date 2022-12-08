package uk.ac.ed.inf;

public class Battery {
    public static final int DEFAULT_MAX_CHARGE = 1000;

    private int currentCharge;

    private final int maxCharge;

    public Battery() {
        currentCharge = DEFAULT_MAX_CHARGE;
        maxCharge = DEFAULT_MAX_CHARGE;
    }
    public Battery(int currentCharge, int maxCharge) {
        this.currentCharge = currentCharge;
        this.maxCharge = maxCharge;
    }

    public int getCurrentCharge() {
        return currentCharge;
    }

    public int getMaxCharge() {
        return maxCharge;
    }

    public void decrementCharge() throws BatteryOutOfChargeException{
        currentCharge -= 1;
        if (currentCharge <= 0){
            throw new BatteryOutOfChargeException();
        }
    }
}
