package pl.polsl.vr_labirynth

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import pl.polsl.vr_labirynth.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        val webSettings = binding.gameWebView.settings
        webSettings.javaScriptEnabled = true
        binding.gameWebView.webChromeClient = WebChromeClient()
        binding.gameWebView.loadUrl("file:///android_asset/index.html")
    }
}