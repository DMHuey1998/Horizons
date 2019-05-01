package edu.gwu.horizons

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import edu.gwu.gwu_explorer.AlbumsAdapter

//returns albums based on search parameters, with that parameter being either album or artist
class ReturnActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_return)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val albums = generateFakeAlbums()

        if (albums.isNotEmpty()) {
            recyclerView.adapter = AlbumsAdapter(albums)
        } else if (albums.isEmpty()) {   //handles if nonsense or nothing was put into the search bar
            AlertDialog.Builder(this)

                .setTitle("No Albums Match Your Search")
                .setPositiveButton("OKAY") { dialog, _ ->
                    dialog.cancel()
                }
                .show()
        }
        title = getString(R.string.myalbums)

        //make sure to handle no results at a later time over here, whether that be by a nonsensical or a blank search

    }

    private fun generateFakeAlbums(): List<Album> { //fake data for the recyclerView
        return listOf(
            Album(
                artist = "Circa Survive",   //this album is great for concentrating on projects such as this one
                release_title = "Juturna",
                label = "Equal Vision Records",
                genre = "Rock",
                style = "Emo",
                country = "US",
                year = "2005"
            ),
            Album(
                artist = "Dance Gavin Dance",   //this is my favorite album from my favorite band ever
                release_title = "Mothership",
                label = "Rise Records",
                genre = "Rock",
                style = "Post-Hardcore",
                country = "US",
                year = "2016"
            ),
            Album(
                artist = "Architects",
                release_title = "Holy Hell",
                label = "Epitaph",
                genre = "Rock",
                style = "Metalcore",
                country = "UK",
                year = "2018"
            ),
            Album(
                artist = "Saosin",  //this is technically an EP and not an album but whatevs
                release_title = "Translating the name",
                label = "Death Do Us Part",
                genre = "Rock",
                style = "Emo",
                country = "US",
                year = "2003"
            )
        )
    }
}