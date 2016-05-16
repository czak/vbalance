package pl.czak.vbalance;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by czak on 17/02/16.
 */
public class UserData {
    private JSONObject json;

    public UserData(JSONObject json) {
        this.json = json;
    }

    public String[] getMsisdns() throws JSONException {
        JSONArray jsonArray = json.getJSONArray("msisdns");
        String[] result = new String[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            result[i] = jsonArray.getString(i);
        }

        return result;
    }
}
