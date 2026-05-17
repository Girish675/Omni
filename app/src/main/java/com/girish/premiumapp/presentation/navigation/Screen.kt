package com.girish.premiumapp.presentation.navigation

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object Expenses : Screen("expenses")
    object Tasks : Screen("tasks")
    object Notes : Screen("notes")
    object Calendar : Screen("calendar")
    object Profile : Screen("profile")
    object ManageAccount : Screen("manage_account")
    object PrivacyPolicy : Screen("privacy_policy")
    object Search : Screen("search")
    object Onboarding : Screen("onboarding")
    object Timer : Screen("timer")
}
