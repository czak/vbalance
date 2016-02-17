package pl.czak.virginbalance;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by czak on 09/02/16.
 */
public class VirginApiClient {
    private static final String TAG = "VirginApiClient";

    private static final String URL_BASE = "https://virginmobile.pl/spitfire-web-api/api/v1";

    private static final String URL_LOGIN          = URL_BASE + "/authentication/login";
    private static final String URL_LOGOUT         = URL_BASE + "/authentication/logout";
    private static final String URL_USER           = URL_BASE + "/user";
    private static final String URL_MSISDN_DETAILS = URL_BASE + "/selfCare/msisdnDetails";

    public UserData login(String username, String password) throws IOException, JSONException {
        HttpURLConnection conn =
                (HttpURLConnection) new URL(URL_LOGIN).openConnection();
        conn.setRequestMethod("POST");
        conn.setReadTimeout(10_000);
        conn.setConnectTimeout(15_000);
        conn.setDoOutput(true);

        PrintStream out = new PrintStream(conn.getOutputStream());
        out.printf("username=%s&password=%s",
                URLEncoder.encode(username, "UTF-8"),
                URLEncoder.encode(password, "UTF-8"));
        out.close();

        conn.connect();

        logResponse("Login", conn);

        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            JSONObject json = new JSONObject(conn.getHeaderField("UserData"));
            return new UserData(json);
        } else {
            return null;
        }
    }

    public void logout() throws IOException {
        HttpURLConnection conn =
                (HttpURLConnection) new URL(URL_LOGOUT).openConnection();
        conn.setRequestMethod("GET");
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);

        conn.connect();

        logResponse("Logout", conn);
    }

    public JSONObject fetchAccountDetails(String msisdn) throws JSONException, IOException {
        HttpURLConnection conn =
                (HttpURLConnection) new URL(URL_MSISDN_DETAILS).openConnection();
        conn.setRequestMethod("GET");
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestProperty("msisdn", msisdn);
        conn.setDoInput(true);

        conn.connect();

        logResponse("Fetch", conn);

        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            return new JSONObject(readStream(conn.getInputStream()));
        } else {
            return null;
        }
    }

    private String readStream(InputStream in) throws IOException {
        byte[] buffer = new byte[1000];
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            int len;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            return out.toString();
        } finally {
            in.close();
            out.close();
        }
    }

    private void logResponse(String label, HttpURLConnection conn) throws IOException {
        int code = conn.getResponseCode();
        Log.d(TAG, label + " response code: " + code);

        if (code != HttpURLConnection.HTTP_OK) {
            Log.d(TAG, label + " body: " + readStream(conn.getErrorStream()));
        }
    }
}
