package pl.czak.virginbalance;

/**
 * Created by czak on 08/02/16.
 */
public class Balance {
    private double quantity;
    private String unit;
    private boolean unlimited;

    public Balance(double quantity, String unit, boolean unlimited) {
        this.quantity = quantity;
        this.unit = unit;
        this.unlimited = unlimited;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public boolean isUnlimited() {
        return unlimited;
    }
}