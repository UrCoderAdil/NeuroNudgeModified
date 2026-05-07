# NeuroNudge

**Your mental wellness companion** — a modern Android app for meditation, health tracking, and AI-powered mental wellness support.

---

## Features

- **Splash Screen** — branded video intro with Firebase Auth auto-login
- **Authentication** — Sign up, Login, Forgot Password, Change Password (Firebase Auth)
- **Home (AI Chat)** — Chat interface for mental wellness guidance
- **Meditation Hub** — Nasheeds, soothing music, and meditation videos in horizontal carousels
- **Health Profile** — Store body condition, blood pressure, sugar level, diseases, and medicines (Firestore)
- **Profile** — View name/email, log activities & medications, change password

---

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java |
| Min SDK | 24 (Android 7.0) |
| Target SDK | 35 |
| UI | Material3 (Dark theme) |
| Auth | Firebase Authentication |
| Database | Firebase Firestore |
| Navigation | Android Navigation Component |
| Animation | Lottie |
| Media | VideoView, MediaPlayer |

---

## Setup

### 1. Clone the repository
```bash
git clone https://github.com/UrCoderAdil/NeuroNudgeModified.git
cd NeuroNudgeModified
```

### 2. Add Firebase configuration
- Go to [Firebase Console](https://console.firebase.google.com/)
- Create (or open) your project
- Add an **Android app** with package name `com.example.neuronudge`
- Download `google-services.json` and place it in `app/`
- Enable **Authentication** (Email/Password) and **Firestore**

### 3. Build and run
```bash
./gradlew assembleDebug
# or open in Android Studio and click Run
```

---

## Deployment

### Option A — Firebase App Distribution (Recommended for testing)

1. Install Firebase CLI:
   ```bash
   npm install -g firebase-tools
   firebase login
   ```

2. Build a release APK:
   ```bash
   ./gradlew assembleRelease
   ```

3. Upload to Firebase App Distribution:
   ```bash
   firebase appdistribution:distribute app/build/outputs/apk/release/app-release.apk \
     --app <YOUR_FIREBASE_APP_ID> \
     --groups "testers" \
     --release-notes "New release"
   ```

### Option B — Google Play Store

1. **Generate a signing keystore** (one-time):
   ```bash
   keytool -genkey -v -keystore neuronudge.jks \
     -alias neuronudge -keyalg RSA -keysize 2048 -validity 10000
   ```

2. **Configure signing** in `app/build.gradle.kts`:
   ```kotlin
   android {
       signingConfigs {
           create("release") {
               storeFile = file("neuronudge.jks")
               storePassword = System.getenv("KEYSTORE_PASSWORD")
               keyAlias = "neuronudge"
               keyPassword = System.getenv("KEY_PASSWORD")
           }
       }
       buildTypes {
           release {
               signingConfig = signingConfigs.getByName("release")
               isMinifyEnabled = true
               proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
           }
       }
   }
   ```

3. **Build release AAB** (required by Play Store):
   ```bash
   ./gradlew bundleRelease
   # Output: app/build/outputs/bundle/release/app-release.aab
   ```

4. Upload `app-release.aab` via [Google Play Console](https://play.google.com/console)

### Option C — GitHub Actions CI/CD (Automated)

The project includes `.github/workflows/android.yml`. Add these **repository secrets**:

| Secret | Description |
|--------|-------------|
| `FIREBASE_APP_ID` | Your Firebase App ID (from project settings) |
| `CREDENTIAL_FILE_CONTENT` | Firebase service account JSON content |
| `KEYSTORE_FILE` | Base64-encoded keystore file |
| `KEYSTORE_PASSWORD` | Keystore password |
| `KEY_ALIAS` | Key alias |
| `KEY_PASSWORD` | Key password |

Every push to `master`/`main` will automatically build and distribute to your Firebase testers group.

---

## Project Structure

```
app/src/main/
├── java/com/example/neuronudge/
│   ├── SplashActivity.java
│   ├── LoginActivity.java
│   ├── SignUpActivity.java
│   ├── ForgotPasswordActivity.java
│   ├── ChangePasswordActivity.java
│   ├── HomeActivity.java
│   ├── Adapters/
│   │   ├── AudioAdapter.java
│   │   └── VideoAdapter.java
│   ├── Fragments/
│   │   ├── HomeFragment.java
│   │   ├── MeditationFragment.java
│   │   ├── SearchFragment.java
│   │   └── ProfileFragment.java
│   └── models/
│       ├── AudioTrack.java
│       └── VideoTrack.java
└── res/
    ├── layout/       # All XML layouts
    ├── values/       # colors, strings, themes
    ├── drawable/     # Icons, backgrounds, images
    ├── color/        # Color state lists (selectors)
    ├── navigation/   # Nav graph
    ├── menu/         # Bottom nav menu
    └── raw/          # Audio/video media files
```

---

## License

MIT License — see [LICENSE](LICENSE) for details.
