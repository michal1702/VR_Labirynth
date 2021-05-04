package pl.polsl.vr_labirynth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import pl.polsl.vr_labirynth.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        binding.gameWebView.loadUrl("file:///android_asset/index.html")
    }
}