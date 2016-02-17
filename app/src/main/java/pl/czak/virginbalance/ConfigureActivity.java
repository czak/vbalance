package pl.czak.virginbalance;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by czak on 09/02/16.
 */
public class ConfigureActivity extends AppCompatActivity {
    private int appWidgetId;

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure);

        loginButton = (Button) findViewById(R.id.login_button);
        emailEditText = (EditText) findViewById(R.id.email_edit_text);
        passwordEditText = (EditText) findViewById(R.id.password_edit_text);
        listView = (ListView) findViewById(R.id.msisdn_list_view);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            appWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String msisdn = adapter.getItem(position);

                // TODO: Introduce a helper object for the prefs
                SharedPreferences prefs =
                        getSharedPreferences("widget" + appWidgetId, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("username", email);
                editor.putString("password", password);
                editor.putString("msisdn", msisdn);
                editor.commit();

                AppWidgetManager appWidgetManager =
                        AppWidgetManager.getInstance(ConfigureActivity.this);
                VirginAppWidget.updateAppWidget(ConfigureActivity.this, appWidgetManager, appWidgetId);

                Intent resultValue = new Intent();
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                setResult(RESULT_OK, resultValue);
                finish();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailEditText.getText().toString();
                final String password = passwordEditText.getText().toString();

                new AsyncTask<Void, Void, UserData>() {
                    @Override
                    protected UserData doInBackground(Void... params) {
                        try {
                            VirginApiClient client = new VirginApiClient();
                            return client.login(email, password);
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }

                        return null;
                    }

                    @Override
                    protected void onPostExecute(UserData userData) {
                        if (userData == null) {
                            Toast.makeText(ConfigureActivity.this,
                                    R.string.login_failed,
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }

                        try {
                            adapter.clear();
                            adapter.addAll(userData.getMsisdns());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }.execute();
            }
        });
    }
}
