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

    //private val searchActivity: SearchActivity = SearchActivity()   //we'll use this bad boy to get the query parameter
    private val discogsManager: DiscogsManager = DiscogsManager()   //this is the manager for the API call and what not
    private val albumsList: MutableList<Album> = mutableListOf()    //list of albums that will be returned

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_return)

        val inputtedSearch = "Nirvana"

        discogsManager.searchAlbums(
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
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("ALBUMS", ArrayList(albumsList))
    }

}