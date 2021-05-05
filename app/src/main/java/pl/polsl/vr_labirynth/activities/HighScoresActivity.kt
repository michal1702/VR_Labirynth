package pl.polsl.vr_labirynth.activities

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import pl.polsl.vr_labirynth.databinding.ActivityHighScoresBinding
import pl.polsl.vr_labirynth.interfaces.IBackgroundAnimation
import pl.polsl.vr_labirynth.interfaces.ITouchAnimation

class HighScoresActivity() : AppCompatActivity(), IBackgroundAnimation, ITouchAnimation {

    override val animationBackground: Drawable by lazy { binding.highScoresLayout.background}
    private lateinit var binding: ActivityHighScoresBinding

    private val backButtonOnClickListener = View.OnClickListener { backButtonClicked() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHighScoresBinding.inflate(layoutInflater)
        setContentView(binding.root)
        animateBackground()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        binding.backButton.setOnClickListener(backButtonOnClickListener)
        imageButtonTouched(binding.backButton)
    }

    private fun backButtonClicked(){
        this.finish()
    }
}