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

        // Construct the RemoteViews object
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_nobundle);
        views.setTextViewText(R.id.balance_amount_text_view, "12,30 zł");
//        views.setTextViewText(R.id.package_minutes_text_view, "123");
//        views.setTextViewText(R.id.package_sms_text_view, "Bez limitu");
//        views.setTextViewText(R.id.package_data_text_view, "1500 MB");

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

