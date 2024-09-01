package eu.tutorials.myrecipeapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


private val retrofit = Retrofit.Builder().baseUrl("https://www.themealdb.com/api/json/v1/1/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

    val recipeService = retrofit.create(ApiService::class.java)
// we want to have this service that allows us to get this categories php file, which then we will
// converts into json objects which we can use


interface ApiService{
    // so when calling the data, when calling the website, we need to use the get keyword


    @GET("categories.php")// this allow us to do http request   and ("categories.php") is where should this http request go to (www.themealdb.com/api/json/v1/1/categories.php)
    // and (www.themealdb.com/api/json/v1/1/) this rest of the link is where we are going to use the this base URL and then connect it to this categories php

    suspend fun getCategories():CategoriesResponse     // CategoriesResponse is the return type

}

/*
this GET keyword is used to specify the type of request that should made to a
particular URL.
in our case it's the categories php URL which represents the endpoint where the get request will be sent.
so the end point is basically the final part of a URL if you will.
and retrofit uses these annotations @ to generate the necessary code to make the network request,
send the get request to the specified URL and process the response data

this suspend keyword making call to APIs are set to be an expensive operation, this is because it could take
longer than usual to display the data on the UI on the user interface depending on the lot of factors
now this kind of operations are processed asynchronously, so to avoid boring the user during the waiting time,
the suspend keyword comes into play.
it is the part of the coroutine API that provide the straightforward and structured way to manage
concurrency, basically running many or multiple task at the same time, like the UI takes care of, UI thread.

Coroutines are a powerful and lightweight concurrency framework in kotlin, specially designed for
handling asynchronous and non- blocking operations like our getting the categories from the web, they are
often used to perform tasks such as making network, reading and writing to databases or handling time consuming operation wihout blocking
the main thread i.e. UI thread.
they promote efficient and responsive user interface by running potentially time consuming operations in the background

so what will this keyword (suspend) do to the getCategories function-> it will process it in the backgorund
keeping the interface responsive until the data is retrieved

 */