package edu.gwu.gwu_explorer

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import edu.gwu.horizons.Album
import edu.gwu.horizons.R

class AlbumsAdapter constructor(private val albums: List<Album>) : RecyclerView.Adapter<AlbumsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_album, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = albums.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentAlbum = albums[position]

        holder.artistTextView.text = currentAlbum.artist
        holder.releaseTextView.text = currentAlbum.release_title
        holder.labelTextView.text = currentAlbum.label
        holder.genreTextView.text = currentAlbum.genre
        holder.styleTextView.text = currentAlbum.style
        holder.countryTextView.text = currentAlbum.country
        holder.yearTextView.text = currentAlbum.year
    }

    class ViewHolder constructor(view: View) : RecyclerView.ViewHolder(view) {

        val artistTextView: TextView = view.findViewById(R.id.artist)

        val releaseTextView: TextView = view.findViewById(R.id.release_title)

        val labelTextView: TextView = view.findViewById(R.id.label)

        val genreTextView: TextView = view.findViewById(R.id.genre)

        val styleTextView: TextView = view.findViewById(R.id.style)

        val countryTextView: TextView = view.findViewById(R.id.country)

        val yearTextView: TextView = view.findViewById(R.id.year)

    }
}