<img height='200' src="app/src/main/res/mipmap-xxxhdpi/ic_anyrecipe_launcher_logo_round.png" align="left">

# AnyRecipes
AnyRecipes is a simple recipes for Android that allow user to view recipes and save their favorite recipes. This app is written in Kotlin with <a href='https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html'>Clean Architecture</a>, Using Android <a href='https://developer.android.com/jetpack'>Jetpack</a> Library such as Navigation, Room, ViewModel, Paging and LiveData. <a href='https://github.com/InsertKoinIO/koin'>Koin</a> is used to perform dependencies injection in this app. This app apply modularization, there is 3 module: app module, core module and feature module. This app also using Kotlin <a href='https://github.com/Kotlin/kotlinx.coroutines'>Coroutine</a> to perform asyncronous operation, Coroutine Flow to handle asyncronous data stream and <a href='https://square.github.io/retrofit/'>Retrofit</a> to communicate with REST API. All recipes data is provided by <a href='https://spoonacular.com/food-api/'>Spoonacular API</a>. And last but not least, this app also apply Advanced Security such as Database Encryption, obfuscation with proguard and Certificate Pinning (to secure the connection with the API). This app was developed to complete <a href='https://www.dicoding.com/academies/165'>Menjadi Android Developer Expert</a> final project and got 5/5 score.

## Preview
<p float="center">
  <img src="docs/AnyRecipes%20Presentation.png" width="1000" />
</p>

## Demo
You can view the app demo on youtube: https://youtu.be/wVXGlu1AlEs

## Project Setup

You need <a href='https://spoonacular.com/food-api'>Spoonacular API</a> Key to make this project work. Make a new file, api.properties, on the root folder and put your api key like this:

In your api.properties (AnyRecipes/api.properties) :

```xml
...

SPOONACULAR_API_KEY = "YOUR_API_KEY"

...
```

## Built With

<pre>
<a href='https://developer.android.com/jetpack'>Android Jetpack</a> - Room, Navigation, Paging, LiveData, ViewModel, Hilt etc.
<a href='https://github.com/google/gson'>Gson</a> - A Java serialization/deserialization library to convert Java Objects into JSON and back.
<a href='https://github.com/bumptech/glide'>Glide</a> - An image loading and caching library for Android focused on smooth scrolling.
<a href='https://github.com/vinc3m1/RoundedImageView'>RoundedImageView</a> - A fast ImageView that supports rounded corners, ovals, and circles.
<a href='https://square.github.io/retrofit/'>Retrofit</a> - A type-safe HTTP client for Android and Java.
<a href='https://github.com/facebook/shimmer-android'>Facebook Shimmer</a> - An easy, flexible way to add a shimmering effect to any view in an Android app.
<a href='https://github.com/airbnb/lottie-android'>Lottie</a> - Lottie is a mobile library for Android and iOS that parses Adobe After Effects animations exported as json with Bodymovin and renders them natively on mobile.
</pre>

## Author

Nandra Saputra
* <a href='https://www.instagram.com/nandrasptr/'>@nandrasptr</a> on Instagram
* <a href='https://www.linkedin.com/in/nandra-saputra-b90b78157/'>LinkedIn</a> Account

## Download Application - APK File
This app require Android 5.0 - Lolipop or higher to run, Please check release section to download the app.

## License

Apache 2.0. See the <a href='https://github.com/nandrasaputra/AnyRecipes/blob/master/LICENSE'>LICENSE</a> file for details.
