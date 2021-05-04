package pl.polsl.vr_labirynth

import android.annotation.SuppressLint
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import pl.polsl.vr_labirynth.databinding.ActivityMainBinding
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val exitButtonClickListener =  View.OnClickListener{ exitButtonClicked() }
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val animation: AnimationDrawable = binding.mainMenuLayout.background as AnimationDrawable
        animation.setEnterFadeDuration(2000)
        animation.setExitFadeDuration(4000)
        animation.start()

        binding.newGameButton.setOnTouchListener { v, event ->
            touched(v, event)
            v?.onTouchEvent(event) ?: true
        }
        binding.loadButton.setOnTouchListener { v, event ->
            touched(v, event)
            v?.onTouchEvent(event) ?: true
        }
        binding.highScoresButton.setOnTouchListener { v, event ->
            touched(v, event)
            v?.onTouchEvent(event) ?: true
        }
        binding.exitButton.setOnTouchListener { v, event ->
            touched(v, event)
            v?.onTouchEvent(event) ?: true
        }
        binding.exitButton.setOnClickListener(exitButtonClickListener)
    }

    /**
     * Method changes background color after a touch
     * @param view current view
     * @param event action performed on a button
     */
    private fun touched(view: View, event: MotionEvent?){
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                view.setBackgroundResource(R.drawable.button_touched_background)
            }
            MotionEvent.ACTION_UP -> {
                view.setBackgroundResource(R.drawable.buttons_background)
            }
        }
    }

    /**
     * Exit point of an application
     */
    private fun exitButtonClicked(){
        finish()
        exitProcess(0);
    }
}

