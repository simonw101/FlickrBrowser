package com.example.flickrbrowser

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

private const val TAG = "FlickrRecyclerViewAdapt"

class FlickrImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var thumbNail: ImageView = view.findViewById(R.id.thumbnail)
    var title: TextView = view.findViewById(R.id.title)

}

class FlickrRecyclerViewAdapter(private var photoList : List<Photo>) : RecyclerView.Adapter<FlickrImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlickrImageViewHolder {
       // called by layout manager when needs a new view
        Log.d(TAG, ".onCreateViewHolder new view requested ")

        val view = LayoutInflater.from(parent.context).inflate(R.layout.browse, parent, false)

        return FlickrImageViewHolder(view)

    }

    override fun getItemCount(): Int {
        Log.d(TAG, ".getItemCount called")
        return if (photoList.isNotEmpty()) photoList.size else 1
    }

    fun loadNewData(newPhotos: List<Photo>) {

        photoList = newPhotos

        notifyDataSetChanged()

    }

    fun getPhoto(position: Int) : Photo? {

        return if (photoList.isNotEmpty()) photoList[position] else null

    }

    override fun onBindViewHolder(holder: FlickrImageViewHolder, position: Int) {

        // called by the layout manager when it wants new data in an existing view

        if (photoList.isEmpty()) {

            holder.thumbNail.setImageResource(R.drawable.placeholder)

            holder.title.setText(R.string.empty_photo)

        } else {

            val photoItem =  photoList[position]
            Log.d(TAG, ".onBindViewHolder: ${photoItem.title} --> $position")
            Picasso.with(holder.thumbNail.context).load(photoItem.image)
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(holder.thumbNail)

            holder.title.text = photoItem.title


        }



    }
}