package com.girish.premiumapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.girish.premiumapp.presentation.dashboard.DashboardScreen
import com.girish.premiumapp.presentation.dashboard.ManageAccountScreen
import com.girish.premiumapp.presentation.dashboard.PrivacyPolicyScreen
import com.girish.premiumapp.presentation.expenses.ExpensesScreen
import com.girish.premiumapp.presentation.tasks.TasksScreen
import com.girish.premiumapp.presentation.notes.NotesScreen
import com.girish.premiumapp.presentation.calendar.CalendarScreen
import com.girish.premiumapp.presentation.dashboard.ProfileScreen
import com.girish.premiumapp.presentation.search.SearchScreen
import com.girish.premiumapp.presentation.onboarding.OnboardingScreen
import com.girish.premiumapp.presentation.timer.PomodoroTimerScreen

@Composable
fun AppNavigation(startDestination: String = Screen.Dashboard.route) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onNavigateToExpenses = { navController.navigate(Screen.Expenses.route) },
                onNavigateToTasks = { navController.navigate(Screen.Tasks.route) },
                onNavigateToNotes = { navController.navigate(Screen.Notes.route) },
                onNavigateToCalendar = { navController.navigate(Screen.Calendar.route) },
                onNavigateToProfile = { navController.navigate(Screen.Profile.route) },
                onNavigateToSearch = { navController.navigate(Screen.Search.route) },
                onNavigateToTimer = { navController.navigate(Screen.Timer.route) }
            )
        }
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onFinish = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Timer.route) { PomodoroTimerScreen(onBack = { navController.popBackStack() }) }
        composable(Screen.Profile.route) {
            ProfileScreen(
                onBack = { navController.popBackStack() },
                onNavigateToManageAccount = { navController.navigate(Screen.ManageAccount.route) },
                onNavigateToPrivacyPolicy = { navController.navigate(Screen.PrivacyPolicy.route) }
            )
        }
        composable(Screen.ManageAccount.route) {
            ManageAccountScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.PrivacyPolicy.route) {
            PrivacyPolicyScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.Search.route) {
            SearchScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.Expenses.route) { ExpensesScreen(onBack = { navController.popBackStack() }) }
        composable(Screen.Tasks.route) { TasksScreen(onBack = { navController.popBackStack() }) }
        composable(Screen.Notes.route) { NotesScreen(onBack = { navController.popBackStack() }) }
        composable(Screen.Calendar.route) { CalendarScreen(onBack = { navController.popBackStack() }) }
    }
}
