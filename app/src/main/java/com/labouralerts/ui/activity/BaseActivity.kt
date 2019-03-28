package com.labouralerts.ui.activity

import android.os.Bundle
import android.os.SystemClock
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.labouralerts.utils.Utils
import com.labouralerts.LabourAlert
import com.labouralerts.utils.Constants


abstract class BaseActivity : AppCompatActivity(), View.OnClickListener {
    protected abstract fun defineLayoutResource(): Int

    protected abstract fun initializeComponent() //to initialize the activity components



//    protected abstract fun initToolbar()

    private var lastClickedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(defineLayoutResource())

//        initToolbar()
        initializeComponent()
    }

    /**
     * Method to get fragment manger.
     */
    fun getLocalFragmentManager(): FragmentManager {
        return this.supportFragmentManager
    }

    /**
     * Adds the Fragment into layout container
     *
     * @param fragmentContainerResourceId Resource id of the layout in which Fragment will be added
     * @param currentFragment             Current loaded Fragment to be hide
     * @param nextFragment                New Fragment to be loaded into fragmentContainerResourceId
     * @param commitAllowingStateLoss     true if commitAllowingStateLoss is needed
     * @return true if new Fragment added successfully into container, false otherwise
     * @throws IllegalStateException Exception if Fragment transaction is invalid
     */
    fun addFragment(container: Int, currentFragment: Fragment, nextFragment: Fragment, commitAllowingStateLoss: Boolean) {

        val fragmentTransaction = getLocalFragmentManager().beginTransaction()
        fragmentTransaction.apply {
            this.add(container, nextFragment, nextFragment.javaClass.simpleName)
            this.addToBackStack(nextFragment.javaClass.simpleName)

            val parentFragment = currentFragment.parentFragment
            this.hide(parentFragment ?: currentFragment)

            if (!commitAllowingStateLoss) this.commit()
            else this.commitAllowingStateLoss()
        }
    }

    fun addFragmentTmp(container: Int, nextFragment: Fragment, commitAllowingStateLoss: Boolean) {

        val fragmentTransaction = getLocalFragmentManager().beginTransaction()
        fragmentTransaction.apply {
            this.add(container, nextFragment, nextFragment.javaClass.simpleName)
            this.addToBackStack(nextFragment.javaClass.simpleName)


            if (!commitAllowingStateLoss) this.commit()
            else this.commitAllowingStateLoss()
        }
    }

    fun replaceFragment(fragmentContainerResourceId: Int, nextFragment: Fragment?, commitAllowingStateLoss: Boolean): Boolean {
        if (nextFragment == null || supportFragmentManager == null) {
            return false
        }
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(fragmentContainerResourceId, nextFragment, nextFragment.javaClass.simpleName)
        if (!commitAllowingStateLoss) {
            fragmentTransaction.commit()
        } else {
            fragmentTransaction.commitAllowingStateLoss()
        }
        return true
    }


    override fun onClick(view: View) {
        Utils.hideSoftKeyBoard(LabourAlert.instance!!, view)
        /*
         * Logic to Prevent the Launch of the Fragment Twice if User makes
         * the Tap(Click) very Fast.
         */
        if (SystemClock.elapsedRealtime() - lastClickedTime < Constants.MAX_CLICK_INTERVAL) {
            return
        }
        lastClickedTime = SystemClock.elapsedRealtime()
    }
}