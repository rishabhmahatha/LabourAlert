package com.labouralerts.utils


import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.labouralerts.R
import com.labouralerts.ui.activity.BaseActivity

import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL
import java.util.*
import java.util.regex.Pattern

object Utils {


    fun hideSoftKeyBoard(context: Context, view: View) {
        try {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun isValidPhoneNumber(phoneNumber: String): Boolean {

        return !TextUtils.isEmpty(phoneNumber) && (phoneNumber.length <=10)

    }

    fun isEmailValid(email: String): Boolean {

        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    fun isValidPassword(password: String): Boolean {

        return !TextUtils.isEmpty(password) && Pattern.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s)(?=.*[!@#$%*]).{6,20}$", password)
    }


//    fun showSnackBar(context: Context, view: View?, isError: Boolean, message: String): Snackbar? {
//
//        if (view == null) {
//            return null
//        }
//
//        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
//        val snackBarView = snackbar.view
//        val textView = snackBarView.findViewById<TextView>(android.support.design.R.id.snackbar_text)
//        if (isError) {
//            snackBarView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.white))
//            textView.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_light))
//            textView.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.ic_launcher_foreground), null, null, null)
//        } else {
//            snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
//            textView.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
//            textView.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, R.drawable.ic_launcher_foreground), null, null, null)
//        }
//        textView.compoundDrawablePadding = context.resources.getDimensionPixelOffset(R.dimen._10sdp)
//        textView.maxLines = 5
//        snackbar.show()
//        return snackbar
//    }

    fun showSnackBar(mContext: Context, view: View?, isError: Boolean, message: String) {
        mContext.let {
            val snackBar = Snackbar.make(view!!, message, Snackbar.LENGTH_LONG)
            val view1 = snackBar.view
            val textView = view1.findViewById(android.support.design.R.id.snackbar_text) as TextView
            when (mContext) {
                is BaseActivity -> {
                    if (isError) {
                        view1.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorSnackbarErrorBg))
                        textView.setTextColor(ContextCompat.getColor(mContext, R.color.colorSnackbarErrorText))
                    } else {
                        view1.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorSnackbarSuccessBg))
                        textView.setTextColor(ContextCompat.getColor(mContext, R.color.colorSnackbarSuccessText))
                    }
                }
                else -> {
                    if (isError) {
                        view1.setBackgroundColor(ContextCompat.getColor(it, R.color.colorSnackbarErrorBg))
                        textView.setTextColor(ContextCompat.getColor(it, R.color.colorSnackbarErrorText))
                    } else {
                        view1.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorSnackbarSuccessBg))
                        textView.setTextColor(ContextCompat.getColor(mContext, R.color.colorSnackbarSuccessText))
                    }
                }
            }
            snackBar.show()
        }
    }

    fun convertImageUrlToFile(imageUrl: String, path: String, imageName: String): File {

        val url = URL(imageUrl)
        val thumbnail = BitmapFactory.decodeStream(url.openConnection().getInputStream())

        val bytes = ByteArrayOutputStream()
        assert(thumbnail != null)
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val imageDir = File(path)
        if (!imageDir.exists()) {
            imageDir.mkdirs()
        }
        val destinationFile = File(path, imageName)
        val fo: FileOutputStream
        try {
            destinationFile.createNewFile()
            fo = FileOutputStream(destinationFile)
            fo.write(bytes.toByteArray())
            fo.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }


        return destinationFile
    }
}