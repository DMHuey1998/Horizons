package edu.gwu.horizons

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class AlbumsAdapter constructor(private val albums: List<Album>) : RecyclerView.Adapter<AlbumsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_album, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = albums.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentAlbum = albums[position]

        holder.titleTextView.text = currentAlbum.title
        holder.styleTextView.text = currentAlbum.style

    }

    class ViewHolder constructor(view: View) : RecyclerView.ViewHolder(view) {

        val titleTextView: TextView = view.findViewById(R.id.title)

        val styleTextView: TextView = view.findViewById(R.id.style)

    }
}