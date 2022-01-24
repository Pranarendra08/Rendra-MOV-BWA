package com.rendra.rendramovbwa.sign.signup

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.rendra.rendramovbwa.home.HomeActivity
import com.rendra.rendramovbwa.R
import com.rendra.rendramovbwa.sign.signin.User
import com.rendra.rendramovbwa.utils.Preferences
import kotlinx.android.synthetic.main.activity_sign_up_photoscreen.*
import java.util.*

class SignUpPhotoScreenActivity : AppCompatActivity(), PermissionListener{

    val REQUEST_IMAGE_CAPTURE = 1
    var statusAdd:Boolean = false
    lateinit var filePath:Uri

    lateinit var storage: FirebaseStorage
    lateinit var storageReferensi: StorageReference
    lateinit var preferences: Preferences

    lateinit var user : User
    private lateinit var mFirebaseDatabase: DatabaseReference
    private lateinit var mFirebaseInstance: FirebaseDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_photoscreen)

        preferences = Preferences(this)
        storage = FirebaseStorage.getInstance()
        storageReferensi = storage.getReference()

        mFirebaseInstance = FirebaseDatabase.getInstance("https://rendra-mov-bwa-default-rtdb.asia-southeast1.firebasedatabase.app/")
        mFirebaseDatabase = mFirebaseInstance.getReference("User")

        user = intent.getParcelableExtra("data")!!
        tv_hello.text = "Selamat Datang\n" + user.nama

        iv_add.setOnClickListener {
            if (statusAdd) {
                statusAdd = false
                btn_save.visibility = View.VISIBLE
                iv_add.setImageResource(R.drawable.ic_btn_upload)
                iv_profile.setImageResource(R.drawable.user_pic)
            } else {
//                Dexter.withActivity(this)
//                    .withPermission(android.Manifest.permission.CAMERA)
//                    .withListener(this)
//                    .check()
                ImagePicker.with(this)
                    .cameraOnly()	//User can only capture image using Camera
                    .start()
            }
        }

        btn_home.setOnClickListener {
            finishAffinity()

            var goHome = Intent(this@SignUpPhotoScreenActivity, HomeActivity::class.java)
            startActivity(goHome)
        }

        btn_save.setOnClickListener {
            if (filePath != null) {
                var progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Uploading...")
                progressDialog.show()

                var ref = storageReferensi.child("images/" + UUID.randomUUID().toString())
                ref.putFile(filePath)
                    .addOnSuccessListener {
                        progressDialog.dismiss()
                        Toast.makeText(this@SignUpPhotoScreenActivity, "Uploaded", Toast.LENGTH_LONG).show()

                        ref.downloadUrl.addOnSuccessListener {
                            saveToFirebase(it.toString())
                        }

                        finishAffinity()
                        var goHome = Intent(this@SignUpPhotoScreenActivity, HomeActivity::class.java)
                        startActivity(goHome)
                    }
                    .addOnFailureListener {
                        progressDialog.dismiss()
                        Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show()
                    }
                    .addOnProgressListener {
                        taskSnapshot -> var progress = 100.0 + taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                        progressDialog.setMessage("Upload " + progress.toInt() + " %")
                    }
            } /*else {

            }*/
        }

    }

    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
            takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        Toast.makeText(this, "Anda tidak bisa menambahkan photo profile", Toast.LENGTH_LONG).show()
    }

    override fun onPermissionRationaleShouldBeShown(
        permission: PermissionRequest?,
        token: PermissionToken?
    ) {

    }

    override fun onBackPressed() {
        Toast.makeText(this, "Tergesah? klik tombol upload nanti aja", Toast.LENGTH_LONG).show()
    }

//    @SuppressLint("MissingSuperCall")
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
//            var bitmap = data?.extras?.get("data") as Bitmap
//            statusAdd = true
//
//            filePath = data.getData()!!
//            Glide.with(this)
//                .load(bitmap)
//                .apply(RequestOptions.circleCropTransform())
//                .into(iv_profile)
//
//            btn_save.visibility = View.VISIBLE
//            iv_add.setImageResource(R.drawable.ic_btn_delete)
//        }
//    }

    //ini adalah update bug kamera
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            statusAdd = true
            filePath = data?.data!!

            Glide.with(this)
                .load(filePath)
                .apply(RequestOptions.circleCropTransform())
                .into(iv_profile)

            btn_save.visibility = View.VISIBLE
            iv_add.setImageResource(R.drawable.ic_btn_delete)

            // Use Uri object instead of File to avoid storage permissions
//            imgProfile.setImageURI(fileUri)
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveToFirebase(url: String) {
        mFirebaseDatabase.child(user.username!!).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                user.url = url
                mFirebaseDatabase.child(user.username!!).setValue(user)

                preferences.setValue("nama", user.nama.toString())
                preferences.setValue("user", user.username.toString())
                preferences.setValue("saldo", "")
                preferences.setValue("url", "")
                preferences.setValue("email", user.email.toString())
                preferences.setValue("status", "1")

                finishAffinity()
                val goHome = Intent(this@SignUpPhotoScreenActivity, HomeActivity::class.java)
                startActivity(goHome)
            }

            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(this@SignUpPhotoScreenActivity, ""+p0.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}