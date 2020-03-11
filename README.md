# Trending App

This Android project is built with MVVM architecture using [Androidx](https://developer.android.com/jetpack/androidx) and [Android Jetpack components](https://developer.android.com/jetpack) 

## Package-Structure:

This Project contains mainly four components :

	
        1. DataSource - This package provides data source to the App using both Network and Room DB.
	
        2. DI - This package contains Dagger v2.0 Modules and Components used to provide dependency at runtime.
	
        3. Repository - This layer provides an interface between ViewModel and DataSource.
	
        4. View/ViewModel - This layer provides interaction between View and ViewModel using Data Binding approach.

## Dependencies:

To build this App following third party dependencies have been used:

		1. Android Lifecycle: Android lifecycle components such as RoomDB and lifecycle extensions
	
		2. Glide: Use to load images inside Recycler View
	
		3. Retrofit and Moshi: Use to make rest Api call and Parse Json to Model
		
		4. Dagger: Use to Inject dependency of various components
		
		5. Coroutines: Concurrency design pattern used to manage long running operation


<h2 id="report">Questions & Issues :thinking:</h2>

Please shoot any issue or questions directly on [email](Pardeepsharma.dev@gmail.com) for the fastest answer.

<h2 id="more-examples">Screen Shots :fire:</h2>

**Home Page (include Trending Repository and Trendind Devs as on 10 March, 2020)**

![alt tag](https://github.com/prdp89/Trending-App/blob/master/screenshots/pic_1.jpeg)
<br/><br/>

![alt tag](https://github.com/prdp89/Trending-App/blob/master/screenshots/pic_2.jpeg)
<br/><br/>

**Trending Repository Detail Page**
![alt tag](https://github.com/prdp89/Trending-App/blob/master/screenshots/pic_3.jpeg)

<h2 id="creators">Special Thanks :heart:</h2>

- [miguelcatalan](https://github.com/MiguelCatalan/MaterialSearchView) - Miguel Catalan Bañuls
		
<h2 id="examples">About Dev :eyes:</h2>

- [Linkedin](https://www.linkedin.com/in/pardeep-sharma-dev/) - Pardeep Sharma
