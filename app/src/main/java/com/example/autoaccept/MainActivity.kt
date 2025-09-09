
package com.example.autoaccept

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import androidx.activity.ComponentActivity
import com.example.autoaccept.services.ServiceController

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    findViewById<Button>(R.id.btnNotifAccess).setOnClickListener {
      startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
    }
    findViewById<Button>(R.id.btnAccSettings).setOnClickListener {
      startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
    }
    findViewById<Button>(R.id.btnStart).setOnClickListener {
      ServiceController.startForeground(this)
    }
    findViewById<Button>(R.id.btnStop).setOnClickListener {
      ServiceController.stopForeground(this)
    }
  }
}
