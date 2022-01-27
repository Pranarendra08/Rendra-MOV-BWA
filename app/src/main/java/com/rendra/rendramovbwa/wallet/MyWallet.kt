package com.rendra.rendramovbwa.wallet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.rendra.rendramovbwa.R
import com.rendra.rendramovbwa.model.Wallet
import kotlinx.android.synthetic.main.activity_my_wallet.*

class MyWallet : AppCompatActivity() {

    private val dataList = ArrayList<Wallet>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_wallet)

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

        }
    }
}