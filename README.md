**📦 Project Overview: Omni**
- Omni is a high-end, all-in-one productivity and personal finance "super-app" for Android. It features a premium, modern dark-themed glassmorphism interface and is built using industry best practices for scalability, security, and performance.

**🌟 Key App Modules & Features Included:**
- 🏠 Unified Premium Dashboard: A gorgeous glassmorphism panel showing today's tasks, pending calendar events, recent notes, and budget balance at a glance.
- 🚀 Advanced Tasks & Habits: Habit Streaks tracking to monitor consistency. Bulk Management capabilities for managing multiple tasks simultaneously. State-Management Safeguards with instant Undo / Redo operations to restore items.
- 💰 Expense & Budget Tracker: Track expenses by categories (Food, Travel, Utilities) with interactive budget safety indicator bars.
- 📝 Masonry Notes: Staggered Pinterest-style note views with search, filtering, and tag/color customization.
- 📅 Calendar & Planner: Dynamic date grids with events linked directly to tasks and specific calendar dates.
- ⏱️ Focus Timer: An integrated Pomodoro-style timer with customizable focus periods and breaks linked directly to tasks.
- 🔍 Universal Search: Real-time global search across all modules (tasks, notes, events, and expenses) in one screen.
- 🛡️ Biometric Vault: Fingerprint/Face unlock powered by AndroidX Biometric with custom passcode fallback and secure cryptographic storage using EncryptedSharedPreferences.

**🛠️ Architecture & Tech Stack:**
- The project implements a Clean Architecture + MVVM pattern to decouple business logic from UI/Framework dependencies:
  - Presentation Layer: Jetpack Compose (Material 3), ViewModels, Hilt Navigation Compose, and custom theming (OmniAppTheme).
  - Domain Layer: Pure Kotlin models (TaskEntity, ExpenseEntity, etc.) and Repository Interfaces.
  - Data Layer: Local Room Database (with automated migration setups) and local datastores.
  - Core Technologies: Kotlin, Dagger Hilt (DI), Kotlin Coroutines & Flow, AndroidX Biometrics, Jetpack Security, and Coil Compose.
