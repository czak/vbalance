package pl.czak.virginbalance;

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
        // TODO: Parse balances from the JSON
        generalBalance = new Balance(7.8, "PLN", false);
        voiceBalance = new Balance(5966, "SECONDS", false);
        smsBalance = new Balance(49999, "UNIT", true);
        dataBalance = new Balance(1867000, "KB", false);
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
