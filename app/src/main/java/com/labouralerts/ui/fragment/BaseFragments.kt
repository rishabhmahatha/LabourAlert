package com.labouralerts.ui.fragment

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.SystemClock
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.labouralerts.ui.activity.BaseActivity
import com.labouralerts.utils.Constants
import com.labouralerts.utils.Utils
import com.labouralerts.utils.imageloader.ImageLoader
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * BaseFragments class created
 */
public abstract class BaseFragments : Fragment(), View.OnClickListener {
    protected abstract fun defineLayoutResource(): Int

    protected abstract fun initializeComponent(view: View) //to initialize the fragment components

    private var lastClickedTime: Long = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(defineLayoutResource(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeComponent(view)
    }

    /**
     * Adds the Fragment into layout container.
     *
     * @param container               Resource id of the layout in which Fragment will be added
     * @param currentFragment         Current loaded Fragment to be hide
     * @param nextFragment            New Fragment to be loaded into container
     * @param commitAllowingStateLoss true if commitAllowingStateLoss is needed
     * @return true if new Fragment added successfully into container, false otherwise
     * @throws ClassCastException    Throws exception if getActivity() is not an instance of BaseActivity
     * @throws IllegalStateException Exception if Fragment transaction is invalid
     */
    protected fun addFragment(container: Int, currentFragment: Fragment, nextFragment: Fragment, commitAllowingStateLoss: Boolean) {
        activity?.let {
            if (activity is BaseActivity) {
                (activity as BaseActivity).addFragment(container, currentFragment, nextFragment, commitAllowingStateLoss)
            } else {
                throw ClassCastException(BaseActivity::class.java.name + " can not be cast into " + activity!!.javaClass.name)
            }
        }
    }

    /**
     * Replaces the Fragment into layout container.
     *
     * @param container               Resource id of the layout in which Fragment will be added
     * @param nextFragment            New Fragment to be loaded into container
     * @param commitAllowingStateLoss true if commitAllowingStateLoss is needed
     * @return true if new Fragment added successfully into container, false otherwise
     * @throws ClassCastException    Throws exception if getActivity() is not an instance of BaseActivity
     * @throws IllegalStateException Exception if Fragment transaction is invalid
     */
    protected fun replaceFragment(container: Int, nextFragment: Fragment, commitAllowingStateLoss: Boolean) {
        activity?.let {
            if (activity is BaseActivity) {
                (activity as BaseActivity).replaceFragment(container, nextFragment, commitAllowingStateLoss)
            } else {
                throw ClassCastException(BaseActivity::class.java.name + " can not be cast into " + activity!!.javaClass.name)
            }
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
//            initToolbar()
        }
    }


    override fun onClick(view: View) {
        Utils.hideSoftKeyBoard(view.context, view)
        /*
         * Logic to Prevent the Launch of the Fragment Twice if User makes
         * the Tap(Click) very Fast.
         */
        if (SystemClock.elapsedRealtime() - lastClickedTime < Constants.MAX_CLICK_INTERVAL) {
            return
        }
        lastClickedTime = SystemClock.elapsedRealtime()
    }


    fun onCaptureImageResult(data: Intent, ivProfileImage: ImageView, path: String, imageName: String): String {

        val bitmapData = "data"
        Log.d("capturedImagePath", path)

        val thumbnail = data.extras!!.get(bitmapData) as Bitmap

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

        val uri = Uri.fromFile(destinationFile)


//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
//            uri = Uri.fromFile(destinationFile)
//        } else {
//            uri = FileProvider.getUriForFile(this.activity!!, BuildConfig.APPLICATION_ID + ".provider", destinationFile)
//        }

        ImageLoader.loadImageWithRoundCorner(activity, ivProfileImage, uri.toString())
        return uri.toString()

    }
}