# Scotia Home App

## Home task

All provided APIs are used in the offer. The main functionality consists of:

- User is able to enter github userid and see avatar, username and list of repositories.
- User is able to view repository details.
- Added UI Tests -> home screen flow.
- Added Unit tests -> small parts of business logic. Like StringExtensions.
- Added Unit tests -> other parts of business logic, coroutines.

## PreSetup

> Please make sure you are using Java 11 Gradle jdk! Go to Android Studio preferences -> Build, Execution, Deployment -> Build tools -> Gradle JDK -> select 11 version

## Implemented

Example includes:

- MVVM approach with repository pattern
- Android ViewModel
- DataBinding/ViewBinding
- Hilt for dependency injection
- Flow
- Coroutines

## Todo

The things I would like to improve in the future:

- Handle custom status view text alignment.
- Add background and images in the custom status view.
- Error handling. Coroutine error handler.
- Tablets layout.
- Landscape layout.
- More Tests.