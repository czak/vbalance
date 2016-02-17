package pl.czak.virginbalance;

import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by czak on 08/02/16.
 */
public class Account {
    private JSONObject json;

    private Balance generalBalance;
    private Balance complexBundleVoiceBalance;
    private Balance voiceBalance;
    private Balance smsBalance;
    private Balance dataBalance;

    public Account(JSONObject json) throws JSONException {
        this.json = json;

        JSONObject balances = json.getJSONObject("customerBalancesDto");
        generalBalance = new Balance(balances.getJSONObject("generalBalance"));
        try {
            complexBundleVoiceBalance = new Balance(balances.getJSONObject("complexBundleVoiceBalance"));
        } catch (JSONException e) {}
        try {
            voiceBalance = new Balance(balances.getJSONObject("voiceBalance"));
        } catch (JSONException e) {}
        smsBalance = new Balance(balances.getJSONObject("smsBalance"));
        dataBalance = new Balance(balances.getJSONObject("dataBalance"));
    }

    public Balance getGeneralBalance() {
        return generalBalance;
    }

    public Balance getComplexBundleVoiceBalance() {
        return complexBundleVoiceBalance;
    }

    public Balance getVoiceBalance() {
        return voiceBalance;
    }

    public Balance getSmsBalance() {
        return smsBalance;
    }

    public Balance getDataBalance() {
        return dataBalance;
    }

    public String getTariffName() {
        return json.optString("tariffName");
    }

    public Date getAccountValidDate() {
        return Utils.parseDate(json.optString("accountValidDate"));
    }

    public String getComplexBundleName() {
        return json.optString("complexBundleName");
    }

    public Date getComplexBundleValidDate() {
        return Utils.parseDate(json.optString("complexBundleValidDate"));
    }

    public String getComplexBundleState() {
        return json.optString("complexBundleState");
    }
}
