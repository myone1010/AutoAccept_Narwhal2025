
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
