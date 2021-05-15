package pl.polsl.vr_labirynth.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Window
import android.webkit.WebChromeClient
import androidx.appcompat.app.AppCompatActivity
import pl.polsl.vr_labirynth.databinding.ActivityGameBinding
import pl.polsl.vr_labirynth.interfaces.IHideActionBar

class GameActivity : AppCompatActivity(), IHideActionBar {

    private lateinit var binding: ActivityGameBinding
    override val activityWindow: Window by lazy { this.window }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hideActionBar()


        val webSettings = binding.gameWebView.settings
        webSettings.javaScriptEnabled = true
        binding.gameWebView.webChromeClient = WebChromeClient()
        binding.gameWebView.loadUrl("file:///android_asset/index.html")

        var score = 25 //temporary

        /*binding.testButton.setOnClickListener{
            val popupIntent = Intent(this, SavePopupActivity::class.java)
            popupIntent.putExtra("score", score)
            startActivity(popupIntent)
        }*/
    }
}