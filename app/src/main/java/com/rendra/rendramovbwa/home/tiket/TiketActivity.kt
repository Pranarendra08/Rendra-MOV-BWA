package com.rendra.rendramovbwa.home.tiket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.rendra.rendramovbwa.R
import com.rendra.rendramovbwa.model.Checkout
import com.rendra.rendramovbwa.model.Film
import com.rendra.rendramovbwa.utils.Preferences
import kotlinx.android.synthetic.main.activity_tiket.*

class TiketActivity : AppCompatActivity() {

    private var dataList = ArrayList<Checkout>()
    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tiket)

        preferences = Preferences(this)
//        dataList = intent.getSerializableExtra("data") as ArrayList<Checkout>
        var data = intent.getParcelableExtra<Film>("data")

        tv_title.text = data!!.judul
        tv_genre.text = data.genre
        tv_rate.text = data.rating

        Glide.with(this)
            .load(data.poster)
            .into(iv_poster_image)

        rv_checkout.layoutManager = LinearLayoutManager(this)
        rv_checkout.adapter = TiketAdapter(dataList) {

        }
    }
}