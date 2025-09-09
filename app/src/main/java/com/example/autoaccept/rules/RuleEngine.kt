
package com.example.autoaccept.rules

object RuleEngine {
  // Your current setting: accept ALL
  fun shouldAccept(packageName: String, title: String, text: String): Boolean {
    // Optionally narrow to the driver package:
    // return packageName == "com.lalamove.global.driver.sea"
    return true
  }
}
