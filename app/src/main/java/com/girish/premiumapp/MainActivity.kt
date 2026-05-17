package com.girish.premiumapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.girish.premiumapp.presentation.navigation.AppNavigation
import com.girish.premiumapp.presentation.theme.OmniAppTheme
import dagger.hilt.android.AndroidEntryPoint

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.runtime.LaunchedEffect
import com.girish.premiumapp.presentation.security.BiometricPromptManager

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        
        val sharedPrefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val isBiometricEnabled = sharedPrefs.getBoolean("biometric_enabled", false)
        
        // Removed splashScreen.setKeepOnScreenCondition to allow activity to fully resume
        // and safely show the BiometricPrompt dialog without crashing.
        
        setContent {
            OmniAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var authState by remember { mutableStateOf(!isBiometricEnabled) }
                    
                    if (!authState) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Unlock to continue...")
                        }
                        
                        LaunchedEffect(Unit) {
                            // Show biometric prompt
                            val biometricPromptManager = BiometricPromptManager(this@MainActivity)
                            biometricPromptManager.showBiometricPrompt(
                                title = "Unlock App",
                                subtitle = "Please authenticate to access your data",
                                onSuccess = {
                                    authState = true
                                },
                                onError = { error ->
                                    // Handle error or allow fallback
                                }
                            )
                        }
                    } else {
                        val hasSeenOnboarding = sharedPrefs.getBoolean("has_seen_onboarding", false)
                        val startDest = if (hasSeenOnboarding) com.girish.premiumapp.presentation.navigation.Screen.Dashboard.route else com.girish.premiumapp.presentation.navigation.Screen.Onboarding.route
                        AppNavigation(startDestination = startDest)
                    }
                }
            }
        }
    }
}
