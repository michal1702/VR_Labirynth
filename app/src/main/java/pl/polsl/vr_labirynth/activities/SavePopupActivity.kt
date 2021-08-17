package pl.polsl.vr_labirynth.activities

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import pl.polsl.vr_labirynth.R
import pl.polsl.vr_labirynth.databinding.ActivitySavePopupBinding
import pl.polsl.vr_labirynth.entities.SaveEntity
import pl.polsl.vr_labirynth.interfaces.IHideActionBar
import pl.polsl.vr_labirynth.interfaces.IToast
import pl.polsl.vr_labirynth.interfaces.ITouchAnimation
import pl.polsl.vr_labirynth.model.GameSaveModel

class SavePopupActivity : AppCompatActivity(), IHideActionBar, ITouchAnimation, IToast {

    override val activityWindow: Window by lazy { this.window }
    override val activityContext: Context = this
    private lateinit var binding: ActivitySavePopupBinding

    private val saveSlot1OnClickListener = View.OnClickListener { slot1Clicked() }
    private val saveSlot2OnClickListener = View.OnClickListener { slot2Clicked() }
    private val saveSlot3OnClickListener = View.OnClickListener { slot3Clicked() }

    private val saveSlot1OnLongClickListener = View.OnLongClickListener { slot1LongClicked() }
    private val saveSlot2OnLongClickListener = View.OnLongClickListener { slot2LongClicked() }
    private val saveSlot3OnLongClickListener = View.OnLongClickListener { slot3LongClicked() }

    private var slot1EnableSave = false
    private var slot2EnableSave = false
    private var slot3EnableSave = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavePopupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hideActionBar()
        checkSaves()

        buttonTouched(binding.saveSlot1)
        buttonTouched(binding.saveSlot2)
        buttonTouched(binding.saveSlot3)
        imageButtonTouched(binding.cancelButton)

        binding.cancelButton.setOnClickListener { finish() }
        binding.saveSlot1.setOnClickListener(saveSlot1OnClickListener)
        binding.saveSlot2.setOnClickListener(saveSlot2OnClickListener)
        binding.saveSlot3.setOnClickListener(saveSlot3OnClickListener)

        binding.saveSlot1.setOnLongClickListener(saveSlot1OnLongClickListener)
        binding.saveSlot2.setOnLongClickListener(saveSlot2OnLongClickListener)
        binding.saveSlot3.setOnLongClickListener(saveSlot3OnLongClickListener)
    }

    /**
     * Checks if there is a saved game in save slots
     */
    private fun checkSaves() {
        val gameSave = GameSaveModel(this)
        if (gameSave.ifFileExists(GameSaveModel.SaveSlots.Slot1)) {
            binding.saveSlot1.text = this.resources.getString(R.string.game_saved)
        }

        if (gameSave.ifFileExists(GameSaveModel.SaveSlots.Slot2)) {
            binding.saveSlot2.text = this.resources.getString(R.string.game_saved)
        }

        if (gameSave.ifFileExists(GameSaveModel.SaveSlots.Slot3)) {
            binding.saveSlot3.text = this.resources.getString(R.string.game_saved)
        }
    }

    /**
     * Performs save to a certain save slot
     * @param button
     */
    private fun performSave(button: Button, slot: GameSaveModel.SaveSlots){
        val gameSaveModel = GameSaveModel(this, slot)
        gameSaveModel.save(SaveEntity()) //tmp values
        button.text = this.resources.getString(R.string.game_saved)
        shortToast("Game saved")
    }

    /**
     * Saves game in a slot no. 1
     */
    private fun slot1Clicked() {
        if (binding.saveSlot1.text == this.resources.getString(R.string.empty_save)) {
            performSave(binding.saveSlot1, GameSaveModel.SaveSlots.Slot1)
        } else {
            slot1EnableSave = if (slot1EnableSave) {
                performSave(binding.saveSlot1, GameSaveModel.SaveSlots.Slot1)
                false
            } else {
                longToast("Touch once again to override")
                true
            }
        }
    }

    /**
     * Saves game in a slot no. 2
     */
    private fun slot2Clicked() {
        if (binding.saveSlot2.text == this.resources.getString(R.string.empty_save)) {
            performSave(binding.saveSlot2, GameSaveModel.SaveSlots.Slot2)
        } else {
            slot2EnableSave = if (slot2EnableSave) {
                performSave(binding.saveSlot2, GameSaveModel.SaveSlots.Slot2)
                false
            } else {
                longToast("Touch once again to override")
                true
            }
        }
    }

    /**
     * Saves game in a slot no. 3
     */
    private fun slot3Clicked() {
        if (binding.saveSlot3.text == this.resources.getString(R.string.empty_save)) {
            performSave(binding.saveSlot3, GameSaveModel.SaveSlots.Slot3)
        } else {
            slot3EnableSave = if (slot3EnableSave) {
                performSave(binding.saveSlot3, GameSaveModel.SaveSlots.Slot3)
                false
            } else {
                longToast("Touch once again to override")
                true
            }
        }
    }

    /**
     * Removes save from a slot no. 1
     * @return Boolean
     */
    private fun slot1LongClicked(): Boolean{
        val gameSave = GameSaveModel(this, GameSaveModel.SaveSlots.Slot1)
        if(gameSave.ifFileExists()) {
            gameSave.removeSave()
            binding.saveSlot1.text = this.resources.getString(R.string.empty_save)
        }
        return true
    }

    /**
     * Removes save from a slot no. 1
     * @return Boolean
     */
    private fun slot2LongClicked(): Boolean{
        val gameSave = GameSaveModel(this, GameSaveModel.SaveSlots.Slot2)
        if(gameSave.ifFileExists()) {
            gameSave.removeSave()
            binding.saveSlot2.text = this.resources.getString(R.string.empty_save)
        }
        return true
    }

    /**
     * Removes save from a slot no. 1
     * @return Boolean
     */
    private fun slot3LongClicked(): Boolean{
        val gameSave = GameSaveModel(this, GameSaveModel.SaveSlots.Slot3)
        if(gameSave.ifFileExists()) {
            gameSave.removeSave()
            binding.saveSlot3.text = this.resources.getString(R.string.empty_save)
        }
        return true
    }
}