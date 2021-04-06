package com.hm.webviewdemo

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.ViewTreeObserver.OnScrollChangedListener
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val WEB_URL: String= "https://www.google.com"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         setUpWebView();
         clickListeners();
    }

    /**
     *  click listeners
     */
    private fun clickListeners() {

        btnPrevious.setOnClickListener {

            if (webView.canGoBack()) {
                webView.goBack()
            }
        }

        btnNext.setOnClickListener {

            if (webView.canGoForward()) {
                webView.goForward()
            }
        }

        btnRefresh.setOnClickListener {
           webView.reload()
        }

        btnClose.setOnClickListener {
           webView.stopLoading()
        }
    }

    /**
     *  setup webview
     */
    private fun setUpWebView() {


        webView.viewTreeObserver.addOnScrollChangedListener(OnScrollChangedListener {
            Log.e(" scroll ", webView.scrollY.toString());
            if (webView.scrollY > 0 && linearBottom.visibility == View.VISIBLE) {
                linearBottom.visibility = View.GONE;
            } else if (webView.scrollY <= 0 && linearBottom.visibility != View.VISIBLE) {
                linearBottom.visibility = View.VISIBLE;
            }
        })

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url!!)
                progressBar.visibility  = View.VISIBLE
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                progressBar.visibility  = View.GONE
                super.onPageFinished(view, url)
            }
        }

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.loadUrl(WEB_URL)

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}