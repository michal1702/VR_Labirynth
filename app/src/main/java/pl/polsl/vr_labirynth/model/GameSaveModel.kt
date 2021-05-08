package pl.polsl.vr_labirynth.model

import android.content.Context
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream

class GameSaveModel(context: Context, slotName: SaveSlots) {

    private var fileLocation: String = ""

    enum class SaveSlots(val value: String){
        Slot1("save1"),
        Slot2("save2"),
        Slot3("save3")
    }

    init {
        fileLocation = context.cacheDir.absolutePath + "/" + slotName.value + ".txt"
    }

    fun load(){
        TODO("Load game")
    }

    fun save(){
        TODO("Save game")
    }

    fun ifFileExists(): Boolean{
        val file = File(fileLocation)
        return file.exists()
    }
}