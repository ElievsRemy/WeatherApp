package com.elievsremy.weatherapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class WeatherAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        String name = PreferenceManager.getDefaultSharedPreferences(context).getString("Name","error");
        String temp = PreferenceManager.getDefaultSharedPreferences(context).getString("Temp","error");
        //String image = PreferenceManager.getDefaultSharedPreferences(context).getString("Image","error");

        RemoteViews city = new RemoteViews(context.getPackageName(), R.layout.weather_app_widget);
        city.setTextViewText(R.id.appwidget_text, name);

        RemoteViews tmp = new RemoteViews(context.getPackageName(), R.id.temp_widget);
        tmp.setTextViewText(R.id.temp_widget, temp);

        /*RemoteViews img = new RemoteViews(context.getPackageName(), R.id.img_widget);
        img.setTextViewText(R.id.appwidget_text, image);*/

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, city);
        //appWidgetManager.updateAppWidget(appWidgetId, tmp);
        //appWidgetManager.updateAppWidget(appWidgetId, img);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
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

