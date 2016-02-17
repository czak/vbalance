package pl.czak.virginbalance;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private TextView balanceAmountTextView;
    private TextView packageMinutesTextView;
    private TextView packageSmsTextView;
    private TextView packageDataTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_bundle);

//        balanceAmountTextView = (TextView) findViewById(R.id.balance_amount_text_view);
//        packageMinutesTextView = (TextView) findViewById(R.id.package_minutes_text_view);
//        packageSmsTextView = (TextView) findViewById(R.id.package_sms_text_view);
//        packageDataTextView = (TextView) findViewById(R.id.package_data_text_view);

        final PropertyManager props = new PropertyManager(this);

        new AsyncTask<Void, Void, Account>() {
            @Override
            protected Account doInBackground(Void... params) {
                try {
                    VirginApiClient client = new VirginApiClient();
                    if (client.login(props.getUsername(), props.getPassword())) {
                        JSONObject json = client.fetchAccountDetails(props.getMsisdn());
                        client.logout();
                        return new Account(json);
                    } else {
                        // TODO: Notify user of failed login
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Account details) {
                if (details == null) return;

                // TODO: Move to a presenter or some such
                // General balance
                Balance balance = details.getGeneralBalance();
                String text = String.format("%.2f %s", balance.getQuantity(), balance.getUnit());
                //balanceAmountTextView.setText(text);

                // Minutes in package (stores in seconds)
                balance = details.getVoiceBalance();
                text = String.format("%.0f", balance.getQuantity() / 60);
                //packageMinutesTextView.setText(text);

                // SMS in package
                balance = details.getSmsBalance();
                text = balance.isUnlimited() ? "Bez limitu" : String.format("%.0f", balance.getQuantity());
                //packageSmsTextView.setText(text);

                // Data in package (stored in KB, divide by 1000)
                balance = details.getDataBalance();
                text = String.format("%.0f MB", balance.getQuantity() / 1000);
                //packageDataTextView.setText(text);
            }
        }.execute();
    }
}
