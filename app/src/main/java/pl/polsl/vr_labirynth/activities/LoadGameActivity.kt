package pl.polsl.vr_labirynth.activities

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.JsonReader
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import pl.polsl.vr_labirynth.R
import pl.polsl.vr_labirynth.databinding.ActivityLoadGameBinding
import pl.polsl.vr_labirynth.interfaces.IBackgroundAnimation
import pl.polsl.vr_labirynth.interfaces.IHideActionBar
import pl.polsl.vr_labirynth.interfaces.ITouchAnimation
import pl.polsl.vr_labirynth.model.GameSaveModel
import java.io.StringReader

class LoadGameActivity() : AppCompatActivity(), IBackgroundAnimation, ITouchAnimation,
    IHideActionBar {

    override val activityWindow: Window by lazy { this.window }
    override val animationBackground: Drawable by lazy { binding.loadGameLayout.background }
    private lateinit var binding: ActivityLoadGameBinding

    private val backButtonOnClickListener = View.OnClickListener { backButtonClicked() }
    private val loadButton1ClickListener = View.OnClickListener { loadButton1Clicked() }
    private val loadButton2ClickListener = View.OnClickListener { loadButton2Clicked() }
    private val loadButton3ClickListener = View.OnClickListener { loadButton3Clicked() }
    private val loadButton1LongClickListener = View.OnLongClickListener { loadButton1LongClicked() }
    private val loadButton2LongClickListener = View.OnLongClickListener { loadButton2LongClicked() }
    private val loadButton3LongClickListener = View.OnLongClickListener { loadButton3LongClicked() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hideActionBar()
        checkSaves()

        imageButtonTouched(binding.backButton)
        buttonTouched(binding.loadButton1)
        buttonTouched(binding.loadButton2)
        buttonTouched(binding.loadButton3)
        binding.backButton.setOnClickListener(backButtonOnClickListener)
        binding.loadButton1.setOnClickListener(loadButton1ClickListener)
        binding.loadButton2.setOnClickListener(loadButton2ClickListener)
        binding.loadButton3.setOnClickListener(loadButton3ClickListener)

        binding.loadButton1.setOnLongClickListener(loadButton1LongClickListener)
        binding.loadButton2.setOnLongClickListener(loadButton2LongClickListener)
        binding.loadButton3.setOnLongClickListener(loadButton3LongClickListener)
    }

    /**
     * Checks if there is a saved game in save slots
     */
    private fun checkSaves() {
        val gameSave = GameSaveModel(this)
        if (gameSave.ifFileExists(GameSaveModel.SaveSlots.Slot1)) {
            binding.loadButton1.text = this.resources.getString(R.string.game_saved)
        }

        if (gameSave.ifFileExists(GameSaveModel.SaveSlots.Slot2)) {
            binding.loadButton2.text = this.resources.getString(R.string.game_saved)
        }

        if (gameSave.ifFileExists(GameSaveModel.SaveSlots.Slot3)) {
            binding.loadButton3.text = this.resources.getString(R.string.game_saved)
        }
    }

    /**
     * Back button clicked action. Closes an activity.
     */
    private fun backButtonClicked() {
        this.finish()
    }

    /**
     * Loads save from slot 1 or starts a new game
     */
    private fun loadButton1Clicked() {
        val gameSave = GameSaveModel(this, GameSaveModel.SaveSlots.Slot1)
        if (gameSave.ifFileExists()) {
            val save = gameSave.load()
            parseSaveAndRun(save)
        } else runGame()
    }

    private fun parseSaveAndRun(save: JSONObject?) {
        val reader = JsonReader(StringReader(save.toString()))
        val gameIntent = Intent(this, GameActivity::class.java).apply {
            reader.beginObject()
            while (reader.hasNext()) {
                when (reader.nextName()) {
                    "columns" -> {
                        putExtra("columns", reader.nextInt())
                    }
                    "rows" -> {
                        putExtra("rows", reader.nextInt())
                    }
                    "positionX" -> {
                        putExtra("positionX", reader.nextDouble())
                    }
                    "positionY" -> {
                        putExtra("positionY", reader.nextDouble())
                    }
                    "positionZ" -> {
                        putExtra("positionZ", reader.nextDouble())
                    }
                    "points" -> {
                        putExtra("points", reader.nextInt())
                    }
                    "hearts" -> {
                        putExtra("hearts", reader.nextInt())
                    }
                    "map" -> {
                        val map = readIntArray(reader)
                        putIntegerArrayListExtra("map", map)
                    }
                    else -> {
                        reader.skipValue()
                    }
                }
            }
            reader.endObject()
        }
        startActivity(gameIntent)
    }

    private fun readIntArray(reader: JsonReader): ArrayList<Int> {
        val values: ArrayList<Int> = ArrayList()

        reader.beginArray()
        while (reader.hasNext()) {
            values.add(reader.nextInt())
        }
        reader.endArray()
        return values
    }


    /**
     * Loads save from slot 2 or starts a new game
     */
    private fun loadButton2Clicked() {
        val gameSave = GameSaveModel(this, GameSaveModel.SaveSlots.Slot2)
        if (gameSave.ifFileExists()) {
            gameSave.load()
        } else runGame()
    }

    /**
     * Loads save from slot 3 or starts a new game
     */
    private fun loadButton3Clicked() {
        val gameSave = GameSaveModel(this, GameSaveModel.SaveSlots.Slot3)
        if (gameSave.ifFileExists()) {
            gameSave.load()
        } else runGame()
    }

    /**
     * Removes save from slot no. 1
     * @return Boolean
     */
    private fun loadButton1LongClicked(): Boolean {
        val gameSave = GameSaveModel(this, GameSaveModel.SaveSlots.Slot1)
        if (gameSave.ifFileExists()) {
            gameSave.removeSave()
            binding.loadButton1.text = this.resources.getString(R.string.empty_slot)
        }
        return true
    }

    /**
     * Removes save from slot no. 2
     * @return Boolean
     */
    private fun loadButton2LongClicked(): Boolean {
        val gameSave = GameSaveModel(this, GameSaveModel.SaveSlots.Slot2)
        if (gameSave.ifFileExists()) {
            gameSave.removeSave()
            binding.loadButton2.text = this.resources.getString(R.string.empty_slot)
        }
        return true
    }

    /**
     * Removes save from slot no. 3
     * @return Boolean
     */
    private fun loadButton3LongClicked(): Boolean {
        val gameSave = GameSaveModel(this, GameSaveModel.SaveSlots.Slot3)
        if (gameSave.ifFileExists()) {
            gameSave.removeSave()
            binding.loadButton3.text = this.resources.getString(R.string.empty_slot)
        }
        return true
    }

    /**
     * Runs new game
     */
    private fun runGame() {
        val gameIntent = Intent(this, GameActivity::class.java)
        startActivity(gameIntent)
    }
}