package pl.polsl.vr_labirynth.activities

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import pl.polsl.vr_labirynth.databinding.ActivityHighScoresBinding
import pl.polsl.vr_labirynth.interfaces.IBackgroundAnimation
import pl.polsl.vr_labirynth.interfaces.ITouchAnimation
import pl.polsl.vr_labirynth.model.DatabaseManager

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

        fillTableView()
    }

    /**
     * Back button clicked action. Closes an activity.
     */
    private fun backButtonClicked(){
        this.finish()
    }

    /**
     * Method starts process of filling a table view.
     */
    private fun fillTableView(){
        val db = DatabaseManager()
        db.getScoreBoardData("Users", this, applicationContext)
    }
}