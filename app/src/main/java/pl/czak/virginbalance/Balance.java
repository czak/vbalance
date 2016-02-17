package pl.czak.virginbalance;

import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by czak on 08/02/16.
 */
public class Balance {
    private double quantity;
    private String unit;
    private boolean unlimited;
    private Date validDate;

    public Balance(JSONObject json) {
        this.quantity = json.optDouble("quantity");
        this.unit = json.optString("unit");
        this.unlimited = json.optBoolean("unlimited");
        this.validDate = Utils.parseDate(json.optString("validDate"));
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

    public Date getValidDate() {
        return validDate;
    }
}