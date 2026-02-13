package com.newsorbit

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class for NewsOrbit.
 * Annotated with @HiltAndroidApp to enable Hilt dependency injection.
 */
@HiltAndroidApp
class NewsOrbitApplication : Application()
