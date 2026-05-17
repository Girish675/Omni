package com.girish.premiumapp.presentation.theme

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

enum class AppThemeMode {
    SYSTEM, DARK, LIGHT, AMOLED
}

// Global mutable theme state for instant recomposition
object ThemeState {
    var currentTheme by mutableStateOf(AppThemeMode.DARK)
}

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryAccent,
    secondary = SecondaryAccent,
    background = DarkBackground,
    surface = SurfaceDark,
    surfaceVariant = Color(0xFF2C2C2C),
    error = ErrorColor,
    onPrimary = DarkBackground,
    onSecondary = DarkBackground,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    primaryContainer = Color(0xFF3700B3),
    onPrimaryContainer = Color(0xFFE0E0E0)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6750A4),
    secondary = Color(0xFF625B71),
    background = LightBackground,
    surface = LightSurface,
    surfaceVariant = Color(0xFFE7E0EC),
    error = Color(0xFFB3261E),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    primaryContainer = Color(0xFFEADDFF),
    onPrimaryContainer = Color(0xFF21005D)
)

private val AmoledColorScheme = darkColorScheme(
    primary = PrimaryAccent,
    secondary = SecondaryAccent,
    background = AmoledBackground,
    surface = AmoledSurface,
    surfaceVariant = Color(0xFF151515),
    error = ErrorColor,
    onPrimary = AmoledBackground,
    onSecondary = AmoledBackground,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    primaryContainer = Color(0xFF2A0070),
    onPrimaryContainer = Color(0xFFE0E0E0)
)

@Composable
fun OmniAppTheme(
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val systemDark = isSystemInDarkTheme()

    // Load saved theme on first composition
    val savedTheme = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        .getString("theme_mode", "DARK") ?: "DARK"
    if (ThemeState.currentTheme.name != savedTheme) {
        ThemeState.currentTheme = try { AppThemeMode.valueOf(savedTheme) } catch (e: Exception) { AppThemeMode.DARK }
    }

    val colorScheme = when (ThemeState.currentTheme) {
        AppThemeMode.SYSTEM -> if (systemDark) DarkColorScheme else LightColorScheme
        AppThemeMode.DARK -> DarkColorScheme
        AppThemeMode.LIGHT -> LightColorScheme
        AppThemeMode.AMOLED -> AmoledColorScheme
    }

    val isActuallyDark = when (ThemeState.currentTheme) {
        AppThemeMode.SYSTEM -> systemDark
        AppThemeMode.DARK, AppThemeMode.AMOLED -> true
        AppThemeMode.LIGHT -> false
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !isActuallyDark
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
