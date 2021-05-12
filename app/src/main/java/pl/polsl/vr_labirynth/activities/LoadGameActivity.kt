package pl.polsl.vr_labirynth.activities

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import pl.polsl.vr_labirynth.databinding.ActivityLoadGameBinding
import pl.polsl.vr_labirynth.interfaces.IBackgroundAnimation
import pl.polsl.vr_labirynth.interfaces.ITouchAnimation
import pl.polsl.vr_labirynth.model.GameSaveModel

class LoadGameActivity() : AppCompatActivity(), IBackgroundAnimation, ITouchAnimation {

    override val animationBackground: Drawable by lazy { binding.loadGameLayout.background }
    private lateinit var binding:ActivityLoadGameBinding

    private val backButtonOnClickListener = View.OnClickListener { backButtonClicked() }
    private val loadButton1ClickListener = View.OnClickListener { loadButton1Clicked() }
    private val loadButton2ClickListener = View.OnClickListener { loadButton2Clicked() }
    private val loadButton3ClickListener = View.OnClickListener { loadButton3Clicked() }

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

    /**
     * Loads save from slot 1 or starts a new game
     */
    private fun loadButton1Clicked(){
        val gameSave = GameSaveModel(this, GameSaveModel.SaveSlots.Slot1)
        if (gameSave.ifFileExists()){
            gameSave.load()
        }
        else runGame()
    }

    /**
     * Loads save from slot 2 or starts a new game
     */
    private fun loadButton2Clicked(){
        val gameSave = GameSaveModel(this, GameSaveModel.SaveSlots.Slot2)
        if (gameSave.ifFileExists()){
            gameSave.load()
        }
        else runGame()
    }

    /**
     * Loads save from slot 3 or starts a new game
     */
    private fun loadButton3Clicked(){
        val gameSave = GameSaveModel(this, GameSaveModel.SaveSlots.Slot3)
        if (gameSave.ifFileExists()){
            gameSave.load()
        }
        else runGame()
    }

    /**
     * Runs new game
     */
    private fun runGame(){
        val gameIntent = Intent(this, GameActivity::class.java)
        startActivity(gameIntent)
    }
}