package edu.gwu.horizons

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit

class DiscogsManager {

    private val okHttpClient: OkHttpClient

    //Don't need to deal with OAuth with this flow, it's secure and easy

    private val consumerKey: String = "OMtKOzruKRvCvNlFEzqU"
    private val consumerSecretKey: String = "vsnWOJiSxeQxOGxEnWvCDejGRncLKVct"

    init {
        val builder = OkHttpClient.Builder()

        builder.connectTimeout(20, TimeUnit.SECONDS)
        builder.readTimeout(20, TimeUnit.SECONDS)
        builder.writeTimeout(20, TimeUnit.SECONDS)

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(logging)

        okHttpClient = builder.build()
    }

    fun searchAlbums( //search albums for the search function, do another one called recommend for recommendations
        query: String,  //this is the query parameter
        successCallback: (List<Album>) -> Unit,
        errorCallback: (Exception) -> Unit
    ) {

        //Building the request, based off album or artist name
        val request = Request.Builder()
            .url("https://api.discogs.com/database/search?q=$query&key=$consumerKey&secret=$consumerSecretKey&type=release")
            .build()

        okHttpClient.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                errorCallback(e)
            }

            override fun onResponse(call: Call, response: Response) {
                val albums = mutableListOf<Album>()
                val responseString = response.body()?.string()

                if (response.isSuccessful && responseString != null) {
                    val results = JSONObject(responseString).getJSONArray("results")
                    for (i in 0 until results.length()) {
                        val curr = results.getJSONObject(i)
                        val title = curr.getString("title")
                        //val style = curr.getString("style")

                        albums.add(
                            Album(
                                title = title,
                                style = ""
                            )
                        )
                    }
                    successCallback(albums)

                } else {
                    errorCallback(Exception("Nothing matches your request or search call failed."))
                }

            }
        })

    }

}