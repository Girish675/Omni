package com.girish.premiumapp.presentation.dashboard

import android.content.Context
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageAccountScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val sharedPrefs = remember { context.getSharedPreferences("account_prefs", Context.MODE_PRIVATE) }

    // Account fields persisted in SharedPreferences
    var displayName by remember { mutableStateOf(sharedPrefs.getString("display_name", "") ?: "") }
    var email by remember { mutableStateOf(sharedPrefs.getString("email", "") ?: "") }
    var phone by remember { mutableStateOf(sharedPrefs.getString("phone", "") ?: "") }

    // Password change
    var showPasswordDialog by remember { mutableStateOf(false) }

    // Delete account confirmation
    var showDeleteDialog by remember { mutableStateOf(false) }

    // Subscription
    var subscriptionPlan by remember { mutableStateOf(sharedPrefs.getString("subscription_plan", "Free") ?: "Free") }
    var showSubscriptionSheet by remember { mutableStateOf(false) }

    // Track if changes have been made
    val originalName = remember { sharedPrefs.getString("display_name", "") ?: "" }
    val originalEmail = remember { sharedPrefs.getString("email", "") ?: "" }
    val originalPhone = remember { sharedPrefs.getString("phone", "") ?: "" }
    val hasChanges = displayName != originalName || email != originalEmail || phone != originalPhone

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Manage Account") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Profile Info Section
            Text(
                "Profile Information",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = displayName,
                        onValueChange = { displayName = it },
                        label = { Text("Display Name") },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email Address") },
                        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("Phone Number") },
                        leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // Save button
            Button(
                onClick = {
                    sharedPrefs.edit()
                        .putString("display_name", displayName)
                        .putString("email", email)
                        .putString("phone", phone)
                        .apply()
                    Toast.makeText(context, "Profile updated successfully!", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = hasChanges
            ) {
                Icon(Icons.Default.Check, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Save Changes")
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

            // Security Section
            Text(
                "Security",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(12.dp)
            ) {
                ListItem(
                    headlineContent = { Text("Change Password") },
                    supportingContent = { Text("Update your account password") },
                    leadingContent = { Icon(Icons.Default.Lock, contentDescription = null) },
                    trailingContent = {
                        Icon(Icons.Default.ChevronRight, contentDescription = null)
                    },
                    modifier = Modifier.clickable { showPasswordDialog = true }
                )
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

            // Subscription Section
            Text(
                "Subscription",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                "Current Plan",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                subscriptionPlan,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = if (subscriptionPlan == "Premium") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                            )
                        }
                        if (subscriptionPlan == "Premium") {
                            AssistChip(
                                onClick = {},
                                label = { Text("Active") },
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedButton(
                        onClick = { showSubscriptionSheet = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(if (subscriptionPlan == "Free") "Upgrade to Premium" else "Manage Subscription")
                    }
                }
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

            // Danger Zone
            Text(
                "Danger Zone",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.error
            )

            OutlinedButton(
                onClick = { showDeleteDialog = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Icon(Icons.Default.DeleteForever, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Delete Account")
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }

    // Password Change Dialog
    if (showPasswordDialog) {
        ChangePasswordDialog(
            onDismiss = { showPasswordDialog = false },
            onConfirm = { currentPwd, newPwd ->
                val savedPwd = sharedPrefs.getString("password", "") ?: ""
                if (savedPwd.isEmpty() || currentPwd == savedPwd) {
                    sharedPrefs.edit().putString("password", newPwd).apply()
                    Toast.makeText(context, "Password updated successfully!", Toast.LENGTH_SHORT).show()
                    showPasswordDialog = false
                } else {
                    Toast.makeText(context, "Current password is incorrect!", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    // Delete Account Dialog
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            icon = { Icon(Icons.Default.Warning, contentDescription = null, tint = MaterialTheme.colorScheme.error) },
            title = { Text("Delete Account?") },
            text = {
                Text("This will permanently delete all your data including expenses, notes, tasks, and events. This action cannot be undone.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        // Clear all shared preferences data
                        sharedPrefs.edit().clear().apply()
                        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE).edit().clear().apply()
                        Toast.makeText(context, "Account data cleared!", Toast.LENGTH_SHORT).show()
                        showDeleteDialog = false
                        onBack()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Delete Everything")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Subscription Bottom Sheet
    if (showSubscriptionSheet) {
        SubscriptionBottomSheet(
            currentPlan = subscriptionPlan,
            onDismiss = { showSubscriptionSheet = false },
            onSelectPlan = { plan ->
                sharedPrefs.edit().putString("subscription_plan", plan).apply()
                subscriptionPlan = plan
                Toast.makeText(context, "Subscription updated to $plan!", Toast.LENGTH_SHORT).show()
                showSubscriptionSheet = false
            }
        )
    }
}

@Composable
fun ChangePasswordDialog(
    onDismiss: () -> Unit,
    onConfirm: (currentPassword: String, newPassword: String) -> Unit
) {
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showCurrentPwd by remember { mutableStateOf(false) }
    var showNewPwd by remember { mutableStateOf(false) }

    val passwordsMatch = newPassword == confirmPassword && newPassword.isNotEmpty()
    val isValid = newPassword.length >= 6 && passwordsMatch

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Change Password") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = currentPassword,
                    onValueChange = { currentPassword = it },
                    label = { Text("Current Password") },
                    singleLine = true,
                    visualTransformation = if (showCurrentPwd) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { showCurrentPwd = !showCurrentPwd }) {
                            Icon(
                                if (showCurrentPwd) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = "Toggle visibility"
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    label = { Text("New Password") },
                    singleLine = true,
                    visualTransformation = if (showNewPwd) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { showNewPwd = !showNewPwd }) {
                            Icon(
                                if (showNewPwd) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = "Toggle visibility"
                            )
                        }
                    },
                    supportingText = {
                        if (newPassword.isNotEmpty() && newPassword.length < 6) {
                            Text("Must be at least 6 characters", color = MaterialTheme.colorScheme.error)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirm New Password") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    isError = confirmPassword.isNotEmpty() && !passwordsMatch,
                    supportingText = {
                        if (confirmPassword.isNotEmpty() && !passwordsMatch) {
                            Text("Passwords do not match", color = MaterialTheme.colorScheme.error)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(currentPassword, newPassword) },
                enabled = isValid
            ) {
                Text("Update Password")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscriptionBottomSheet(
    currentPlan: String,
    onDismiss: () -> Unit,
    onSelectPlan: (String) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Choose Your Plan",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            // Free Plan
            SubscriptionPlanCard(
                planName = "Free",
                price = "₹0",
                features = listOf("Basic expense tracking", "Up to 50 notes", "Standard themes"),
                isSelected = currentPlan == "Free",
                onSelect = { onSelectPlan("Free") }
            )

            // Premium Plan
            SubscriptionPlanCard(
                planName = "Premium",
                price = "₹199/mo",
                features = listOf("Unlimited expense tracking", "Unlimited notes", "Cloud sync", "Priority support", "All premium themes"),
                isSelected = currentPlan == "Premium",
                isPremium = true,
                onSelect = { onSelectPlan("Premium") }
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun SubscriptionPlanCard(
    planName: String,
    price: String,
    features: List<String>,
    isSelected: Boolean,
    isPremium: Boolean = false,
    onSelect: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(planName, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Text(price, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.primary)
                }
                if (isSelected) {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = "Selected",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            features.forEach { feature ->
                Row(
                    modifier = Modifier.padding(vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Check,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(feature, style = MaterialTheme.typography.bodyMedium)
                }
            }
            if (!isSelected) {
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = onSelect,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Select $planName")
                }
            }
        }
    }
}
