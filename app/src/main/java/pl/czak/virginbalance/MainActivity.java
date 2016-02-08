package pl.czak.virginbalance;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    private TextView balanceAmountTextView;
    private TextView packageMinutesTextView;
    private TextView packageSmsTextView;
    private TextView packageDataTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.virgin_app_widget);

        balanceAmountTextView = (TextView) findViewById(R.id.balance_amount_text_view);
        packageMinutesTextView = (TextView) findViewById(R.id.package_minutes_text_view);
        packageSmsTextView = (TextView) findViewById(R.id.package_sms_text_view);
        packageDataTextView = (TextView) findViewById(R.id.package_data_text_view);

        new AsyncTask<Void, Void, AccountDetails>() {
            @Override
            protected AccountDetails doInBackground(Void... params) {
                // TODO: Retrieve data from the API
                return new AccountDetails();
            }

            @Override
            protected void onPostExecute(AccountDetails details) {
                // TODO: Move to a presenter or some such
                // General balance
                Balance balance = details.getGeneralBalance();
                String text = String.format("%.2f %s", balance.getQuantity(), balance.getUnit());
                balanceAmountTextView.setText(text);

                // Minutes in package (stores in seconds)
                balance = details.getVoiceBalance();
                text = String.format("%.0f", balance.getQuantity() / 60);
                packageMinutesTextView.setText(text);

                // SMS in package
                balance = details.getSmsBalance();
                text = balance.isUnlimited() ? "Bez limitu" : String.format("%.0f", balance.getQuantity());
                packageSmsTextView.setText(text);

                // Data in package (stored in KB, divide by 1000)
                balance = details.getDataBalance();
                text = String.format("%.0f MB", balance.getQuantity() / 1000);
                packageDataTextView.setText(text);
            }
        }.execute();
    }
}
