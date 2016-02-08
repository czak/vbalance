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
                try {
                    JSONObject json = new JSONObject("{\"type\":\"SelfCareMsisdnDetailsDTO\",\"msisdn\":\"48123456789\",\"name\":null,\"customerBalancesDto\":{\"msisdnState\":\"active\",\"generalBalance\":{\"quantity\":17.80,\"unit\":\"PLN\",\"validDate\":\"2017-01-30T22:59:59.000+0000\",\"unlimited\":false},\"valuePackBalance\":{\"quantity\":0.00,\"unit\":\"PLN\",\"validDate\":\"2016-09-27T21:59:59.000+0000\",\"unlimited\":false},\"voiceBalance\":null,\"complexBundleVoiceBalance\":{\"quantity\":5966,\"unit\":\"SECONDS\",\"validDate\":\"2016-03-01T22:59:59.000+0000\",\"unlimited\":false},\"voiceOnNetBalance\":{\"quantity\":3000000,\"unit\":\"SECONDS\",\"validDate\":\"2016-03-01T22:59:59.000+0000\",\"unlimited\":true},\"dataBalance\":{\"quantity\":1867000,\"unit\":\"KB\",\"validDate\":\"2016-03-01T22:59:59.000+0000\",\"unlimited\":false},\"smsBalance\":{\"quantity\":49999.0,\"unit\":\"UNIT\",\"validDate\":\"2016-03-01T22:59:59.000+0000\",\"unlimited\":true}},\"lastOrders\":[{\"date\":\"2016-01-31T05:35:24.856+0000\",\"description\":\"immediate\",\"status\":\"FINISHED\",\"orderId\":3065835,\"price\":19.00},{\"date\":\"2015-12-31T06:47:31.034+0000\",\"description\":\"immediate\",\"status\":\"FINISHED\",\"orderId\":2727462,\"price\":19.00},{\"date\":\"2015-11-30T06:11:08.434+0000\",\"description\":\"immediate\",\"status\":\"FINISHED\",\"orderId\":2378633,\"price\":19.00},{\"date\":\"2015-02-26T16:01:24.381+0000\",\"description\":\"immediate\",\"status\":\"FINISHED\",\"orderId\":541257,\"price\":19.00},{\"date\":\"2015-04-01T14:07:45.216+0000\",\"description\":\"immediate\",\"status\":\"FINISHED\",\"orderId\":677786,\"price\":50.00},{\"date\":\"2015-04-01T14:13:51.805+0000\",\"description\":\"immediate\",\"status\":\"FINISHED\",\"orderId\":677812,\"price\":5.00},{\"date\":\"2015-04-18T13:24:11.545+0000\",\"description\":\"immediate\",\"status\":\"FINISHED\",\"orderId\":744379,\"price\":19.00},{\"date\":\"2015-05-28T18:28:04.892+0000\",\"description\":\"immediate\",\"status\":\"FINISHED\",\"orderId\":917386,\"price\":19.00},{\"date\":\"2015-06-28T14:30:05.393+0000\",\"description\":\"immediate\",\"status\":\"FINISHED\",\"orderId\":1098369,\"price\":19.00},{\"date\":\"2015-07-29T08:54:12.396+0000\",\"description\":\"immediate\",\"status\":\"FINISHED\",\"orderId\":1286776,\"price\":19.00},{\"date\":\"2015-08-29T06:05:22.673+0000\",\"description\":\"immediate\",\"status\":\"FINISHED\",\"orderId\":1536505,\"price\":19.00},{\"date\":\"2015-09-29T04:41:54.983+0000\",\"description\":\"immediate\",\"status\":\"FINISHED\",\"orderId\":1817534,\"price\":19.00},{\"date\":\"2015-09-28T18:38:03.911+0000\",\"description\":\"immediate\",\"status\":\"FINISHED\",\"orderId\":1815881,\"price\":10.00},{\"date\":\"2015-10-30T05:29:04.946+0000\",\"description\":\"immediate\",\"status\":\"FINISHED\",\"orderId\":2074980,\"price\":19.00}],\"accountValidDate\":\"2017-01-30T22:59:59.000+0000\",\"complexBundleName\":\"#pełenluz\",\"complexBundleValidDate\":\"2016-03-01T22:59:59.000+0000\",\"newTariff\":true,\"tariffName\":\"#ChcęWszystko\",\"migratedFromOldTariff\":false,\"migrationTargetTariff\":null,\"migrationPending\":false,\"bundleOperationsFlags\":false,\"mnp\":true,\"complexBundleState\":\"ACTIVATED\",\"bundleFund\":\"PAYPAL\",\"complexBundleActivationSubmitted\":false,\"virginClubAwardDto\":null,\"mgmParentMsisdn\":null,\"lastCalls\":null}");
                    return new AccountDetails(json);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(AccountDetails details) {
                if (details == null) return;

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
