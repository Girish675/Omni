package com.girish.premiumapp.presentation.expenses

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.girish.premiumapp.domain.model.ExpenseEntity
import com.girish.premiumapp.domain.model.PaymentMethod
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

private val chartColors = listOf(
    Color(0xFFBB86FC), Color(0xFF03DAC6), Color(0xFFCF6679),
    Color(0xFFFFC107), Color(0xFF4CAF50), Color(0xFF2196F3)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesScreen(
    onBack: () -> Unit,
    viewModel: ExpensesViewModel = hiltViewModel()
) {
    val expenses by viewModel.expenses.collectAsState()
    val analytics by viewModel.analytics.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var expenseToEdit by remember { mutableStateOf<ExpenseEntity?>(null) }
    var selectedTab by remember { mutableIntStateOf(0) }

    val totalSpent = expenses.sumOf { it.amount }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Expenses") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        floatingActionButton = {
            if (selectedTab == 0) {
                FloatingActionButton(
                    onClick = { showAddDialog = true },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Expense")
                }
            }
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            // Tab bar: List / Analytics
            TabRow(selectedTabIndex = selectedTab) {
                Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }, text = { Text("List") }, icon = { Icon(Icons.Default.List, contentDescription = null) })
                Tab(selected = selectedTab == 1, onClick = { selectedTab = 1; viewModel.loadAnalytics() }, text = { Text("Analytics") }, icon = { Icon(Icons.Default.PieChart, contentDescription = null) })
            }

            when (selectedTab) {
                0 -> ExpenseListTab(expenses, totalSpent, onDelete = { viewModel.deleteExpense(it) }, onEdit = { expenseToEdit = it })
                1 -> ExpenseAnalyticsTab(analytics)
            }
        }
    }

    if (showAddDialog) {
        AddEditExpenseDialog(
            expense = null,
            onDismiss = { showAddDialog = false },
            onConfirm = { amount, category, note, method, imageUri ->
                viewModel.addExpense(amount, category, note, method, imageUri)
                showAddDialog = false
            }
        )
    }

    expenseToEdit?.let { expense ->
        AddEditExpenseDialog(
            expense = expense,
            onDismiss = { expenseToEdit = null },
            onConfirm = { amount, category, note, method, imageUri ->
                viewModel.updateExpense(
                    expense.copy(amount = amount, category = category, note = note, paymentMethod = method, imageUri = imageUri)
                )
                expenseToEdit = null
            }
        )
    }
}

@Composable
fun ExpenseListTab(
    expenses: List<ExpenseEntity>,
    totalSpent: Double,
    onDelete: (Int) -> Unit,
    onEdit: (ExpenseEntity) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Total Spent", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onPrimaryContainer)
                Spacer(modifier = Modifier.height(8.dp))
                Text("₹${String.format("%.2f", totalSpent)}", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)
            }
        }

        if (expenses.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No expenses recorded yet.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        } else {
            LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(expenses, key = { it.id }) { expense ->
                    ExpenseItem(expense = expense, onDelete = { onDelete(expense.id) }, onEdit = { onEdit(expense) })
                }
            }
        }
    }
}

