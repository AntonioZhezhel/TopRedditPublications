package com.example.topredditpublications

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_dialog.view.*
import android.os.Bundle as Bundle1


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private val RECORD_REQUEST_CODE = 101
    private lateinit var viewModel: ListViewModel

    private var adapter = PostAdapter {
         val mDialog = LayoutInflater.from(this).inflate(R.layout.custom_dialog,null)
         val dialog = AlertDialog.Builder(this).setView(mDialog)
         val alert = dialog.show()

         alert.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
         Picasso.get().load(it.url).into(mDialog.ivImageURL)

         android.os.Handler().postDelayed({
             if (mDialog.ivImageURL.drawable == null) {
                 alert.dismiss()
             } else {
                 mDialog.bnSave.setOnClickListener {
                     val permission = ContextCompat.checkSelfPermission(
                         this@MainActivity,
                         android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                     )

                     if (permission != PackageManager.PERMISSION_GRANTED) {
                         makeRequest()
                     } else {
                         val myDrawable = mDialog.ivImageURL.drawable
                         val bitmap: Bitmap = (myDrawable as BitmapDrawable).bitmap

                         val uri: Uri = saveImageToExternalStorage(bitmap, "saveImage")
                         toast("Image saved successful.$uri")
                         alert.dismiss()
                     }
                 }

                 mDialog.bnCancel.setOnClickListener {
                     alert.dismiss()
                 }

             }
         },500)
     }

    private fun saveImageToExternalStorage(bitmap: Bitmap, save: String): Uri {

        val savedImageURL = MediaStore.Images.Media.insertImage(
            contentResolver,
            bitmap,
            save,
            "Image of $save"
        )
        return Uri.parse(savedImageURL)
    }

    override fun onCreate(savedInstanceState: Bundle1?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        setupPermissions()
        initList()
    }
    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(this,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(this,
            arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
            RECORD_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            RECORD_REQUEST_CODE -> {

                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                } else {
                }
            }
        }
    }



    private fun initList(){
        rvListPost.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        rvListPost.adapter = adapter
        viewModel.postList.observe(this, Observer { adapter.submitList(it) })
    }


}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}