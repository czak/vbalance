package pl.czak.vbalance;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;
import android.widget.RemoteViews;

import org.json.JSONObject;

/**
 * Implementation of App Widget functionality.
 */
public class AppWidget extends AppWidgetProvider {
    private static final String ACTION_FORCE_UPDATE = "pl.czak.vbalance.FORCE_UPDATE";

    static void updateAppWidget(final Context context, final AppWidgetManager appWidgetManager,
                                final int appWidgetId) {

        final SharedPreferences prefs =
                context.getSharedPreferences("widget" + appWidgetId, Context.MODE_PRIVATE);

        final RemoteViews baseViews = new RemoteViews(context.getPackageName(), R.layout.widget_frame);

        // Handle clicking on the frame
        Intent intent = new Intent(context, AppWidget.class);
        intent.setAction(ACTION_FORCE_UPDATE);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, appWidgetId, intent, 0);
        baseViews.setOnClickPendingIntent(R.id.widget_frame, pendingIntent);

        // Turn on the spinner and hide the error symbol
        baseViews.setViewVisibility(R.id.progress_bar, View.VISIBLE);
        baseViews.setViewVisibility(R.id.error_image_view, View.INVISIBLE);

        appWidgetManager.updateAppWidget(appWidgetId, baseViews);

        new AsyncTask<Void, Void, Account>() {
            @Override
            protected Account doInBackground(Void... params) {
                String username = prefs.getString("username", null);
                String password = prefs.getString("password", null);
                String msisdn = prefs.getString("msisdn", null);

                try {
                    ApiClient client = new ApiClient();
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
                // Hide the spinner first
                baseViews.setViewVisibility(R.id.progress_bar, View.INVISIBLE);

                if (account == null) {
                    baseViews.setViewVisibility(R.id.error_image_view, View.VISIBLE);
                    appWidgetManager.updateAppWidget(appWidgetId, baseViews);
                    return;
                }

                AccountPresenter presenter = new AccountPresenter(account, context);

                // Basic data & general balance
                baseViews.setTextViewText(R.id.balance_amount_text_view, presenter.getBalanceAmount());
                baseViews.setTextViewText(R.id.account_tariff_text_view, presenter.getAccountTariff());
                baseViews.setTextViewText(R.id.account_validity_text_view, presenter.getAccountValidity());

                // Views for the right pane
                RemoteViews paneViews = new RemoteViews(context.getPackageName(), presenter.getPaneLayoutId());
                baseViews.removeAllViews(R.id.right_pane_frame);
                baseViews.addView(R.id.right_pane_frame, paneViews);

                // Complex bundle
                paneViews.setTextViewText(R.id.package_name_text_view, presenter.getPackageName());
                paneViews.setTextViewText(R.id.package_minutes_text_view, presenter.getPackageMinutes());
                paneViews.setTextViewText(R.id.package_sms_text_view, presenter.getPackageSms());
                paneViews.setTextViewText(R.id.package_data_text_view, presenter.getPackageData());
                paneViews.setTextViewText(R.id.complex_bundle_validity_text_view, presenter.getComplexBundleValidity());

                // Minutes package
                paneViews.setTextViewText(R.id.package_minutes_text_view, presenter.getPackageMinutes());
                paneViews.setTextViewText(R.id.package_minutes_validity_text_view, presenter.getPackageMinutesValidity());
                if (presenter.isPackageMinutesActive()) {
                    paneViews.setTextColor(R.id.package_minutes_text_view, context.getResources().getColor(R.color.virginRed));
                    paneViews.setTextColor(R.id.package_minutes_validity_text_view, Color.BLACK);
                } else {
                    paneViews.setTextColor(R.id.package_minutes_text_view, Color.GRAY);
                    paneViews.setTextColor(R.id.package_minutes_validity_text_view, Color.GRAY);
                }

                // Data package
                paneViews.setTextViewText(R.id.package_data_text_view, presenter.getPackageData());
                paneViews.setTextViewText(R.id.package_data_validity_text_view, presenter.getPackageDataValidity());
                if (presenter.isPackageDataActive()) {
                    paneViews.setTextColor(R.id.package_data_text_view, context.getResources().getColor(R.color.virginRed));
                    paneViews.setTextColor(R.id.package_data_validity_text_view, Color.BLACK);
                } else {
                    paneViews.setTextColor(R.id.package_data_text_view, Color.GRAY);
                    paneViews.setTextColor(R.id.package_data_validity_text_view, Color.GRAY);
                }

                // Instruct the widget manager to update the widget
                appWidgetManager.updateAppWidget(appWidgetId, baseViews);
            }
        }.execute();
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (intent.getAction().equals(ACTION_FORCE_UPDATE)) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
}

