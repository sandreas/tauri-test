package com.plugin.mediabutton

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.view.KeyEvent
import android.webkit.WebMessage
import android.webkit.WebView
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import app.tauri.annotation.Command
import app.tauri.annotation.InvokeArg
import app.tauri.annotation.TauriPlugin
import app.tauri.plugin.JSObject
import app.tauri.plugin.Plugin
import app.tauri.plugin.Invoke

@InvokeArg
class PingArgs {
  var value: String? = null
}

@TauriPlugin
class ExamplePlugin(private val activity: Activity): Plugin(activity) {
    private var timeout: Int? = 3000
    private lateinit var webView: WebView

    override fun load(webView: WebView) {
        super.load(webView)
        this.webView = webView

        val data = "message from kotlin"
        val message = WebMessage(data)
        val uri = webView.url?.toUri() ?: "".toUri();
        this.webView.postWebMessage(message, uri);

    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onNewIntent(intent: Intent) {
        if (Intent.ACTION_MEDIA_BUTTON != intent.action) {
            return
        }
        val keyEvent = intent.getParcelableExtra<KeyEvent>(Intent.EXTRA_KEY_EVENT) ?: return

        val data = "{" +
                "\"keyCode\":\""+keyEvent.keyCode+"\"" +
                ",\"action\":\""+keyEvent.action+"\"" +
                ",\"repeatCount\":\""+keyEvent.repeatCount+"\"" +
                ",\"isLongPress\":\""+keyEvent.isLongPress+"\"" +
                "}"
        val message = WebMessage(data)
        val uri = webView.url?.toUri() ?: "".toUri();
        this.webView.postWebMessage(message, uri);
    }
}


/*
// original plugin code from here
package com.plugin.mediabutton

import android.app.Activity
import app.tauri.annotation.Command
import app.tauri.annotation.InvokeArg
import app.tauri.annotation.TauriPlugin
import app.tauri.plugin.JSObject
import app.tauri.plugin.Plugin
import app.tauri.plugin.Invoke

@InvokeArg
class PingArgs {
  var value: String? = null
}

@TauriPlugin
class ExamplePlugin(private val activity: Activity): Plugin(activity) {
    private val implementation = Example()

    @Command
    fun ping(invoke: Invoke) {
        val args = invoke.parseArgs(PingArgs::class.java)

        val ret = JSObject()
        ret.put("value", implementation.pong(args.value ?: "default value :("))
        invoke.resolve(ret)
    }
}
*/