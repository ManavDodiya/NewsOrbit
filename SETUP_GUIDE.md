# Quick Setup Guide

## Issue Found

Your app was stuck in loading state due to two issues:

1. ✅ **FIXED**: Bug in `NewsRepository.kt` that caused the app to hang
2. ⚠️ **ACTION NEEDED**: Missing API key in `local.properties`

## Steps to Fix

### 1. Get Your NewsAPI Key

1. Visit: https://newsapi.org/register
2. Sign up for a free account (takes 1 minute)
3. Copy your API key from the dashboard

### 2. Add API Key to Project

Open this file: `NewsOrbit/local.properties`

Replace this line:
```
NEWS_API_KEY=your_api_key_here
```

With your actual API key:
```
NEWS_API_KEY=abc123your_actual_key_here
```

### 3. Rebuild the App

In Android Studio:
1. Click **Build** → **Clean Project**
2. Click **Build** → **Rebuild Project**
3. Click **Run** (▶️) to launch the app

OR from command line:
```bash
cd NewsOrbit
./gradlew clean
./gradlew build
./gradlew installDebug
```

## What Was Fixed

The bug in `NewsRepository.getCachedArticles()` was trying to collect from a Flow synchronously, which would never complete. This prevented the app from showing error messages when the API call failed.

**Before (buggy):**
```kotlin
val entities = articleDao.getAllArticles()
entities.collect { ... } // This hangs forever!
```

**After (fixed):**
```kotlin
articleDao.getAllArticles()
    .map { ... }
    .first() // Gets first emission and completes
```

Now the app will properly show error messages when:
- API key is missing/invalid
- Network is unavailable
- API rate limit is exceeded

## Expected Behavior After Fix

✅ **With valid API key**: Articles will load and display
✅ **With invalid API key**: Error message: "Invalid API key. Please check your configuration."
✅ **Without internet**: Error message: "Network error. Please check your connection."
✅ **After viewing once**: Cached articles will show when offline

## Need Help?

If you still see issues after adding your API key and rebuilding:
1. Check Android Studio's Logcat for error messages
2. Verify your API key is correct (no extra spaces)
3. Ensure you have internet connection
4. Try running: `Build → Invalidate Caches → Invalidate and Restart`
