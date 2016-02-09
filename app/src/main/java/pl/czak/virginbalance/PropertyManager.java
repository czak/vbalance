package pl.czak.virginbalance;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by czak on 09/02/16.
 */
public class PropertyManager {
    private static final String TAG = "PropertyManager";

    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_MSISDN = "msisdn";

    private Properties props = new Properties();

    public PropertyManager(Context ctx) {
        InputStream in = null;
        try {
            in = ctx.getResources().getAssets().open("config.properties");
            props.load(in);
        } catch (IOException e) {
            Log.d(TAG, "Failed to load config.properties");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {

                }
            }
        }
    }

    public String getUsername() {
        return props.getProperty(KEY_USERNAME);
    }

    public String getPassword() {
        return props.getProperty(KEY_PASSWORD);
    }

    public String getMsisdn() {
        return props.getProperty(KEY_MSISDN);
    }
}
