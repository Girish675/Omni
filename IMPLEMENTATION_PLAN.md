# Premium Android Productivity App - Architecture & Design Proposal (v1.0)

This document outlines the complete architecture, technical stack, security strategy, and development roadmap for the premium Android application.

## Proposed Tech Stack

- **Language:** Kotlin
- **UI Framework:** Jetpack Compose (Modern, declarative UI, excellent for premium animations and theming)
- **Architecture:** Clean Architecture + MVVM (Model-View-ViewModel)
- **Dependency Injection:** Dagger Hilt
- **Local Database:** Room Database (SQLite abstraction with built-in migration support)
- **Navigation:** Jetpack Navigation Compose
- **Security:** `androidx.biometric:biometric` for Fingerprint/Face unlock, `EncryptedSharedPreferences` for sensitive keys.
- **Asynchronous Operations:** Kotlin Coroutines & Flow

## Architecture Proposal: Clean Architecture + MVVM

The app will be divided into three main layers to ensure modularity and future-proofing:
1. **Presentation Layer (UI):** Contains Jetpack Compose screens and ViewModels.
2. **Domain Layer (Business Logic):** Contains Use Cases and Repository Interfaces. This is completely independent of Android framework dependencies.
3. **Data Layer (Storage/Network):** Contains Room Database, Data Sources, and Repository Implementations.

## Security Strategy (Biometrics & App Lock)

- **Biometric Prompt:** Use AndroidX Biometric API to prompt for Fingerprint or Face Authentication upon app launch.
- **Fallback PIN:** Implement a custom PIN screen if biometrics fail or are unavailable.
- **Encrypted Storage:** Use Android Keystore and `EncryptedSharedPreferences` to securely store the user's PIN hash and other sensitive preferences.

## Data Persistence & Migration Strategy

- **Room Database:** All user data (Tasks, Notes, Expenses) will be stored in a local SQLite database via Room. This guarantees offline-first functionality.
- **Migrations:** Room supports automated and manual migrations. When adding a new feature (e.g., Calendar), we will increment the database version and provide a `Migration` script to alter existing tables without dropping them, ensuring **zero data loss**.
- **Backup:** We can configure `android:allowBackup="true"` and specify backup rules so that Google Drive automatically backs up the database when the phone backs up.

## UI/UX Design Suggestions (Home Dashboard)

- **Theme:** Dark mode by default, utilizing a sleek color palette (e.g., deep charcoal background, subtle neon or pastel accents for modules).
- **Typography:** Modern sans-serif (like Inter or Google Sans) for a clean, premium look.
- **Layout:** A grid or staggered layout of premium "Glassmorphism" or neatly elevated cards with rounded corners.
  - *Header:* Greeting + Date + Settings/Profile Icon.
  - *Expense Card:* A quick summary chart or total balance.
  - *TODO Card:* Top 3 pending tasks with a quick "Add" button.
  - *Notes Card:* Recent notes in a masonry layout.
  - *Calendar Card:* A minimal weekly strip view.
- **Interactions:** Subtle scale animations on press, smooth transitions between screens using Navigation Compose.

## Modular Project Structure

```text
app/
 ┣ data/
 ┃ ┣ local/ (Room DB, DAOs, Entities)
 ┃ ┣ repository/ (Repository Implementations)
 ┃ ┗ datastore/ (Encrypted Prefs)
 ┣ domain/
 ┃ ┣ model/ (Core data models)
 ┃ ┣ repository/ (Interfaces)
 ┃ ┗ usecase/ (Business logic e.g., GetTasksUseCase)
 ┣ presentation/
 ┃ ┣ theme/ (Colors, Typography, Shapes)
 ┃ ┣ dashboard/ (Home Screen Compose UI & ViewModel)
 ┃ ┣ tasks/
 ┃ ┣ notes/
 ┃ ┣ expenses/
 ┃ ┗ security/ (Biometric/PIN screens)
 ┗ di/ (Hilt Modules)
```

## How to add future features safely without data loss
1. **Never change existing database columns** directly. Instead, use Room Migrations (`@Database(version = 2)` and provide a `Migration(1, 2)` object).
2. **Create new packages** for new features (e.g., `presentation/calendar/`) without touching existing UI code, except to add a new card to the Dashboard and a new navigation route.
3. **Isolate business logic** in specific UseCases so you don't accidentally break the Expense tracker while updating the Calendar.
