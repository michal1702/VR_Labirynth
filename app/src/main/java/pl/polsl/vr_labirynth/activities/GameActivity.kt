package pl.polsl.vr_labirynth.activities

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.webkit.WebChromeClient
import androidx.appcompat.app.AppCompatActivity
import pl.polsl.vr_labirynth.R
import pl.polsl.vr_labirynth.databinding.ActivityGameBinding
import pl.polsl.vr_labirynth.interfaces.IHideActionBar

class GameActivity : AppCompatActivity(), IHideActionBar {

    private lateinit var binding: ActivityGameBinding
    override val activityWindow: Window by lazy { this.window }
    private lateinit var dialog:Dialog

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dialog = Dialog(this)
        hideActionBar()

        val webSettings = binding.gameWebView.settings
        webSettings.javaScriptEnabled = true
        binding.gameWebView.webChromeClient = WebChromeClient()
        binding.gameWebView.loadUrl("file:///android_asset/index.html")

        var score = 25 //temporary

        //For test purposes only
        binding.testButton.setOnClickListener{
            //val popupIntent = Intent(this, ScorePopupActivity::class.java)
            val popupIntent = Intent(this, SavePopupActivity::class.java)
            popupIntent.putExtra("score", score)
            startActivity(popupIntent)

        }
    }
}