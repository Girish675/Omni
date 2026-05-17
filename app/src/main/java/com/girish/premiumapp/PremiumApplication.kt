package com.girish.premiumapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PremiumApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize any necessary libraries here
    }
}
