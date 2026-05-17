package com.girish.premiumapp.presentation.dashboard

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.NoteAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.girish.premiumapp.presentation.theme.CardGradientEnd
import com.girish.premiumapp.presentation.theme.CardGradientStart
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToExpenses: () -> Unit,
    onNavigateToTasks: () -> Unit,
    onNavigateToNotes: () -> Unit,
    onNavigateToCalendar: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToSearch: () -> Unit = {},
    onNavigateToTimer: () -> Unit = {},
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val stats by viewModel.stats.collectAsState()
    val context = LocalContext.current

    // Reload stats when screen comes into focus
    LaunchedEffect(Unit) {
        viewModel.loadStats()
    }

    // Dynamic greeting based on time of day
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val greeting = when {
        hour < 12 -> "Good morning"
        hour < 17 -> "Good afternoon"
        else -> "Good evening"
    }
    val displayName = stats.userName.ifBlank {
        context.getSharedPreferences("account_prefs", Context.MODE_PRIVATE)
            .getString("display_name", "User") ?: "User"
    }.ifBlank { "User" }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text("$greeting,", style = MaterialTheme.typography.bodyLarge)
                        Text(displayName, style = MaterialTheme.typography.headlineLarge)
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateToSearch) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    IconButton(onClick = onNavigateToProfile) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Profile",
                            modifier = Modifier.size(32.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            item(span = { GridItemSpan(2) }) {
                ProductivitySnapshot(
                    pendingTasks = stats.pendingTasks,
                    totalTasks = stats.totalTasks,
                    todayEvents = stats.todayEvents,
                    monthlySpent = stats.monthlySpent
                )
            }
            item {
                DashboardCard(
                    title = "Expenses",
                    subtitle = if (stats.monthlySpent > 0) "Rs. ${String.format("%.0f", stats.monthlySpent)} this month" else "Track spending",
                    icon = Icons.Default.AttachMoney,
                    onClick = onNavigateToExpenses
                )
            }
            item {
                DashboardCard(
                    title = "Tasks",
                    subtitle = if (stats.totalTasks > 0) "${stats.totalTasks - stats.pendingTasks}/${stats.totalTasks} done" else "Manage to-dos",
                    icon = Icons.Default.CheckCircle,
                    onClick = onNavigateToTasks
                )
            }
            item {
                DashboardCard(
                    title = "Notes",
                    subtitle = if (stats.noteCount > 0) "${stats.noteCount} notes" else "Capture ideas",
                    icon = Icons.Default.NoteAlt,
                    onClick = onNavigateToNotes
                )
            }
            item {
                DashboardCard(
                    title = "Calendar",
                    subtitle = if (stats.todayEvents > 0) "${stats.todayEvents} events today" else "Schedule events",
                    icon = Icons.Default.CalendarToday,
                    onClick = onNavigateToCalendar
                )
            }
            item {
                DashboardCard(
                    title = "Timer",
                    subtitle = "Pomodoro & focus",
                    icon = Icons.Default.PlayArrow,
                    onClick = onNavigateToTimer
                )
            }
        }
    }
}

@Composable
fun ProductivitySnapshot(
    pendingTasks: Int,
    totalTasks: Int,
    todayEvents: Int,
    monthlySpent: Double
) {
    val completed = (totalTasks - pendingTasks).coerceAtLeast(0)
    val progress = if (totalTasks > 0) completed.toFloat() / totalTasks else 0f

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text("Today", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text("$pendingTasks open tasks - $todayEvents events", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Text("Rs. ${String.format("%.0f", monthlySpent)}", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
            }
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                if (totalTasks > 0) "$completed of $totalTasks tasks complete" else "Add tasks to start tracking progress",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun DashboardCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(CardGradientStart, CardGradientEnd)
                )
            )
            .clickable { onClick() }
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(36.dp)
            )
            
            Column {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = subtitle,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
