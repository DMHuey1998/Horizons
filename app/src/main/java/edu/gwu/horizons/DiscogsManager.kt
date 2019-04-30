package edu.gwu.horizons

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.sql.Timestamp
import java.util.concurrent.TimeUnit

class DiscogsManager {

    private val okHttpClient: OkHttpClient

    private var oAuthToken: String? = "EqmUZjaWTXzZRPFmccEDEUvEVMsBxZcXACkfAazY"    //I'm just gonna hardcode it in

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

        okHttpClient.newCall(request).enqueue( object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                errorCallback(e)
                
            }

        })
    }

}