package edu.gwu.horizons

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import java.io.IOException
import java.sql.Timestamp
import java.util.concurrent.TimeUnit

class DiscogsManager {

    private val okHttpClient: OkHttpClient

    private var oAuthToken: String = "EqmUZjaWTXzZRPFmccEDEUvEVMsBxZcXACkfAazY"    //I'm just gonna hardcode it in because thug life

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

    fun retrieveOAuthToken(
        successCallback: (String) -> Unit,
        errorCallback: (Exception) -> Unit
    ) {
        if (oAuthToken != null) {
            successCallback(oAuthToken!!)
            return
        }

        val consumerKey =
                "OMtKOzruKRvCvNlFEzqU"
        val consumerSecret =
                "vsnWOJiSxeQxOGxEnWvCDejGRncLKVct"
        val currentTime = Timestamp(System.currentTimeMillis())

        //this is the post request with the following parameters:
        /*OAuth oauth_consumer_key="your_consumer_key",
            * oauth_nonce="random_string_or_timestamp",
            * oauth_token="oauth_token_received_from_step_2"
            * oauth_signature="your_consumer_secret&",
            * oauth_signature_method="PLAINTEXT",
            * oauth_timestamp="current_timestamp",
            * oauth_verifier="users_verifier"
            I have no idea what I'm doing so here goes
        */

        val request: Request = Request.Builder()
            .url("https://api.discogs.com/oauth/access_token")
            .header("oauth_consumer_key", consumerKey)
            .header("oauth_token", oAuthToken)
            .header("oauth_signature", consumerSecret)
            .post(
                RequestBody.create(
                    MediaType.parse("application/x-www-form-urlencoded"),
                    "grant_type=client_credentials"
                )
            )
            .build()

        okHttpClient.newCall(request).enqueue(object : Callback {
            /**
             * [onFailure] is called if OkHttp is has an issue making the request (for example,
             * no network connectivity).
             */
            override fun onFailure(call: Call, e: IOException) {
                // Invoke the callback passed to our [retrieveOAuthToken] function.
                errorCallback(e)
            }

            /**
             * [onResponse] is called if OkHttp is able to get any response (successful or not)
             * back from the server
             */
            override fun onResponse(call: Call, response: Response) {
                // The token would be part of the JSON response body
                val responseBody = response.body()?.string()

                // Check if the response was successful (200 code) and the body is non-null
                if (response.isSuccessful && responseBody != null) {
                    // Parse the token out of the JSON
                    val jsonObject = JSONObject(responseBody)
                    val token = jsonObject.getString("access_token")
                    oAuthToken = token

                    // Invoke the callback passed to our [retrieveOAuthToken] function.
                    successCallback(token)
                } else {
                    // Invoke the callback passed to our [retrieveOAuthToken] function.
                    errorCallback(Exception("OAuth call failed"))
                }
            }
        })
    }
    fun retrieveAlbums(
        oAuthToken: String,
        query: String,  //this is the query parameter
        successCallback: (List<Album>) -> Unit,
        errorCallback: (Exception) -> Unit
    ) {

        //Building the request, based off album and artist name
        val request = Request.Builder()
            .url("https://api.discogs.com/database/search?q=$query&{release_title,artist}")
            .header("Authorization", oAuthToken)
            .build()

        okHttpClient.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                errorCallback(e)
            }

            override fun onResponse(call: Call, response: Response) {
                val albums = mutableListOf<Album>()
                val responseString = response.body()?.string()

                if (response.isSuccessful && responseString != null) {
                    val statuses = JSONObject(responseString).getJSONArray("statuses")
                    for (i in 0 until statuses.length()) {
                        val curr = statuses.getJSONObject(i)
                        val artist = curr.getString("artist")
                        val release_title = curr.getString("release_title")
                        val label = curr.getString("label")
                        val genre = curr.getString("genre")
                        val style = curr.getString("style")
                        val country = curr.getString("country")
                        val year = curr.getString("year")
                        albums.add(
                            Album(
                                artist = artist,
                                release_title = release_title,
                                label = label,
                                genre = genre,
                                style = style,
                                country = country,
                                year = year
                            )
                        )
                        successCallback(albums)
                    }

                } else {
                    errorCallback(Exception("Nothing matches your request or search call failed."))
                }

            }
        })

    }

}