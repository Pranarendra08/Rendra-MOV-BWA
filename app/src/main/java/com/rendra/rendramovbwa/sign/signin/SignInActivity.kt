package com.rendra.rendramovbwa.sign.signin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.*
import com.rendra.rendramovbwa.R
import kotlinx.android.synthetic.main.activity_sign_in.*

import com.rendra.rendramovbwa.HomeActivity
import com.rendra.rendramovbwa.sign.signup.SignUpActivity
import com.rendra.rendramovbwa.utils.Preferences


class SignInActivity : AppCompatActivity() {

    lateinit var  iUsername:String
    lateinit var  iPassword:String

    lateinit var mDatabase: DatabaseReference
    lateinit var preference: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        mDatabase = FirebaseDatabase.getInstance("https://rendra-mov-bwa-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("User")
        preference = Preferences(this)

        preference.setValue("onboarding", "1")
        if (preference.getValue("status").equals("1")) {
            finishAffinity()

            var goHome = Intent(this@SignInActivity, HomeActivity::class.java)
            startActivity(goHome)
        }

        btn_home.setOnClickListener {
            iUsername = et_username.text.toString()
            iPassword = et_password.text.toString()

            if (iUsername.equals("")) {
                et_username.error = "Silahkan tulis username Anda"
                et_username.requestFocus()
            } else if (iPassword.equals("")) {
                et_password.error = "Silahkan tulis password Anda"
                et_password.requestFocus()
            } else {
                pushLogin(iUsername, iPassword)
            }
        }
        btn_daftar.setOnClickListener {
            var goSignUp = Intent(this@SignInActivity,
                SignUpActivity::class.java)
            startActivity(goSignUp)
        }
    }

    private fun pushLogin(iUsername: String, iPassword: String) {
        mDatabase.child(iUsername).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val user = dataSnapshot.getValue(User::class.java)
                if (user == null) {
                    Toast.makeText(this@SignInActivity, "Username tidak ditemukan",
                        Toast.LENGTH_LONG).show()
                } else {

                    if (user.password.equals(iPassword)) {

                        preference.setValue("nama", user.nama.toString())
                        preference.setValue("username", user.username.toString())
                        preference.setValue("url", user.url.toString())
                        preference.setValue("email", user.email.toString())
                        preference.setValue("saldo", user.saldo.toString())
                        preference.setValue("status", "1")

                        var intent = Intent(this@SignInActivity, HomeActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@SignInActivity, "Password anda salah",
                            Toast.LENGTH_LONG).show()
                    }

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@SignInActivity, databaseError.message,
                    Toast.LENGTH_LONG).show()
            }
        })
    }
}