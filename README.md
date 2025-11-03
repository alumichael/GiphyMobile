# GiphyMobile
GiphyMobile is a simple yet robust Android application built with Clean MVVM architecture, integrating the Giphy API for GIF search and detail viewing. The project demonstrates key Android development best practices, including dependency injection, LiveData state management, coroutines, and unit testing.


Features

1. GIF Search: Search for trending or specific GIFs using the Giphy API.

2. GIF Detail View: Tap any GIF to view details such as title, rating, and a larger preview.

3. Network State Handling: Graceful handling of offline/online states using a network utility class.

4. State Management with UiState: ViewModels emit UiState objects (Loading, Success, Error) to represent real-time UI changes.

Comprehensive Unit Testing:

ViewModel tests validate success, loading, and error states.

LiveData tested using getOrAwaitValue() extension.

Mocked repository and network layer with Mockito & JUnit.

🧱 Modular Code Structure: Clear separation of concerns across data, ui, and domain layers.

💡 Coroutines & Flow: Used for asynchronous operations and seamless thread management.
