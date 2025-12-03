# Changelog

## Version 0.3.0 - Rich Data Display & UI Interactivity

### ✨ New Features

*   **Interactive Set Status**: Users can now tap a switch on each set item to toggle its status between "Built" and "In Box". The change is saved and reflected in real-time.
*   **Detailed Set Information**: The app now fetches and displays more detailed information for each set:
    *   **Theme Name**: Shows the official theme name (e.g., "Star Wars").
    *   **Minifigure Count**: Displays the number of minifigures included in the set.
    *   **Acquisition & Build Dates**: Automatically records and displays the date a set was acquired and the date it was marked as built.

### ♻️ Refactoring

*   **API Logic**: The repository now makes multiple parallel calls to different API endpoints (sets, minifigs, themes) to gather and combine all necessary data.
*   **Utility Functions**: Helper functions like `getCurrentDate` have been moved to a dedicated `utils` package for better code organization.

---

## Version 0.2.0 - Rebrickable API Integration & UI Enhancements

### ✨ New Features

*   **Remote API Integration**: The application now connects to the live **Rebrickable API** to fetch details for LEGO sets, replacing the previous data simulation.
*   **Set Image Display**: The inventory list now displays an image for each set, loaded from the URL provided by the Rebrickable API using the **Coil** library.
*   **API Integration Test**: An instrumented test has been added to verify the connection and data parsing from the Rebrickable API.

### 🐛 Bug Fixes

*   **Fixed Application Crash**: Resolved a critical "black screen" crash on startup by correcting the Hilt dependency injection graph.
*   **Fixed API Calls**: Corrected the Rebrickable API calls to automatically append the required `-1` suffix to set numbers, resolving 404 errors.
*   **Passed All Unit Tests**: All unit tests in all modules were updated and fixed.

---

## Version 0.1.0 - Architecture Setup and Inventory MVP

### ✨ New Features

*   **Domain & Data Modules**: Created pure `domain` and `data` layers, establishing a Clean Architecture.
*   **Presentation Module (`app`)**: Built the initial UI with Jetpack Compose, including the `InventoryScreen` and `InventoryViewModel`.
*   **Dependency Injection**: Configured Hilt for dependency management.
*   **Testing**: Established a solid foundation for unit and integration testing.
