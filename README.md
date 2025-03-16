# Country List Android Application

A robust Android application that fetches and displays country data from a remote JSON source, featuring clean architecture, error handling, and device rotation support.


## Features
- **Dynamic Data Fetching**: Retrieves country data from remote JSON endpoint
- **Responsive UI**:
    - RecyclerView with custom CardView items
    - Progress indicators for loading states
    - Error messages with retry functionality
- **Robust Error Handling**:
    - Network connectivity checks
    - HTTP error detection
    - Empty state management
- **Configuration Resilience**:
    - Screen rotation support
    - View state preservation
- **Modern Android Practices**:
    - MVVM architecture
    - View Binding
    - Coroutines for async operations
    - DiffUtil for efficient RecyclerView updates

## Tech Stack
- **Language**: Kotlin
- **Android Components**:
    - ViewModel
    - LiveData
    - RecyclerView
    - View Binding
- **Networking**:
    - Retrofit 2
    - Moshi


