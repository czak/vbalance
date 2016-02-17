package pl.czak.virginbalance;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.widget.RemoteViews;

import org.json.JSONObject;

/**
 * Implementation of App Widget functionality.
 */
public class VirginAppWidget extends AppWidgetProvider {
    private static final String TAG = "VirginAppWidget";

    static void updateAppWidget(final Context context, final AppWidgetManager appWidgetManager,
                                final int appWidgetId) {

        final SharedPreferences prefs =
                context.getSharedPreferences("widget" + appWidgetId, Context.MODE_PRIVATE);

        new AsyncTask<Void, Void, Account>() {
            @Override
            protected Account doInBackground(Void... params) {
                String username = prefs.getString("username", null);
                String password = prefs.getString("password", null);
                String msisdn = prefs.getString("msisdn", null);

                try {
                    VirginApiClient client = new VirginApiClient();
                    if (client.login(username, password) != null) {
                        JSONObject json = client.fetchAccountDetails(msisdn);
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
            protected void onPostExecute(Account account) {
                if (account == null) return;

                AccountPresenter presenter = new AccountPresenter(account, context);
                RemoteViews views = new RemoteViews(context.getPackageName(), presenter.getLayoutId());

                // Basic data & general balance
                views.setTextViewText(R.id.balance_amount_text_view, presenter.getBalanceAmount());
                views.setTextViewText(R.id.account_tariff_text_view, presenter.getAccountTariff());
                views.setTextViewText(R.id.account_validity_text_view, presenter.getAccountValidity());

                // Complex bundle
                views.setTextViewText(R.id.package_name_text_view, presenter.getPackageName());
                views.setTextViewText(R.id.package_minutes_text_view, presenter.getPackageMinutes());
                views.setTextViewText(R.id.package_sms_text_view, presenter.getPackageSms());
                views.setTextViewText(R.id.package_data_text_view, presenter.getPackageData());
                views.setTextViewText(R.id.complex_bundle_validity_text_view, presenter.getComplexBundleValidity());

                // Minutes package
                views.setTextViewText(R.id.package_minutes_text_view, presenter.getPackageMinutes());
                views.setTextViewText(R.id.package_minutes_validity_text_view, presenter.getPackageMinutesValidity());
                if (presenter.isPackageMinutesActive()) {
                    views.setTextColor(R.id.package_minutes_text_view, context.getResources().getColor(R.color.virginRed));
                    views.setTextColor(R.id.package_minutes_validity_text_view, Color.BLACK);
                } else {
                    views.setTextColor(R.id.package_minutes_text_view, Color.GRAY);
                    views.setTextColor(R.id.package_minutes_validity_text_view, Color.GRAY);
                }

                // Data package
                views.setTextViewText(R.id.package_data_text_view, presenter.getPackageData());
                views.setTextViewText(R.id.package_data_validity_text_view, presenter.getPackageDataValidity());
                if (presenter.isPackageDataActive()) {
                    views.setTextColor(R.id.package_data_text_view, context.getResources().getColor(R.color.virginRed));
                    views.setTextColor(R.id.package_data_validity_text_view, Color.BLACK);
                } else {
                    views.setTextColor(R.id.package_data_text_view, Color.GRAY);
                    views.setTextColor(R.id.package_data_validity_text_view, Color.GRAY);
                }

                // Instruct the widget manager to update the widget
                appWidgetManager.updateAppWidget(appWidgetId, views);
            }
        }.execute();
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
}

