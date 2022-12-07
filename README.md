# Evaluation app

## About this app
The app has been made in Kotlin following MVVM as design pattern + Cleanr architecture

## Components
The main components into this app are:
* Navigation component
* Koin: Dependency injection framework
* Retrofit: Type-safe HTTP client for Android
* Glide: Open source media manager

## Android
To run this app your device has been in the range of Android SDK 21 to 33.

## Note
Unfortunately, I can't complete the challenge. I get stuck into Create/Edit fragment because I use Koin throughout the whole project and I gen an error in their fragment. You can use the app, but if you hit the button "Agregrar" the app will be crash.

## Features
In a normal flow the user can:
- Open the app an see the "SignInView"
  -- Do sign in and navigate to "ListView"
  -- The app stores the user data to avoid needing to do sign in again
- Create a new register into "SignUpView"
  -- The app stores the user data to avoid needing to do sign in again
  -- Do sign up and navigate to "ListView"
- After SignUp/Sign In or open the app after doing a successful SignUp/Sign In the user can get a list of 30 rows of users, into a list
  -- The user can filter the users by name or some letter
  -- The list is sorted by name
  -- If the user selects a user from the list, then the app will open the email app with the email of the user selects