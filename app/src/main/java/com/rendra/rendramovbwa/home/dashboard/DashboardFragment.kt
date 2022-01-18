package com.rendra.rendramovbwa.home.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.*
import com.rendra.rendramovbwa.R
import com.rendra.rendramovbwa.model.Film
import com.rendra.rendramovbwa.utils.Preferences
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class DashboardFragment : Fragment() {

    private lateinit var preferences: Preferences
    private lateinit var mDatabase: DatabaseReference

    private var dataList = ArrayList<Film>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preferences = Preferences(requireActivity().applicationContext)
        mDatabase = FirebaseDatabase.getInstance("https://rendra-mov-bwa-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Film")

        tv_nama.setText(preferences.getValue("nama"))
        if (preferences.getValue("saldo").equals("")) {
            currency(preferences.getValue("saldo")!!.toDouble(), tv_saldo)
        }

        Glide.with(this)
            .load(preferences.getValue("url"))
            .apply(RequestOptions.circleCropTransform())
            .into(iv_profile)

        rv_now_playing.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_coming_soon.layoutManager = LinearLayoutManager(context)
        getData()

    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataList.clear()
                for (getdataSnapshot in dataSnapshot.children) {
                    var film = getdataSnapshot.getValue(Film::class.java)
                    dataList.add(film!!)
                }

                rv_now_playing.adapter = NowPlayingAdapter(dataList) {

                }

                rv_coming_soon.adapter = ComingSoonAdapter(dataList) {

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(context, ""+databaseError.message, Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun currency(harga : Double, textview: TextView) {
        val localID = Locale("in", "ID")
        val format = NumberFormat.getCurrencyInstance(localID)
        textView.setText(format.format(harga))
    }

}