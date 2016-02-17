package pl.czak.virginbalance;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class VirginAppWidget extends AppWidgetProvider {
    private static final String TAG = "VirginAppWidget";

    static void updateAppWidget(Context context, final AppWidgetManager appWidgetManager,
                                final int appWidgetId) {
        Log.d(TAG, "updateAppWidget() called with: " + "context = [" + context + "], appWidgetManager = [" + appWidgetManager + "], appWidgetId = [" + appWidgetId + "]");

        // TODO: Fetch account for the current widget
        Account account = new Account();
        AccountPresenter presenter = new AccountPresenter(account, context);

        // Construct the RemoteViews object
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

        // Data package
        views.setTextViewText(R.id.package_data_text_view, presenter.getPackageSms());
        views.setTextViewText(R.id.package_data_validity_text_view, presenter.getPackageSmsValidity());

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d(TAG, "onUpdate() called with: " + "context = [" + context + "], appWidgetManager = [" + appWidgetManager + "], appWidgetIds = [" + appWidgetIds + "]");

        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

