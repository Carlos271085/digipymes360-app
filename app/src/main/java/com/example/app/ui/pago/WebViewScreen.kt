package com.example.app.ui.pago

import android.annotation.SuppressLint
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen(
    url: String
) {
    // Necesitas una referencia al WebView para actualizarlo desde LaunchedEffect
    var webView: WebView? = null

    LaunchedEffect(url) {
        webView?.loadUrl(url)
    }

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.useWideViewPort = true
                settings.loadWithOverviewMode = true

                webViewClient = WebViewClient()
                loadUrl(url)

                webView = this
            }
        },
        update = { view ->
            webView = view
        }
    )
}

