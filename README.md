
# AutoAcceptTemplate — Narwhal 2025 profile
- Android Gradle Plugin: **8.6.1**
- Gradle Wrapper: **8.7**
- Kotlin: **2.0.20** (K2 enabled OK)
- Gradle JDK (runtime): **21** is fine; Android language level is set to **Java 17**

## Build steps
1) Open in Android Studio 2025.1.3 (Narwhal 3 Feature Drop).
2) Settings → Build, Execution, Deployment → Build Tools → **Gradle JDK = 21** (Studio default is OK).
3) File → Sync Project with Gradle Files.
4) Build → Make Project, then Build APK(s).

If you hit a Kotlin K2 warning, keep K2 on — this project uses Kotlin 2.0.20.

# How to run and use the APK you build from this project

## 1) First-time setup (permissions)

Because this app automates UI interaction, you must grant special permissions:

1. **Notification Access**

   * Open the app → press **“Open Notification Access”**.
   * Toggle **AutoAccept Template** → Allow.

2. **Accessibility Service**

   * Press **“Open Accessibility Settings”**.
   * Toggle **AutoAccept Accessibility** → Allow.
   * This is what allows the app to simulate button clicks.

3. (Optional) Location permissions if you plan to expand features.

---

## 2) Run the app

* Open the app.

* Tap **Start** → it runs a foreground service (you’ll see a persistent notification “AutoAccept running”).

* When a notification from the target app (e.g., Lalamove driver) appears, the app:

  * Listens to it via the notification service.
  * Triggers the accessibility service to try clicking “Accept”.

* Tap **Stop** in the app to end the service.

---

## 3) Debugging

* If it doesn’t click properly, open **Logcat** in Android Studio while the phone is connected.
* You’ll see logs from `AutoAcceptAccessibility` and `JobNotificationListener`.

---

⚠️ **Important note**: this project is just a template. It will try to click “accept” buttons, but you may need to adjust `RuleEngine.kt` and `Selectors.kt` to match the exact wording/IDs that uses in their app.
