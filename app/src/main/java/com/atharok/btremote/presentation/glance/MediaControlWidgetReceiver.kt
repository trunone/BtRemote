package com.atharok.btremote.presentation.glance

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.bluetooth.BluetoothHidDevice
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.atharok.btremote.R
import com.atharok.btremote.domain.usecases.BluetoothHidServiceUseCase
import com.atharok.btremote.presentation.activities.MainActivity
import com.atharok.btremote.presentation.services.NotificationBroadcastReceiver
import com.atharok.btremote.presentation.services.NotificationBroadcastReceiver.Companion.ACTION_BACK
import com.atharok.btremote.presentation.services.NotificationBroadcastReceiver.Companion.ACTION_DOWN
import com.atharok.btremote.presentation.services.NotificationBroadcastReceiver.Companion.ACTION_HOME
import com.atharok.btremote.presentation.services.NotificationBroadcastReceiver.Companion.ACTION_LEFT
import com.atharok.btremote.presentation.services.NotificationBroadcastReceiver.Companion.ACTION_MULTIMEDIA_NEXT
import com.atharok.btremote.presentation.services.NotificationBroadcastReceiver.Companion.ACTION_MULTIMEDIA_PLAY_PAUSE
import com.atharok.btremote.presentation.services.NotificationBroadcastReceiver.Companion.ACTION_MULTIMEDIA_PREVIOUS
import com.atharok.btremote.presentation.services.NotificationBroadcastReceiver.Companion.ACTION_PICK
import com.atharok.btremote.presentation.services.NotificationBroadcastReceiver.Companion.ACTION_RIGHT
import com.atharok.btremote.presentation.services.NotificationBroadcastReceiver.Companion.ACTION_UP
import com.atharok.btremote.presentation.services.NotificationBroadcastReceiver.Companion.ACTION_VOLUME_DOWN
import com.atharok.btremote.presentation.services.NotificationBroadcastReceiver.Companion.ACTION_VOLUME_UP
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MediaControlWidgetReceiver : AppWidgetProvider(), KoinComponent {

    private val bluetoothHidServiceUseCase: BluetoothHidServiceUseCase by inject()

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        updateAllWidgets(context, appWidgetManager, appWidgetIds)
    }

    private fun updateAllWidgets(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        CoroutineScope(Dispatchers.Main).launch {
            val state = bluetoothHidServiceUseCase.getDeviceHidConnectionState().first()
            val remoteViews = RemoteViews(context.packageName, R.layout.notification_large_layout).apply {
                setOnClickPendingIntent(R.id.notification_large_layout_volume_inc, createPendingIntent(context, ACTION_VOLUME_UP))
                setOnClickPendingIntent(R.id.notification_large_layout_volume_dec, createPendingIntent(context, ACTION_VOLUME_DOWN))
                setOnClickPendingIntent(R.id.notification_large_layout_play_pause, createPendingIntent(context, ACTION_MULTIMEDIA_PLAY_PAUSE))
                setOnClickPendingIntent(R.id.notification_large_layout_previous, createPendingIntent(context, ACTION_MULTIMEDIA_PREVIOUS))
                setOnClickPendingIntent(R.id.notification_large_layout_next, createPendingIntent(context, ACTION_MULTIMEDIA_NEXT))
                setOnClickPendingIntent(R.id.notification_large_layout_left, createPendingIntent(context, ACTION_LEFT))
                setOnClickPendingIntent(R.id.notification_large_layout_right, createPendingIntent(context, ACTION_RIGHT))
                setOnClickPendingIntent(R.id.notification_large_layout_up, createPendingIntent(context, ACTION_UP))
                setOnClickPendingIntent(R.id.notification_large_layout_down, createPendingIntent(context, ACTION_DOWN))
                setOnClickPendingIntent(R.id.notification_large_layout_pick, createPendingIntent(context, ACTION_PICK))
                setOnClickPendingIntent(R.id.notification_large_layout_back, createPendingIntent(context, ACTION_BACK))
                setOnClickPendingIntent(R.id.notification_large_layout_home, createPendingIntent(context, ACTION_HOME))
            }
            for (widgetId in appWidgetIds) {
                appWidgetManager.updateAppWidget(widgetId, remoteViews)
            }
        }
    }

    private fun createPendingIntent(context: Context, action: String): PendingIntent {
        val intent = Intent(context, NotificationBroadcastReceiver::class.java).apply { setAction(action) }
        return PendingIntent.getBroadcast(context, action.hashCode(), intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
    }

    companion object {
        fun updateWidget(context: Context) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val componentName = ComponentName(context, MediaControlWidgetReceiver::class.java)
            val appWidgetIds = appWidgetManager.getAppWidgetIds(componentName)
            if (appWidgetIds.isNotEmpty()) {
                val intent = Intent(context, MediaControlWidgetReceiver::class.java).apply {
                    action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
                    putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds)
                }
                context.sendBroadcast(intent)
            }
        }
    }
}