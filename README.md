# TravelApp
TravelApp is an android app that lets you see the list of nearby train stations information based on your current location using train api

## Architecture
The app uses `MVVM Clean Architecture` design pattern. 
This provides better separation of concern, easier testing, lifecycle awareness, etc.

## Functionality
The app's functionality includes:
## Stations Screen: 
1. Shows a list of nearby stations based on current location.
2. Shows a way to search your favorite train stations and displays list of station based on stations name
3. Contains a feature to cache trains stations information for first time launch of the app and display list of stations from database for next app launches(cache_stations branch).
4. Covered with Unit testing and UI testiong of the features

## Technologies Used
1.  [Android appcompat](https://developer.android.com/jetpack/androidx/releases/appcompat), [KTX](https://developer.android.com/kotlin/ktx), [Constraint layout](https://developer.android.com/reference/androidx/constraintlayout/widget/ConstraintLayout), [Material Support](https://material.io/develop/android/docs/getting-started).
2.  [Android View Binding](https://developer.android.com/topic/libraries/view-binding)
3. [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for dependency injection.
4. [Retrofit](https://square.github.io/retrofit/) for REST API communication
5. [Coroutine](https://developer.android.com/kotlin/coroutines) for Network call
6. [Lifecycle](https://developer.android.com/jetpack/androidx/releases/lifecycle), [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
7. [Room](https://developer.android.com/jetpack/androidx/releases/room) for local database.
8. [Navigation Component](https://developer.android.com/guide/navigation/navigation-getting-started) for supporting navigation through the app.
