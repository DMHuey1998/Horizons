package edu.gwu.horizons

data class Album (  //I am using the query section of the discogs search API documentation
    val artist: String,
    val release_title: String,
    val label: String,
    val genre: String,
    val style: String,
    val country: String,
    val year: String   //the release year in discogs is a string data type
    //these need to be complex because of the granular nature of modern musical subgenres
    //(it's how you get genres like blackened emo post-vegan grindcore)

)