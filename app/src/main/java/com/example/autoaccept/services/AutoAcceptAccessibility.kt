
package com.example.autoaccept.services

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.example.autoaccept.rules.Selectors

class AutoAcceptAccessibility : AccessibilityService() {

  companion object {
    @Volatile private var shouldAttemptAccept: Boolean = false
    fun requestAccept() { shouldAttemptAccept = true }
  }
  private val uiHandler = Handler(Looper.getMainLooper())

  override fun onAccessibilityEvent(event: AccessibilityEvent?) {
    if (!shouldAttemptAccept) return
    val root = rootInActiveWindow ?: return

    val nodes = mutableListOf<AccessibilityNodeInfo>()
    fun collect(node: AccessibilityNodeInfo?) {
      if (node == null) return
      nodes.add(node)
      for (i in 0 until node.childCount) collect(node.getChild(i))
    }
    collect(root)

    val targets = nodes.filter { node ->
      val text = node.text?.toString()?.lowercase() ?: ""
      val desc = node.contentDescription?.toString()?.lowercase() ?: ""
      val id = node.viewIdResourceName ?: ""
      (Selectors.acceptTexts.any { text.contains(it) } ||
       Selectors.acceptDescs.any { desc.contains(it) } ||
       Selectors.acceptIds.any { id.endsWith(it) }) && node.isClickable
    }

    if (targets.isEmpty()) {
      Log.w("AutoAccept", "No accept control found. Possibly verification screen; handing off to user.")
      shouldAttemptAccept = false
      return
    }
    val ok = targets.first().performAction(AccessibilityNodeInfo.ACTION_CLICK)
    Log.i("AutoAccept", "Click result: $ok")
    shouldAttemptAccept = false

    if (!ok) {
      uiHandler.postDelayed({ tryTapCenter() }, 150)
    }
  }

  override fun onInterrupt() {}

  private fun tryTapCenter() {
    val display = display ?: return
    val dm = android.util.DisplayMetrics()
    @Suppress("DEPRECATION")
    display.getRealMetrics(dm)
    val cx = dm.widthPixels / 2f
    val cy = dm.heightPixels * 0.8f
    val path = Path().apply { moveTo(cx, cy) }
    val gesture = GestureDescription.Builder()
      .addStroke(GestureDescription.StrokeDescription(path, 0, 50))
      .build()
    dispatchGesture(gesture, null, null)
  }
}
