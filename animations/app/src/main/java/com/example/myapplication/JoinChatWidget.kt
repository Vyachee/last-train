package com.example.myapplication

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.AlarmManager
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.RemoteViews
import android.widget.Toast

/**
 * Implementation of App Widget functionality.
 */
class JoinChatWidget : AppWidgetProvider() {

    val timer = "android.appwidget.action.TIMER"

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    @SuppressLint("ShortAlarm", "UnspecifiedImmutableFlag")
    override fun onEnabled(context: Context) {

        val intent = Intent(context, JoinChatWidget::class.java)
        intent.action = timer
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarm: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarm.cancel(pendingIntent)
        val interval: Long = 1000
        alarm.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), interval, pendingIntent)

    }

    override fun onDisabled(context: Context) {
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        val action = intent?.action
        when(action) {
            timer -> {
                val sharedPrefs = context?.getSharedPreferences("widget_settings", MODE_PRIVATE)
                val editor = sharedPrefs?.edit()
                val currentCount = sharedPrefs?.getInt("count", 5)
                var nextCount = currentCount?.minus(1)

                if(nextCount == 0) {
                    nextCount = 5
                }

                if (nextCount != null) {
                    editor?.putInt("count", nextCount)
                }

                editor?.apply()

                val avatarIds = arrayListOf (R.id.ivp1, R.id.ivp2, R.id.ivp3, R.id.ivp4, R.id.ivp5)
                val views = RemoteViews(context?.packageName, R.layout.join_chat_widget)

                for(id in avatarIds) {
                    views.setViewVisibility(id, View.INVISIBLE)
                }

                for(index in 0 until nextCount!!) {
                    views.setViewVisibility(avatarIds.get(index), View.VISIBLE)
                }

                AppWidgetManager.getInstance(context).updateAppWidget(
                    context?.let { ComponentName(it, JoinChatWidget::class.java) },
                    views
                )

            }
        }

    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val views = RemoteViews(context.packageName, R.layout.join_chat_widget)

    val prefs = context.getSharedPreferences("widget_settings", MODE_PRIVATE)
    val intent = Intent(context, MainActivity::class.java)
    intent.putExtra("count", prefs.getInt("count", 5))
    val pendingIntent = PendingIntent.getActivity(context, 1, intent, 0)
    views.setOnClickPendingIntent(R.id.textView3, pendingIntent)

    appWidgetManager.updateAppWidget(appWidgetId, views)
}