package com.rendra.rendramovbwa.wallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.rendra.rendramovbwa.R
import com.rendra.rendramovbwa.utils.Preferences
import kotlinx.android.synthetic.main.activity_my_wallet.*
import kotlinx.android.synthetic.main.activity_my_wallet_top_up.*
import kotlinx.android.synthetic.main.activity_my_wallet_top_up.btn_top_up
import kotlinx.android.synthetic.main.activity_my_wallet_top_up.tv_saldo
import kotlinx.android.synthetic.main.activity_tiket.*
import java.text.NumberFormat
import java.util.*

class MyWalletTopUpActivity : AppCompatActivity() {

    private var status10K : Boolean = false
    private var status25K : Boolean = false
    private var status50K : Boolean = false
    private var status100K : Boolean = false
    private var status200K : Boolean = false
    private var status500K : Boolean = false

    private lateinit var preferences: Preferences
    private lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_wallet_top_up)

        var sCustom = et_customTopUp.text.toString()

        preferences = Preferences(this)
        mDatabase = FirebaseDatabase.getInstance("https://rendra-mov-bwa-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("User")

        if (preferences.getValue("saldo").equals("")) {
            tv_saldo.text = "Rp0"
        } else {
            currency(preferences.getValue("saldo")!!.toDouble(), tv_saldo)
        }

        btn_top_up.setOnClickListener {
            startActivity(Intent(this, MyWalletSuccessActivity::class.java))
        }

        tv_10k.setOnClickListener {
            if (status10K) {
                deSelectMoney(tv_10k)
            } else {
                selectMoney(tv_10k)
            }
        }

        tv_25k.setOnClickListener {
            if (status25K) {
                deSelectMoney(tv_25k)
            } else {
                selectMoney(tv_25k)
            }
        }

        tv_50k.setOnClickListener {
            if (status50K) {
                deSelectMoney(tv_50k)
            } else {
                selectMoney(tv_50k)
            }
        }

        tv_100k.setOnClickListener {
            if (status100K) {
                deSelectMoney(tv_100k)
            } else {
                selectMoney(tv_100k)
            }
        }

        tv_200k.setOnClickListener {
            if (status200K) {
                deSelectMoney(tv_200k)
            } else {
                selectMoney(tv_200k)
            }
        }

        tv_500k.setOnClickListener {
            if (status500K) {
                deSelectMoney(tv_500k)
            } else {
                selectMoney(tv_500k)
            }
        }

        et_customTopUp.setOnClickListener {
            var sCustom = et_customTopUp.text.toString()
            if (sCustom.equals("0") || sCustom.equals("")) {
                btn_top_up.visibility = View.INVISIBLE
            } else {
                btn_top_up.visibility = View.VISIBLE
            }

        }

    }

    private fun selectMoney(textView: TextView) {
        textView.setTextColor(resources.getColor(R.color.colorPink))
        textView.setBackgroundResource(R.drawable.shape_line_pink)
        when (textView) {
            tv_10k -> status10K = true
            tv_25k -> status25K = true
            tv_50k -> status50K = true
            tv_100k -> status100K = true
            tv_200k -> status200K = true
            tv_500k -> status500K = true
        }


        btn_top_up.visibility = View.VISIBLE
    }

    private fun deSelectMoney(textView: TextView) {
        textView.setTextColor(resources.getColor(R.color.white))
        textView.setBackgroundResource(R.drawable.shape_line_white)
        when (textView) {
            tv_10k -> status10K = false
            tv_25k -> status25K = false
            tv_50k -> status50K = false
            tv_100k -> status100K = false
            tv_200k -> status200K = false
            tv_500k -> status500K = false
        }

        btn_top_up.visibility = View.INVISIBLE
    }

    private fun currency(harga: Double, textview: TextView) {
        val localeID = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
        textview.setText(formatRupiah.format(harga))
    }
}