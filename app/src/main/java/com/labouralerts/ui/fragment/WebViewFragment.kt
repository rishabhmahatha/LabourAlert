package com.labouralerts.ui.fragment

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.labouralerts.R
import com.labouralerts.utils.Constants
import kotlinx.android.synthetic.main.fragment_web_view.*

class WebViewFragment : BaseFragments() {

    override fun defineLayoutResource(): Int {
        return R.layout.fragment_web_view
    }

    override fun initializeComponent(view: View) {
        if (arguments != null && arguments!!.containsKey(Constants.WEB_VIEW_URL)) {
//            loadPrivacyPolicyView(arguments!!.getString(Constants.WEB_VIEW_URL)!!)
            loadPrivacyPolicyView("https://www.google.co.in")
        }

    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun loadPrivacyPolicyView(url: String) {
        fragment_web_view_webView.webViewClient = ViewClient()
        fragment_web_view_webView.settings.loadsImagesAutomatically = true
        fragment_web_view_webView.settings.javaScriptEnabled = true
        fragment_web_view_webView.settings.setAppCacheEnabled(false)
        fragment_web_view_webView.clearHistory()
        fragment_web_view_webView.clearCache(true)
        fragment_web_view_webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        fragment_web_view_webView.loadUrl(url)
    }


    inner class ViewClient : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
//            fragment_web_view_pbProgress.visibility = View.VISIBLE
        }

        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            view!!.loadUrl(request!!.url.toString())
            return super.shouldOverrideUrlLoading(view, request)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
//            if (isAdded)
//                fragment_web_view_pbProgress.visibility = View.GONE
        }
    }


}