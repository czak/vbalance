package pl.czak.virginbalance;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by czak on 08/02/16.
 */
public class AccountDetails {
    private Balance generalBalance;
    private Balance voiceBalance;
    private Balance smsBalance;
    private Balance dataBalance;

    public AccountDetails() {
    }

    public AccountDetails(JSONObject json) throws JSONException {
        JSONObject balances = json.getJSONObject("customerBalancesDto");
        generalBalance = new Balance(balances.getJSONObject("generalBalance"));
        voiceBalance = new Balance(balances.getJSONObject("complexBundleVoiceBalance"));
        smsBalance = new Balance(balances.getJSONObject("smsBalance"));
        dataBalance = new Balance(balances.getJSONObject("dataBalance"));
    }

    public Balance getGeneralBalance() {
        return generalBalance;
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
}
