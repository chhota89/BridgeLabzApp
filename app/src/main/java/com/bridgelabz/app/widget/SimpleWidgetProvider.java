package com.bridgelabz.app.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.bridgelabz.app.R;

import java.util.Random;

/**
 * Created by bridgelabz5 on 27/5/16.
 */
public class SimpleWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int i=0;i<appWidgetIds.length;i++){
            int widgetId = appWidgetIds[i];
            String number = String.format("%03d", (new Random().nextInt(900) + 100));
            RemoteViews remoteViews=new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            remoteViews.setTextViewText(R.id.text,number);

            Intent intent=new Intent(context,SimpleWidgetProvider.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,widgetId);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.updateWidget, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }
}
