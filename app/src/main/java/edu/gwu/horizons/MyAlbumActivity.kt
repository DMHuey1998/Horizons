package edu.gwu.horizons

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MyAlbumActivity: AppCompatActivity() {    //the activity that lists the albums you have saved under your account


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)



    }
}