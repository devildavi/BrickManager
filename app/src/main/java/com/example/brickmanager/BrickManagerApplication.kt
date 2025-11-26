package com.example.brickmanager

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * The main Application class for the BrickManager app.
 * The [@HiltAndroidApp] annotation triggers Hilt's code generation and allows
 * dependency injection to be used throughout the application.
 */
@HiltAndroidApp
class BrickManagerApplication : Application()
