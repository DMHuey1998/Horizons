package edu.gwu.horizons

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class DiscogsManager {

    private val okHttpClient: OkHttpClient

    private var oAuthToken: String? = null

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
        val request: Request.Builder()  //post request for the discogs API
            .url("https://api.discogs.com/oauth/access_token")
            .header("Authorization", "$consumerKey")
            .post(
                RequestBody.create(
                    MediaType.parse("application/x-www-form-urlencoded"),
                    "grant_type=client_credentials"
                )
            )
            .build()
    }

}