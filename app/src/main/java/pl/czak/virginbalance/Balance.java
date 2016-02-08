package pl.czak.virginbalance;

import org.json.JSONObject;

/**
 * Created by czak on 08/02/16.
 */
public class Balance {
    private double quantity;
    private String unit;
    private boolean unlimited;

    public Balance(JSONObject json) {
        this.quantity = json.optDouble("quantity");
        this.unit = json.optString("unit");
        this.unlimited = json.optBoolean("unlimited");
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