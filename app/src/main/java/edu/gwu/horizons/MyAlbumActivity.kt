package edu.gwu.horizons

import android.os.Bundle
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

        title = getString(R.string.myalbums)

        //make sure to handle no results at a later time over here, whether that be by a nonsensical or a blank search

    }

}