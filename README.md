<h1 align="center">Omni: The Premium Productivity Super-App</h1>

<div align="center">
  <p><strong>A high-end, all-in-one productivity and personal finance application for Android.</strong></p>
  <img alt="Android" src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white" />
  <img alt="Kotlin" src="https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white" />
  <img alt="Jetpack Compose" src="https://img.shields.io/badge/Jetpack_Compose-4285F4?style=for-the-badge&logo=android&logoColor=white" />
  <img alt="Room Database" src="https://img.shields.io/badge/Room-000000?style=for-the-badge&logo=android&logoColor=white" />
</div>

<br/>

## 📦 Project Overview

**Omni** is a meticulously crafted productivity super-app designed to help you organize your life. It features a premium, modern dark-themed **glassmorphism** interface and is built using industry best practices for scalability, security, and performance. Omni combines task management, financial tracking, note-taking, and secure storage into one unified, elegant experience.

---

## 🌟 Key Features

*   **🏠 Unified Premium Dashboard:** A gorgeous glassmorphism panel showing today's tasks, pending calendar events, recent notes, and your budget balance at a glance.
*   **🚀 Advanced Tasks & Habits:** 
    *   **Habit Streaks:** Track daily habits and monitor your consistency over time.
    *   **Bulk Management:** Efficiently manage multiple tasks simultaneously.
    *   **State-Management Safeguards:** Instant Undo / Redo operations to prevent accidental deletions.
*   **💰 Expense & Budget Tracker:** Track expenses by custom categories (Food, Travel, Utilities, etc.) with interactive budget safety indicator bars.
*   **📝 Masonry Notes:** Staggered, Pinterest-style note views with search, filtering, and rich tag/color customization.
*   **📅 Calendar & Planner:** Dynamic date grids with events linked directly to tasks and specific calendar dates.
*   **⏱️ Focus Timer:** An integrated Pomodoro-style timer with customizable focus periods and breaks, linked directly to your tasks.
*   **🔍 Universal Search:** Real-time global search across all modules (tasks, notes, events, and expenses) in one unified screen.
*   **🛡️ Biometric Vault:** Fingerprint/Face unlock powered by AndroidX Biometric, featuring a custom passcode fallback and secure cryptographic storage using `EncryptedSharedPreferences`.

---

## 📸 Screenshots

| Dashboard | Tasks & Habits | Expense Tracker | Notes |
|:---:|:---:|:---:|:---:|
| *(Add Screenshot Here)* | *(Add Screenshot Here)* | *(Add Screenshot Here)* | *(Add Screenshot Here)* |

---

## 🛠️ Architecture & Tech Stack

Omni implements **Clean Architecture** paired with the **MVVM (Model-View-ViewModel)** design pattern. This ensures a clean separation of concerns, decoupling business logic from UI and framework dependencies.

### 🏗️ Project Structure
*   **Presentation Layer:** Jetpack Compose (Material 3), ViewModels, Hilt Navigation Compose, and custom theming (`OmniAppTheme`).
*   **Domain Layer:** Pure Kotlin models (`TaskEntity`, `ExpenseEntity`, etc.) and Repository Interfaces encapsulating core business rules.
*   **Data Layer:** Local Room Database (with automated migration setups) and local datastores for offline-first functionality.

### 💻 Technologies Used
*   **Language:** Kotlin
*   **UI Toolkit:** Jetpack Compose
*   **Dependency Injection:** Dagger Hilt
*   **Asynchronous Programming:** Kotlin Coroutines & Flow
*   **Local Database:** Room Database
*   **Security:** AndroidX Biometrics & Jetpack Security (`EncryptedSharedPreferences`)
*   **Image Loading:** Coil Compose

---

## 🚀 Getting Started

Follow these steps to set up the project locally:

### Prerequisites
*   Android Studio (Latest version recommended)
*   JDK 17 or higher
*   An Android device or emulator running Android 8.0 (API level 26) or higher.

### Installation
1. **Clone the repository:**
   ```bash
   git clone https://github.com/Girish675/Omni.git
   ```
2. **Open the project in Android Studio:**
   * Open Android Studio -> `File` -> `Open` -> Select the `Omni` directory.
3. **Sync Gradle:**
   * Allow Android Studio to sync the Gradle files and download all necessary dependencies.
4. **Run the App:**
   * Select your target device or emulator and click the **Run** ▶️ button in Android Studio.

---

## 🤝 Contributing

Contributions, issues, and feature requests are welcome! Feel free to check out the [issues page](https://github.com/Girish675/Omni/issues).

1. Fork the project.
2. Create your feature branch: `git checkout -b feature/MyFeature`
3. Commit your changes: `git commit -m 'Add some feature'`
4. Push to the branch: `git push origin feature/MyFeature`
5. Open a Pull Request.

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

<br/>

<div align="center">
  <sub>Built with ❤️ by <a href="https://github.com/Girish675">Girish675</a></sub>
</div>
