package com.rendra.rendramovbwa.home.setting

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rendra.rendramovbwa.EditProfile
import com.rendra.rendramovbwa.R
import com.rendra.rendramovbwa.utils.Preferences
import com.rendra.rendramovbwa.wallet.MyWallet
import kotlinx.android.synthetic.main.fragment_setting.*

class SettingFragment : Fragment() {

    lateinit var preferences: Preferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preferences = Preferences(context!!)

        tv_nama.text = preferences.getValue("nama")
        tv_email.text = preferences.getValue("email")

        Glide.with(this)
            .load(preferences.getValue("url"))
            .apply(RequestOptions.circleCropTransform())
            .into(iv_profile)

        tv_my_wallet.setOnClickListener {
            startActivity(Intent(activity, MyWallet::class.java))
        }

        tv_edit_profile.setOnClickListener {
            startActivity(Intent(activity, EditProfile::class.java))
        }
    }


}