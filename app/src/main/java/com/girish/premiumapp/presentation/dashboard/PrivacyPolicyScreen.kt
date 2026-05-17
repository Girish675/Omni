package com.girish.premiumapp.presentation.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyPolicyScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Privacy Policy") },
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
            // Header Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.Security,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        "Omni Productivity",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        "Privacy Policy",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Last updated: May 11, 2026",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
                    )
                }
            }

            // Introduction
            PolicySection(
                title = "1. Introduction",
                content = "Welcome to Omni Productivity (\"we\", \"our\", \"us\"). We are committed to protecting your personal information and your right to privacy. This Privacy Policy explains how we collect, use, disclose, and safeguard your information when you use our mobile application.\n\nPlease read this privacy policy carefully. If you do not agree with the terms of this privacy policy, please do not access the application."
            )

            // Information We Collect
            PolicySection(
                title = "2. Information We Collect",
                content = "We collect information that you voluntarily provide to us when you use the application:\n\n" +
                    "• Personal Information: Display name, email address, and phone number provided in your account settings.\n\n" +
                    "• Usage Data: Expenses, tasks, notes, and calendar events you create within the app.\n\n" +
                    "• Device Information: Device type, operating system version, and unique device identifiers for app functionality.\n\n" +
                    "• Biometric Data: If you enable biometric lock, we use your device's biometric authentication system. We do not store your biometric data — it is handled entirely by your device's secure hardware."
            )

            // How We Use Your Information
            PolicySection(
                title = "3. How We Use Your Information",
                content = "We use your information for the following purposes:\n\n" +
                    "• To provide and maintain the application's core functionality\n" +
                    "• To store your expenses, tasks, notes, and events locally on your device\n" +
                    "• To enable Cloud Sync functionality if activated by you\n" +
                    "• To send notifications and reminders you have opted into\n" +
                    "• To improve and optimize the application experience\n" +
                    "• To detect and prevent technical issues"
            )

            // Data Storage
            PolicySection(
                title = "4. Data Storage & Security",
                content = "Your data is primarily stored locally on your device using encrypted databases. We implement appropriate technical and organizational security measures to protect your personal information.\n\n" +
                    "• Local Storage: All your expenses, notes, tasks, and calendar data are stored locally on your device using Room Database.\n\n" +
                    "• Encryption: Sensitive data like passwords are stored using Android's security-crypto library.\n\n" +
                    "• Cloud Sync: If you enable Cloud Sync, your data is encrypted in transit and at rest on our secure servers.\n\n" +
                    "• Biometric Security: Biometric authentication uses Android's BiometricPrompt API and never exposes your biometric data to the application."
            )

            // Data Sharing
            PolicySection(
                title = "5. Data Sharing & Third Parties",
                content = "We do not sell, trade, or rent your personal information to third parties. Your data may only be shared in the following limited circumstances:\n\n" +
                    "• With your explicit consent\n" +
                    "• To comply with legal obligations or law enforcement requests\n" +
                    "• To protect the rights, property, or safety of our users\n" +
                    "• In connection with a merger, acquisition, or sale of assets (with prior notice)"
            )

            // Your Rights
            PolicySection(
                title = "6. Your Rights",
                content = "You have the following rights regarding your personal data:\n\n" +
                    "• Access: You can view all data stored by the application at any time.\n\n" +
                    "• Correction: You can update your personal information through the Manage Account screen.\n\n" +
                    "• Deletion: You can delete your account and all associated data through the \"Delete Account\" option in Manage Account.\n\n" +
                    "• Data Portability: You can export your data in standard formats.\n\n" +
                    "• Opt-out: You can disable notifications, cloud sync, and biometric lock at any time from Settings."
            )

            // Children's Privacy
            PolicySection(
                title = "7. Children's Privacy",
                content = "Our application is not intended for children under the age of 13. We do not knowingly collect personal information from children under 13. If we discover that a child under 13 has provided us with personal information, we will delete such information from our records immediately."
            )

            // Changes to Policy
            PolicySection(
                title = "8. Changes to This Policy",
                content = "We may update this privacy policy from time to time. We will notify you of any changes by posting the new privacy policy within the application and updating the \"Last updated\" date. You are advised to review this privacy policy periodically for any changes."
            )

            // Contact Us
            PolicySection(
                title = "9. Contact Us",
                content = "If you have any questions or concerns about this Privacy Policy, please contact us at:\n\n" +
                    "Email: privacy@omniproductivity.app\n" +
                    "Website: www.omniproductivity.app/privacy"
            )

            // Footer
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    "By using Omni Productivity, you acknowledge that you have read and understood this Privacy Policy and agree to its terms.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun PolicySection(title: String, content: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                content,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
