package com.girish.premiumapp.presentation.dashboard

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.fragment.app.FragmentActivity
import com.girish.premiumapp.presentation.security.BiometricPromptManager
import com.girish.premiumapp.presentation.theme.AppThemeMode
import com.girish.premiumapp.presentation.theme.ThemeState
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onBack: () -> Unit,
    onNavigateToManageAccount: () -> Unit = {},
    onNavigateToPrivacyPolicy: () -> Unit = {}
) {
    val context = LocalContext.current
    val sharedPrefs = remember { context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE) }

    var biometricEnabled by remember { mutableStateOf(sharedPrefs.getBoolean("biometric_enabled", false)) }
    var notificationsEnabled by remember { mutableStateOf(sharedPrefs.getBoolean("notifications_enabled", true)) }
    var showThemeDialog by remember { mutableStateOf(false) }
    var showExportDialog by remember { mutableStateOf(false) }
    var showFeedbackDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile & Settings") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Account Section
            Text("Account", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                Column {
                    ListItem(
                        headlineContent = { Text("Manage Account") },
                        leadingContent = { Icon(Icons.Default.AccountCircle, contentDescription = null) },
                        trailingContent = { Icon(Icons.Default.ChevronRight, contentDescription = null) },
                        modifier = Modifier.clickable { onNavigateToManageAccount() }
                    )
                    HorizontalDivider()
                    ListItem(
                        headlineContent = { Text("Privacy Policy") },
                        leadingContent = { Icon(Icons.Default.Security, contentDescription = null) },
                        trailingContent = { Icon(Icons.Default.ChevronRight, contentDescription = null) },
                        modifier = Modifier.clickable { onNavigateToPrivacyPolicy() }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Appearance Section
            Text("Appearance", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                ListItem(
                    headlineContent = { Text("Theme") },
                    supportingContent = { Text(ThemeState.currentTheme.name.lowercase().replaceFirstChar { it.uppercase() }) },
                    leadingContent = { Icon(Icons.Default.Palette, contentDescription = null) },
                    trailingContent = { Icon(Icons.Default.ChevronRight, contentDescription = null) },
                    modifier = Modifier.clickable { showThemeDialog = true }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Security Section
            Text("Security", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                Column {
                    ListItem(
                        headlineContent = { Text("Biometric Lock") },
                        supportingContent = { Text("Secure app with fingerprint") },
                        leadingContent = { Icon(Icons.Default.Lock, contentDescription = null) },
                        trailingContent = {
                            Switch(
                                checked = biometricEnabled,
                                onCheckedChange = { isEnabled ->
                                    if (isEnabled) {
                                        val activity = context as? FragmentActivity
                                        if (activity != null) {
                                            val biometricManager = BiometricPromptManager(activity)
                                            biometricManager.showBiometricPrompt(
                                                title = "Enable Biometric",
                                                subtitle = "Authenticate to enable",
                                                onSuccess = {
                                                    biometricEnabled = true
                                                    sharedPrefs.edit().putBoolean("biometric_enabled", true).apply()
                                                },
                                                onError = { error ->
                                                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                                                }
                                            )
                                        }
                                    } else {
                                        biometricEnabled = false
                                        sharedPrefs.edit().putBoolean("biometric_enabled", false).apply()
                                    }
                                }
                            )
                        }
                    )
                    HorizontalDivider()
                    ListItem(
                        headlineContent = { Text("Notifications") },
                        supportingContent = { Text("Daily reminders & alerts") },
                        leadingContent = { Icon(Icons.Default.Notifications, contentDescription = null) },
                        trailingContent = {
                            Switch(
                                checked = notificationsEnabled,
                                onCheckedChange = {
                                    notificationsEnabled = it
                                    sharedPrefs.edit().putBoolean("notifications_enabled", it).apply()
                                    Toast.makeText(context, if (it) "Notifications enabled" else "Notifications disabled", Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Data Section
            Text("Data", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                Column {
                    ListItem(
                        headlineContent = { Text("Export Data") },
                        supportingContent = { Text("Export as CSV or JSON") },
                        leadingContent = { Icon(Icons.Default.FileDownload, contentDescription = null) },
                        trailingContent = { Icon(Icons.Default.ChevronRight, contentDescription = null) },
                        modifier = Modifier.clickable { showExportDialog = true }
                    )
                    HorizontalDivider()
                    ListItem(
                        headlineContent = { Text("Cloud Sync") },
                        supportingContent = { Text("Coming soon") },
                        leadingContent = { Icon(Icons.Default.Sync, contentDescription = null) },
                        trailingContent = { Icon(Icons.Default.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text("Feedback", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                ListItem(
                    headlineContent = { Text("Send Feedback") },
                    supportingContent = { Text("Report a bug or suggest a feature") },
                    leadingContent = { Icon(Icons.Default.Feedback, contentDescription = null) },
                    trailingContent = { Icon(Icons.Default.ChevronRight, contentDescription = null) },
                    modifier = Modifier.clickable { showFeedbackDialog = true }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // About
            Text("About", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                ListItem(
                    headlineContent = { Text("Omni Productivity") },
                    supportingContent = { Text("Version 1.0.0") },
                    leadingContent = { Icon(Icons.Default.Info, contentDescription = null) }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }

    // Theme Picker Dialog
    if (showThemeDialog) {
        AlertDialog(
            onDismissRequest = { showThemeDialog = false },
            title = { Text("Choose Theme") },
            text = {
                Column {
                    AppThemeMode.entries.forEach { mode ->
                        Row(
                            modifier = Modifier.fillMaxWidth().clickable {
                                ThemeState.currentTheme = mode
                                sharedPrefs.edit().putString("theme_mode", mode.name).apply()
                                showThemeDialog = false
                            }.padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(selected = ThemeState.currentTheme == mode, onClick = {
                                ThemeState.currentTheme = mode
                                sharedPrefs.edit().putString("theme_mode", mode.name).apply()
                                showThemeDialog = false
                            })
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(mode.name.lowercase().replaceFirstChar { it.uppercase() }, style = MaterialTheme.typography.titleMedium)
                                Text(
                                    when (mode) {
                                        AppThemeMode.SYSTEM -> "Follow system dark/light setting"
                                        AppThemeMode.DARK -> "Default dark theme"
                                        AppThemeMode.LIGHT -> "Clean light theme"
                                        AppThemeMode.AMOLED -> "Pure black for OLED screens"
                                    },
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = { TextButton(onClick = { showThemeDialog = false }) { Text("Close") } }
        )
    }

    // Export Dialog
    if (showExportDialog) {
        AlertDialog(
            onDismissRequest = { showExportDialog = false },
            title = { Text("Export Data") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Choose what to export:", style = MaterialTheme.typography.bodyMedium)

                    OutlinedButton(
                        onClick = {
                            exportData(context, "expenses")
                            showExportDialog = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) { Icon(Icons.Default.AttachMoney, contentDescription = null); Spacer(modifier = Modifier.width(8.dp)); Text("Export Expenses (CSV)") }

                    OutlinedButton(
                        onClick = {
                            exportData(context, "notes")
                            showExportDialog = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) { Icon(Icons.Default.NoteAlt, contentDescription = null); Spacer(modifier = Modifier.width(8.dp)); Text("Export Notes (Text)") }

                    OutlinedButton(
                        onClick = {
                            exportData(context, "tasks")
                            showExportDialog = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) { Icon(Icons.Default.CheckCircle, contentDescription = null); Spacer(modifier = Modifier.width(8.dp)); Text("Export Tasks (CSV)") }

                    OutlinedButton(
                        onClick = {
                            exportData(context, "all")
                            showExportDialog = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) { Icon(Icons.Default.BackupTable, contentDescription = null); Spacer(modifier = Modifier.width(8.dp)); Text("Export All (JSON)") }
                }
            },
            confirmButton = { TextButton(onClick = { showExportDialog = false }) { Text("Cancel") } }
        )
    }

    if (showFeedbackDialog) {
        var feedback by remember { mutableStateOf("") }
        var includeDiagnostics by remember { mutableStateOf(true) }
        AlertDialog(
            onDismissRequest = { showFeedbackDialog = false },
            title = { Text("Send Feedback") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = feedback,
                        onValueChange = { feedback = it },
                        label = { Text("What should improve?") },
                        minLines = 4,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = includeDiagnostics, onCheckedChange = { includeDiagnostics = it })
                        Text("Include anonymous diagnostics")
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val target = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_SUBJECT, "Omni Feedback")
                            putExtra(
                                Intent.EXTRA_TEXT,
                                buildString {
                                    appendLine(feedback)
                                    if (includeDiagnostics) {
                                        appendLine()
                                        appendLine("Theme: ${ThemeState.currentTheme}")
                                        appendLine("Biometric enabled: $biometricEnabled")
                                        appendLine("Notifications enabled: $notificationsEnabled")
                                    }
                                }
                            )
                        }
                        context.startActivity(Intent.createChooser(target, "Send feedback"))
                        showFeedbackDialog = false
                    },
                    enabled = feedback.isNotBlank()
                ) { Text("Send") }
            },
            dismissButton = { TextButton(onClick = { showFeedbackDialog = false }) { Text("Cancel") } }
        )
    }
}

private fun exportData(context: Context, type: String) {
    try {
        val fileName = "omni_export_${type}_${System.currentTimeMillis()}"
        val extension = if (type == "all" || type == "notes") ".txt" else ".csv"
        val file = File(context.cacheDir, "$fileName$extension")

        val content = when (type) {
            "expenses" -> {
                val db = androidx.room.Room.databaseBuilder(context, com.girish.premiumapp.data.local.AppDatabase::class.java, "premium_app_db").allowMainThreadQueries().build()
                val expenses = db.expenseDao.getAllExpensesList()
                val sb = StringBuilder("Category,Amount,Payment Method,Note,Date\n")
                expenses.forEach { e ->
                    sb.appendLine("${e.category},${e.amount},${e.paymentMethod},\"${e.note}\",${java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date(e.date))}")
                }
                db.close()
                sb.toString()
            }
            "tasks" -> {
                val db = androidx.room.Room.databaseBuilder(context, com.girish.premiumapp.data.local.AppDatabase::class.java, "premium_app_db").allowMainThreadQueries().build()
                val tasks = db.taskDao.getAllTasksList()
                val sb = StringBuilder("Title,Description,Priority,Completed,Due Date\n")
                tasks.forEach { t ->
                    sb.appendLine("\"${t.title}\",\"${t.description}\",${t.priority},${t.isCompleted},${t.dueDate?.let { java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date(it)) } ?: "None"}")
                }
                db.close()
                sb.toString()
            }
            "notes" -> {
                val db = androidx.room.Room.databaseBuilder(context, com.girish.premiumapp.data.local.AppDatabase::class.java, "premium_app_db").allowMainThreadQueries().build()
                val notes = db.noteDao.getAllNotesList()
                val sb = StringBuilder()
                notes.forEach { n ->
                    sb.appendLine("=== ${n.title} ===")
                    sb.appendLine(n.content)
                    sb.appendLine("Tags: ${n.tags}")
                    sb.appendLine("Date: ${java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date(n.lastModified))}")
                    sb.appendLine()
                }
                db.close()
                sb.toString()
            }
            else -> {
                // All data as JSON-like text
                val db = androidx.room.Room.databaseBuilder(context, com.girish.premiumapp.data.local.AppDatabase::class.java, "premium_app_db").allowMainThreadQueries().build()
                val sb = StringBuilder()
                sb.appendLine("=== OMNI DATA EXPORT ===")
                sb.appendLine("\n--- EXPENSES ---")
                db.expenseDao.getAllExpensesList().forEach { sb.appendLine("${it.category}: Rs. ${it.amount} (${it.paymentMethod}) - ${it.note}") }
                sb.appendLine("\n--- TASKS ---")
                db.taskDao.getAllTasksList().forEach { sb.appendLine("[${if (it.isCompleted) "x" else " "}] ${it.title} (${it.priority})") }
                sb.appendLine("\n--- NOTES ---")
                db.noteDao.getAllNotesList().forEach { sb.appendLine("${it.title}: ${it.content.take(100)}...") }
                sb.appendLine("\n--- EVENTS ---")
                db.eventDao.getAllEventsList().forEach { sb.appendLine("${it.title} - ${java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault()).format(java.util.Date(it.startTime))} ${if (it.recurrenceRule.name != "NONE") "(${it.recurrenceRule})" else ""}") }
                db.close()
                sb.toString()
            }
        }

        file.writeText(content)

        val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            this.type = "text/*"
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_SUBJECT, "Omni Export - ${type.replaceFirstChar { it.uppercase() }}")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(shareIntent, "Export $type"))
    } catch (e: Exception) {
        Toast.makeText(context, "Export failed: ${e.message}", Toast.LENGTH_LONG).show()
    }
}
