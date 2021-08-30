package pl.polsl.vr_labirynth.activities

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.webkit.WebChromeClient
import androidx.appcompat.app.AppCompatActivity
import pl.polsl.vr_labirynth.databinding.ActivityGameBinding
import pl.polsl.vr_labirynth.interfaces.IHideActionBar

class GameActivity : AppCompatActivity(), IHideActionBar {

    private lateinit var binding: ActivityGameBinding
    override val activityWindow: Window by lazy { this.window }
    private lateinit var dialog:Dialog
    private var exitGame = false;

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
    }

    override fun onBackPressed() {
        var score = 25 //temporary

        val popupIntent = Intent(this, SavePopupActivity::class.java)
        popupIntent.putExtra("score", score)
        startActivityForResult(popupIntent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK){
            val result = data?.getBooleanExtra("exitGame", false)
            if(result != null && result == true){
                finish()
            }
        }
    }
}