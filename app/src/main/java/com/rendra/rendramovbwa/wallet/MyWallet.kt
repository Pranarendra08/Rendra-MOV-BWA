package com.rendra.rendramovbwa.wallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.rendra.rendramovbwa.R
import com.rendra.rendramovbwa.model.Wallet
import com.rendra.rendramovbwa.utils.Preferences
import kotlinx.android.synthetic.main.activity_my_wallet.*
import kotlinx.android.synthetic.main.activity_my_wallet.tv_saldo
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class MyWallet : AppCompatActivity() {

    private val dataList = ArrayList<Wallet>()

    private lateinit var preferences: Preferences
    private lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_wallet)

        preferences = Preferences(this)
        mDatabase = FirebaseDatabase.getInstance("https://rendra-mov-bwa-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("User")

        if (preferences.getValue("saldo").equals("")) {
            tv_saldo.text = "Rp0"
        } else {
            currency(preferences.getValue("saldo")!!.toDouble(), tv_saldo)
        }

        dataList.add(
            Wallet (
                "Avengers: Infinity War",
                "Sabtu 12 Jan, 2020",
                70000.0,
                "0"
            )
        )

        dataList.add(
            Wallet (
                "Top Up",
                "Sabtu 13 Jan, 2020",
                70000.0,
                "1"
            )
        )

        dataList.add(
            Wallet (
                "Avengers: Infinity War",
                "Sabtu 15 Jan, 2020",
                70000.0,
                "0"
            )
        )

        rv_transaksi.layoutManager = LinearLayoutManager(this)
        rv_transaksi.adapter = WalletAdapter(dataList) {

        }

        btn_top_up.setOnClickListener {
            startActivity(Intent(this, MyWalletTopUpActivity::class.java))
        }
    }

    private fun currency(harga: Double, textview: TextView) {
        val localeID = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
        textview.setText(formatRupiah.format(harga))
    }
}