package com.rendra.rendramovbwa.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.rendra.rendramovbwa.R
import com.rendra.rendramovbwa.model.Checkout
import com.rendra.rendramovbwa.model.Film
import kotlinx.android.synthetic.main.activity_pilih_bangku.*

class CheckoutActivity : AppCompatActivity() {

    private var dataList = ArrayList<Checkout>()
    private var total: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
    }

}