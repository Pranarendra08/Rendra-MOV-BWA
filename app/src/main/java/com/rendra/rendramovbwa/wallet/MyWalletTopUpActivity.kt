package com.rendra.rendramovbwa.wallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rendra.rendramovbwa.R
import kotlinx.android.synthetic.main.activity_my_wallet_top_up.*

class MyWalletTopUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_wallet_top_up)

        btn_top_up.setOnClickListener {
            startActivity(Intent(this, MyWalletSuccessActivity::class.java))
        }
    }
}