@Composable
fun ExpenseAnalyticsTab(analytics: AnalyticsData) {
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Summary cards
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Card(modifier = Modifier.weight(1f), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer), shape = RoundedCornerShape(16.dp)) {
                Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("This Month", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onPrimaryContainer)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("₹${String.format("%.0f", analytics.monthlyTotal)}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                }
            }
            Card(modifier = Modifier.weight(1f), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), shape = RoundedCornerShape(16.dp)) {
                Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("All Time", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("₹${String.format("%.0f", analytics.totalSpent)}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                }
            }
        }

        // Pie Chart
        if (analytics.categorySpending.isNotEmpty()) {
            Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), shape = RoundedCornerShape(16.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Spending by Category", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))

                    val total = analytics.categorySpending.sumOf { it.total }

                    Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                        Canvas(modifier = Modifier.size(180.dp)) {
                            var startAngle = -90f
                            analytics.categorySpending.forEachIndexed { index, cs ->
                                val sweepAngle = (cs.total / total * 360f).toFloat()
                                val color = chartColors[index % chartColors.size]
                                drawArc(
                                    color = color,
                                    startAngle = startAngle,
                                    sweepAngle = sweepAngle,
                                    useCenter = true,
                                    topLeft = Offset.Zero,
                                    size = Size(size.width, size.height)
                                )
                                startAngle += sweepAngle
                            }
                            // Inner circle for donut effect
                            drawCircle(
                                color = Color(0xFF1E1E1E),
                                radius = size.width * 0.3f,
                                center = center
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Legend
                    analytics.categorySpending.forEachIndexed { index, cs ->
                        val percentage = if (total > 0) (cs.total / total * 100) else 0.0
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(modifier = Modifier.size(12.dp).clip(CircleShape).background(chartColors[index % chartColors.size]))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(cs.category, style = MaterialTheme.typography.bodyMedium)
                            }
                            Text("₹${String.format("%.0f", cs.total)} (${String.format("%.0f", percentage)}%)", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
                        }
                    }
                }
            }
        } else {
            Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), shape = RoundedCornerShape(16.dp)) {
                Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                    Text("Add expenses to see analytics", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseItem(
    expense: ExpenseEntity,
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {
    var isDismissed by remember { mutableStateOf(false) }
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (it == SwipeToDismissBoxValue.EndToStart) {
                isDismissed = true
                true
            } else {
                false
            }
        }
    )

    LaunchedEffect(isDismissed) {
        if (isDismissed) {
            delay(300)
            onDelete()
        }
    }

    AnimatedVisibility(
        visible = !isDismissed,
        exit = shrinkVertically() + fadeOut(animationSpec = tween(300))
    ) {
        SwipeToDismissBox(
            state = dismissState,
            enableDismissFromStartToEnd = false,
            backgroundContent = {
                Box(
                    modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.error, RoundedCornerShape(12.dp)).padding(end = 20.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.onError)
                }
            }
        ) {
            Card(
                modifier = Modifier.fillMaxWidth().clickable { onEdit() },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = expense.category, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(4.dp))
                            if (expense.note.isNotBlank()) {
                                Text(text = expense.note, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Spacer(modifier = Modifier.height(4.dp))
                            }
                            Text(
                                text = "${expense.paymentMethod.name.replace("_", " ")} • ${SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date(expense.date))}",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "₹${String.format("%.2f", expense.amount)}",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.error
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Tap to edit", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f))
                        }
                    }

                    expense.imageUri?.let { uri ->
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current).data(Uri.parse(uri)).crossfade(true).build(),
                            contentDescription = "Expense receipt",
                            modifier = Modifier.fillMaxWidth().height(120.dp).padding(horizontal = 16.dp).padding(bottom = 12.dp).clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditExpenseDialog(
    expense: ExpenseEntity?,
    onDismiss: () -> Unit,
    onConfirm: (Double, String, String, PaymentMethod, String?) -> Unit
) {
    val isEditing = expense != null
    var amountText by remember { mutableStateOf(expense?.amount?.let { String.format("%.2f", it) } ?: "") }
    var category by remember { mutableStateOf(expense?.category ?: "Food") }
    var note by remember { mutableStateOf(expense?.note ?: "") }
    var paymentMethod by remember { mutableStateOf(expense?.paymentMethod ?: PaymentMethod.CREDIT_CARD) }
    var imageUri by remember { mutableStateOf(expense?.imageUri) }

    var categoryExpanded by remember { mutableStateOf(false) }
    var paymentExpanded by remember { mutableStateOf(false) }

    val categories = listOf("Food", "Transport", "Shopping", "Bills", "Entertainment", "Other")

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { imageUri = it.toString() }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (isEditing) "Edit Expense" else "Add Expense") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = amountText,
                    onValueChange = { amountText = it },
                    label = { Text("Amount (₹)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                ExposedDropdownMenuBox(expanded = categoryExpanded, onExpandedChange = { categoryExpanded = it }) {
                    OutlinedTextField(value = category, onValueChange = {}, readOnly = true, label = { Text("Category") }, modifier = Modifier.menuAnchor().fillMaxWidth())
                    ExposedDropdownMenu(expanded = categoryExpanded, onDismissRequest = { categoryExpanded = false }) {
                        categories.forEach { sel ->
                            DropdownMenuItem(text = { Text(sel) }, onClick = { category = sel; categoryExpanded = false })
                        }
                    }
                }

                ExposedDropdownMenuBox(expanded = paymentExpanded, onExpandedChange = { paymentExpanded = it }) {
                    OutlinedTextField(value = paymentMethod.name.replace("_", " "), onValueChange = {}, readOnly = true, label = { Text("Payment Method") }, modifier = Modifier.menuAnchor().fillMaxWidth())
                    ExposedDropdownMenu(expanded = paymentExpanded, onDismissRequest = { paymentExpanded = false }) {
                        PaymentMethod.entries.forEach { method ->
                            DropdownMenuItem(text = { Text(method.name.replace("_", " ")) }, onClick = { paymentMethod = method; paymentExpanded = false })
                        }
                    }
                }

                OutlinedTextField(value = note, onValueChange = { note = it }, label = { Text("Note (Optional)") }, modifier = Modifier.fillMaxWidth())

                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                Text("Receipt / Image", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)

                if (imageUri != null) {
                    Box(modifier = Modifier.fillMaxWidth().height(100.dp).clip(RoundedCornerShape(8.dp))) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current).data(Uri.parse(imageUri)).crossfade(true).build(),
                            contentDescription = "Selected image",
                            modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                        IconButton(
                            onClick = { imageUri = null },
                            modifier = Modifier.align(Alignment.TopEnd).padding(4.dp).size(24.dp).background(MaterialTheme.colorScheme.surface.copy(alpha = 0.8f), CircleShape)
                        ) {
                            Icon(Icons.Default.Close, contentDescription = "Remove", modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.error)
                        }
                    }
                    TextButton(onClick = { imagePickerLauncher.launch("image/*") }, modifier = Modifier.fillMaxWidth()) {
                        Icon(Icons.Default.Image, contentDescription = null, modifier = Modifier.size(18.dp)); Spacer(modifier = Modifier.width(8.dp)); Text("Change Image")
                    }
                } else {
                    OutlinedButton(onClick = { imagePickerLauncher.launch("image/*") }, modifier = Modifier.fillMaxWidth()) {
                        Icon(Icons.Default.AddAPhoto, contentDescription = null, modifier = Modifier.size(18.dp)); Spacer(modifier = Modifier.width(8.dp)); Text("Attach Receipt / Image")
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = { amountText.toDoubleOrNull()?.let { if (it > 0) onConfirm(it, category, note, paymentMethod, imageUri) } }, enabled = amountText.isNotBlank()) {
                Text(if (isEditing) "Update" else "Save")
            }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
    )
}
