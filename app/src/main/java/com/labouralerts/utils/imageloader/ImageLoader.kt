package com.labouralerts.utils.imageloader

import android.content.Context
import android.graphics.Bitmap
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.labouralerts.R

import java.io.File
import java.util.concurrent.ExecutionException


 class ImageLoader {
    companion object {
        fun loadImage(context: Context, imageView: ImageView, imageURL: String) {
//            TODO: add placeholder and error image accordingly
            Glide.with(context).asBitmap().load(imageURL)
//                    .placeholder(R.drawable.placeholder_default)
//                    .error(R.drawable.placeholder_default)
                .into(imageView)


        }

//
//        fun loadRoundedImage(context: Context, imageView: ImageView, imageUrl: String, placeHolderRes: Int) {
//            Glide.with(context).asBitmap().load(imageUrl).apply(RequestOptions().dontAnimate().dontTransform().placeholder(placeHolderRes).centerCrop()).into<>(object : BitmapImageViewTarget(imageView) {
//                override fun setResource(resource: Bitmap?) {
//                    val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.resources, resource)
//                    circularBitmapDrawable.isCircular = true
//                    imageView.setImageDrawable(circularBitmapDrawable)
//                }
//            })
//        }

        fun loadImageWithRoundCorner(context: Context?, imageView: ImageView, imageUrl: String) {

            if (context == null) {
                return
            }

            Glide.with(context).asBitmap().load(imageUrl).apply(RequestOptions().dontAnimate().dontTransform().centerCrop()).into<BitmapImageViewTarget>(object : BitmapImageViewTarget(imageView) {
                override fun setResource(resource: Bitmap?) {
                    val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.resources, resource)
                    circularBitmapDrawable.cornerRadius = context.resources.getDimension(R.dimen._6sdp).toInt().toFloat()
                    circularBitmapDrawable.setAntiAlias(true)
                    imageView.setImageDrawable(circularBitmapDrawable)
                }
            })
        }

        fun getCacheImage(context: Context, imageUrl: String): File? {
            var imageFile: File? = null
            try {
                imageFile = Glide.with(context).asFile().load(imageUrl).submit(com.bumptech.glide.request.target.Target.SIZE_ORIGINAL, com.bumptech.glide.request.target.Target.SIZE_ORIGINAL).get()
                //            imageFile = Glide.with(context).downloadOnly().load(imageUrl).submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } catch (e: ExecutionException) {
                e.printStackTrace()
            }

            return imageFile
        }
    }

}