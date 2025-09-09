
package com.example.autoaccept.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.autoaccept.R
import com.example.autoaccept.MainActivity

object ServiceController {
  private const val ID = 101
  fun startForeground(ctx: Context) {
    val intent = PendingIntent.getActivity(
      ctx, 0, Intent(ctx, MainActivity::class.java),
      PendingIntent.FLAG_UPDATE_CURRENT or (if (Build.VERSION.SDK_INT >= 31) PendingIntent.FLAG_MUTABLE else 0)
    )
    val notif = Notification.Builder(ctx, "autoaccept")
      .setSmallIcon(R.mipmap.ic_launcher)
      .setContentTitle("AutoAccept running")
      .setContentText("Listening for offers & automating per your rules")
      .setContentIntent(intent)
      .build()
    JobNotificationListener.ensureServiceAlive(ctx, notif, ID)
  }
  fun stopForeground(ctx: Context) {
    JobNotificationListener.stopService(ctx)
  }
}
