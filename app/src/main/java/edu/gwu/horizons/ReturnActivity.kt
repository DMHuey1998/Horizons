package edu.gwu.horizons

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

//returns albums based on search parameters, with that parameter being either album or artist
class ReturnActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_return)


    }

    private fun generateFakeAlbums(): List<Album> {
        return listOf(
            Album(
                artist = "Circa Survive",
                release_title = "Juturna",
                label =
            )
        )
    }
}