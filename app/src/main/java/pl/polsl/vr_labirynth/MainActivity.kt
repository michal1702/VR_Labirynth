package pl.polsl.vr_labirynth

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import pl.polsl.vr_labirynth.databinding.ActivityMainBinding
import pl.polsl.vr_labirynth.interfaces.IBackgroundAnimation
import pl.polsl.vr_labirynth.interfaces.ITouchAnimation
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity(), IBackgroundAnimation, ITouchAnimation {

    override val animationBackground: Drawable by lazy { binding.mainMenuLayout.background }
    private lateinit var binding: ActivityMainBinding

    private val exitButtonClickListener =  View.OnClickListener{ exitButtonClicked() }
    private val newGameButtonClickListener = View.OnClickListener { newGameButtonClicked() }
    private val highScoresButtonClickListener = View.OnClickListener { highScoresButtonClicked() }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        animateBackground()

        buttonTouched(binding.newGameButton)
        buttonTouched(binding.loadButton)
        buttonTouched(binding.highScoresButton)
        buttonTouched(binding.exitButton)

        binding.exitButton.setOnClickListener(exitButtonClickListener)
        binding.newGameButton.setOnClickListener(newGameButtonClickListener)
        binding.highScoresButton.setOnClickListener(highScoresButtonClickListener)
    }

    /**
     * Exit point of an application
     */
    private fun exitButtonClicked(){
        finish()
        exitProcess(0);
    }

    /**
     * Method starts new game
     */
    private fun newGameButtonClicked(){
        val newGameIntent = Intent(this, GameActivity::class.java)
        startActivity(newGameIntent)
    }

    /**
     * Method opens high scores activity
     */
    private fun highScoresButtonClicked(){
        val highScoresIntent = Intent(this, HighScoresActivity::class.java)
        startActivity(highScoresIntent)
    }
}

