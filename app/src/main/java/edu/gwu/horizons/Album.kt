package edu.gwu.horizons

data class Album (  //I am using the query section of the discogs search API documentation
    val title: String,
    val style: String
    //I deleted all the unnecessary data that won't help, instead replacing it with genre and style
)