# Changelog

## Version 0.1.0 - Architecture Setup and Inventory MVP

### ✨ New Features

*   **Domain Module (`domain`)**: A pure, framework-agnostic business layer has been created, containing:
    *   **Entities**: `Set`, `Brick`, `TradeOffer`, and `OfferStatus` that define the business models.
    *   **Repositories**: `InventoryRepository` and `TradeRepository` interfaces that define the contracts for data retrieval.
    *   **Use Cases**: `AddSetToInventoryUseCase` and `GetSetInventoryUseCase` to encapsulate business logic.

*   **Data Module (`data`)**: The data access layer has been implemented, including:
    *   **Repository Implementation**: `InventoryRepositoryImpl` which implements the inventory data retrieval logic.
    *   **Local Database**: Full Room setup with `AppDatabase`, the `SetEntity` entity, and the `SetDao` DAO for local persistence.
    *   **Mappers**: Extension functions to safely convert models between the `data` and `domain` layers.

*   **Presentation Module (`app`)**: The user interface and presentation logic have been built:
    *   **ViewModel**: `InventoryViewModel` that manages the UI state and communicates with the use cases.
    *   **UI State**: `InventoryUiState` that models all possible screen states (loading, error, success, empty).
    *   **Inventory Screen**: `InventoryScreen` created with Jetpack Compose, which reacts to ViewModel state changes and renders the corresponding UI.
    *   **Reusable Components**: `SetList` and `InventorySetItem` to display data in a structured way.

*   **Dependency Injection**: Hilt has been configured throughout the project to manage the lifecycle and provision of dependencies (`AppDatabase`, `SetDao`, `InventoryRepository`, Use Cases, etc.).

*   **Testing**: A solid foundation for testing has been established across all layers:
    *   **Domain Tests**: Unit tests for use cases with MockK.
    *   **Data Tests**: Tests for the `InventoryRepository` implementation.
    *   **ViewModel Tests**: Tests for the `InventoryViewModel` using `Turbine` to verify `StateFlow` state transitions.

*   **Compose Previews**: Previews have been added for all Composables, including simulations of different main screen states to speed up UI development.

### 🏗️ Architecture

*   A clean, multi-module architecture has been implemented, following the principles of **Clean Architecture**, with a clear separation of responsibilities between the `app` (presentation), `domain` (business), and `data` (data) layers.
*   A **version catalog (`libs.versions.toml`)** has been configured to manage dependencies centrally.
*   The project has been migrated to use **KSP (Kotlin Symbol Processing)** instead of Kapt for annotation processing, improving compilation times.
