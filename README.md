# NewsOrbit 📰

A production-ready Android news application built with Kotlin and Jetpack Compose that displays news articles from NewsAPI.org with offline support, pagination, and modern Material Design 3 UI.

## ✨ Features

- **Modern UI**: Built with Jetpack Compose and Material Design 3
- **Offline Support**: Articles are cached locally using Room database
- **Pagination**: Lazy loading for smooth scrolling through articles
- **Pull-to-Refresh**: Swipe down to fetch latest news
- **Error Handling**: Comprehensive error states with retry functionality
- **Dark Mode**: Automatic dark theme support
- **Clean Architecture**: MVVM pattern with clear separation of concerns

## 🏗️ Architecture

The app follows **MVVM (Model-View-ViewModel)** architecture with clean separation:

```
NewsOrbit/
├── data/                    # Data Layer
│   ├── local/              # Room database (offline cache)
│   ├── remote/             # Retrofit API (network calls)
│   └── repository/         # Single source of truth
├── domain/                  # Domain Layer
│   └── model/              # Business models
├── ui/                      # Presentation Layer
│   ├── screens/            # Compose screens & ViewModels
│   ├── components/         # Reusable UI components
│   ├── theme/              # Material 3 theming
│   └── navigation/         # Navigation graph
└── di/                      # Dependency Injection (Hilt)
```

## 🛠️ Tech Stack

| Category | Technology |
|----------|-----------|
| **Language** | Kotlin |
| **UI Framework** | Jetpack Compose |
| **Architecture** | MVVM |
| **Dependency Injection** | Hilt |
| **Networking** | Retrofit + OkHttp |
| **Local Database** | Room |
| **Image Loading** | Coil |
| **Async** | Coroutines + Flow |
| **Design** | Material Design 3 |

## 📋 Prerequisites

- **Android Studio**: Hedgehog (2023.1.1) or later
- **JDK**: 17 or higher
- **Android SDK**: API 24+ (Android 7.0+)
- **NewsAPI Key**: Free API key from [newsapi.org](https://newsapi.org/register)

## 🚀 Setup Instructions

### 1. Clone the Repository

```bash
git clone git remote add origin https://github.com/ManavDodiya/NewsOrbit.git
cd NewsOrbit
```

### 2. Get NewsAPI Key

1. Visit [https://newsapi.org/register](https://newsapi.org/register)
2. Sign up for a free account
3. Copy your API key from the dashboard

### 3. Configure API Key

Open `local.properties` file in the project root and add your API key:

```properties
NEWS_API_KEY=92d8a303cfea40218398454edaaf45fa
```

> **⚠️ Important**: Never commit `local.properties` to version control. It's already in `.gitignore`.

### 4. Build and Run

#### Using Android Studio:
1. Open the project in Android Studio
2. Wait for Gradle sync to complete
3. Click **Run** (▶️) or press `Shift + F10`

#### Using Command Line:
```bash
# Debug build
./gradlew assembleDebug

# Install on connected device
./gradlew installDebug
```

## 📱 How to Use

1. **Launch the app** - You'll see a list of top news headlines
2. **Pull down** to refresh articles
3. **Scroll down** to load more articles (pagination)
4. **Tap an article** to view full details
5. **Go offline** - Cached articles will still be available

## 🎨 UI States

The app handles all possible states:

- **Loading**: Shimmer skeleton UI while fetching data
- **Success**: Grid of article cards with images
- **Error**: Error message with retry button
- **Empty**: Friendly message when no articles available
- **Offline**: Shows cached articles when network unavailable

## 🔧 Configuration

### API Endpoint
The app uses NewsAPI's top headlines endpoint:
```
GET https://newsapi.org/v2/top-headlines
```

Default parameters:
- `country`: us
- `pageSize`: 20
- `page`: 1, 2, 3... (for pagination)

### Customization

To change the news source or category, edit `NewsApiService.kt`:

```kotlin
@GET("v2/top-headlines")
suspend fun getTopHeadlines(
    @Query("country") country: String = "us",  // Change country
    @Query("category") category: String? = null,  // Add category
    // ...
)
```

## 📦 Build Variants

- **Debug**: Development build with logging enabled
- **Release**: Production build with ProGuard/R8 optimization

```bash
# Build release APK
./gradlew assembleRelease
```

## 🐛 Troubleshooting

### API Key Issues
**Error**: "Invalid API key"
- Verify your API key in `local.properties`
- Ensure no extra spaces or quotes
- Rebuild the project (`Build > Clean Project` then `Build > Rebuild Project`)

### Build Errors
**Error**: "Unresolved reference: BuildConfig"
- Sync Gradle: `File > Sync Project with Gradle Files`
- Invalidate caches: `File > Invalidate Caches > Invalidate and Restart`

### Network Errors
**Error**: "Unable to resolve host"
- Check internet connection
- Verify firewall/proxy settings
- Ensure `INTERNET` permission in AndroidManifest.xml

## 📄 License

This project is for educational and demonstration purposes.

## 🙏 Acknowledgments

- News data provided by [NewsAPI.org](https://newsapi.org)
- Icons from Material Design Icons
- Built with ❤️ using Jetpack Compose

---

**Note**: This app uses the free tier of NewsAPI (100 requests/day). For production use, consider upgrading to a paid plan.

![home screen](https://github.com/user-attachments/assets/ca735ef1-822e-421b-ad62-979bdb97549e)
![detail screen](https://github.com/user-attachments/assets/33dae62d-8da0-4b38-9927-5dbc6dc2c456)
