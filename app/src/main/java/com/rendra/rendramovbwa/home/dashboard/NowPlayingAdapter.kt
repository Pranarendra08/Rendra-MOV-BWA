package com.rendra.rendramovbwa.home.dashboard

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rendra.rendramovbwa.model.Film
import java.util.*

class NowPlayingAdapter(private var data: List<Film>,
                        private val listener:(Film) -> Unit)
    : RecyclerView.Adapter<NowPlayingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NowPlayingAdapter.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: NowPlayingAdapter.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

}
