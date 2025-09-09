
package com.example.autoaccept.services

import android.app.Notification
import android.content.Intent
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.example.autoaccept.rules.RuleEngine

class JobNotificationListener : NotificationListenerService() {

  companion object {
    private var instance: JobNotificationListener? = null
    fun ensureServiceAlive(ctx: android.content.Context, notification: Notification, id: Int) {
      instance?.startForeground(id, notification)
    }
    fun stopService(ctx: android.content.Context) {
      instance?.stopForeground(true)
      ctx.stopService(Intent(ctx, JobNotificationListener::class.java))
    }
  }

  override fun onListenerConnected() {
    super.onListenerConnected()
    instance = this
    Log.i("AutoAccept", "Notification listener connected")
  }
  override fun onListenerDisconnected() {
    super.onListenerDisconnected()
    instance = null
    Log.w("AutoAccept", "Notification listener disconnected")
  }

  override fun onNotificationPosted(sbn: StatusBarNotification) {
    val pkg = sbn.packageName ?: return
    val extras = sbn.notification.extras
    val title = extras?.getCharSequence("android.title")?.toString() ?: ""
    val text = extras?.getCharSequence("android.text")?.toString() ?: ""

    if (RuleEngine.shouldAccept(pkg, title, text)) {
      try {
        val launch = packageManager.getLaunchIntentForPackage(pkg)
        if (launch != null) {
          launch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
          startActivity(launch)
        }
      } catch (e: Exception) {
        Log.e("AutoAccept", "Failed to launch target app: $e")
      }
      AutoAcceptAccessibility.requestAccept()
    }
  }
}
