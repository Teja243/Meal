package com.example.personal.meal;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {
    private static SharedPreferences sharedPreferences;
    static String string;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
           // updateAppWidget(context, appWidgetManager, appWidgetId);
            sharedPreferences=context.getSharedPreferences(context.getString(R.string.data1),Context.MODE_PRIVATE);
            string=sharedPreferences.getString("name","no data available");

            Intent intent=new Intent(context,Meal.class);
            PendingIntent pendingIntent=PendingIntent.getActivity(context,1,intent,0);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
            views.setTextViewText(R.id.appwidget_text,string);
            views.setOnClickPendingIntent(R.id.appwidget_text,pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetId, views);
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

