package pl.polsl.vr_labirynth.activities

import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import pl.polsl.vr_labirynth.databinding.ActivityLoadGameBinding
import pl.polsl.vr_labirynth.interfaces.IBackgroundAnimation
import pl.polsl.vr_labirynth.interfaces.ITouchAnimation

class LoadGameActivity() : AppCompatActivity(), IBackgroundAnimation, ITouchAnimation {

    override val animationBackground: Drawable by lazy { binding.loadGameLayout.background }
    private lateinit var binding:ActivityLoadGameBinding

    private val backButtonOnClickListener = View.OnClickListener { backButtonClicked() }
    private val loadButton1ClickListener = View.OnClickListener { loadButton1Clicker() }
    private val loadButton2ClickListener = View.OnClickListener { loadButton2Clicker() }
    private val loadButton3ClickListener = View.OnClickListener { loadButton3Clicker() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
            window.setDecorFitsSystemWindows(false)
        else
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        binding = ActivityLoadGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageButtonTouched(binding.backButton)
        buttonTouched(binding.loadButton1)
        buttonTouched(binding.loadButton2)
        buttonTouched(binding.loadButton3)
        binding.backButton.setOnClickListener(backButtonOnClickListener)
        binding.loadButton1.setOnClickListener(loadButton1ClickListener)
        binding.loadButton2.setOnClickListener(loadButton2ClickListener)
        binding.loadButton3.setOnClickListener(loadButton3ClickListener)
    }

    /**
     * Back button clicked action. Closes an activity.
     */
    private fun backButtonClicked(){
        this.finish()
    }

    private fun loadButton1Clicker(){

    }

    private fun loadButton2Clicker(){

    }

    private fun loadButton3Clicker(){

    }
}