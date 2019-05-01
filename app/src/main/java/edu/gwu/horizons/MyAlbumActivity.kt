package edu.gwu.horizons

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class MyAlbumActivity: AppCompatActivity() {    //the activity that lists the albums you have saved under your account

    private lateinit var recyclerView: RecyclerView

    //this will take the albums you have saved and use the albumadapter in the recyclerview that we also used for search
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val albums = generateFakeAlbums()

        if (albums.isNotEmpty()) {
            recyclerView.adapter = AlbumsAdapter(albums)
        } else if (albums.isEmpty()) {   //handles if no albums were saved onto account
            AlertDialog.Builder(this)

                .setTitle("You have not saved any albums!")
                .setPositiveButton("OKAY") { dialog, _ ->
                    dialog.cancel()
                }
                .show()
        }
        title = getString(R.string.myalbums)

        //make sure to handle no results at a later time over here, whether that be by a nonsensical or a blank search

    }

    private fun generateFakeAlbums(): List<Album> { //fake data for the recyclerView, just like search except already owned
        return listOf(
            Album(
                artist = "The Contortionist",
                release_title = "Language",
                label = "eOne",
                genre = "Rock",
                style = "Progressive Metal",
                country = "US",
                year = "2014"
            ),
            Album(
                artist = "Polyphia",
                release_title = "New Levels New Devils",
                label = "Equal Vision Records",
                genre = "Rock",
                style = "Progressive Metal",
                country = "US",
                year = "2018"
            ),
            Album(
                artist = "Periphery",
                release_title = "Periphery IV: Hail Stan",
                label = "eOne",
                genre = "Rock",
                style = "Progressive Metal",
                country = "US",
                year = "2019"
            ),
            Album(
                artist = "Angelmaker",  //Discogs has an annoying tendency to label metal as "rock", which it isn't
                release_title = "Dissentient",
                label = "Not On Label",
                genre = "Rock",
                style = "Death Metal",
                country = "US",
                year = "2015"
            )
        )
    }
}