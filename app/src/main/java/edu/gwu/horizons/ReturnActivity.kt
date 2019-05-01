package edu.gwu.horizons

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import edu.gwu.horizons.AlbumsAdapter
import edu.gwu.horizons.DiscogsManager

//returns albums based on search parameters, with that parameter being either album or artist
//in it, you want to find put a button to add that album that will add it to firebase. A floating action button, if you will.
class ReturnActivity : AppCompatActivity() {

    private val searchActivity: SearchActivity = SearchActivity()   //we'll use this bad boy to get the query parameter
    private val discogsManager: DiscogsManager = DiscogsManager()   //this is the manager for the API call and what not
    private val albumsList: MutableList<Album> = mutableListOf()    //list of albums that will be returned

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_return)

        val inputtedSearch = searchActivity.searchContent.toString()

        discogsManager.retrieveOAuthToken(
            successCallback = { token ->

                discogsManager.searchAlbums(
                    oAuthToken = token,
                    query = inputtedSearch,   //takes the searchContent variable from searchActivity
                    successCallback = { albums ->
                        runOnUiThread {
                            albumsList.clear()
                            albumsList.addAll(albums)

                            recyclerView.adapter =
                                    AlbumsAdapter(albums)
                        }
                    },
                    errorCallback = {
                        runOnUiThread {
                            Toast.makeText(this@ReturnActivity, "Error retrieving Albums!", Toast.LENGTH_LONG).show()
                        }
                    }
                )
            },
            errorCallback = { exception ->
                runOnUiThread {
                    Toast.makeText(this@ReturnActivity, "Error performing OAuth", Toast.LENGTH_LONG)
                        .show()
                }
            }
        )

        //make sure to handle no results at a later time over here, whether that be by a nonsensical or a blank search

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("ALBUMS", ArrayList(albumsList))
    }

    private fun generateFakeAlbums(): List<Album> { //fake data for the recyclerView, we aren't going to use this anymore though but I'll leave it in here for old times' sake
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