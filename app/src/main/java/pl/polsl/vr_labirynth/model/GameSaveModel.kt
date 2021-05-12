package pl.polsl.vr_labirynth.model

import android.content.Context
import org.json.JSONObject
import pl.polsl.vr_labirynth.entities.SaveEntity
import pl.polsl.vr_labirynth.utils.IOUtil
import java.io.IOException

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

    /**
     * Loads game data from JSON format
     * @return JSON object or null in case of error
     */
    fun load(): JSONObject?{
        return try {
            JSONObject(IOUtil.readJsonFromFile(this.fileLocation))
        }catch (e: IOException){
            null
        }
    }

    /**
     * Saves game data in JSON format
     * @param saveEntity
     */
    fun save(saveEntity: SaveEntity){
        val jObject = JSONObject()
        jObject.put("positionX", saveEntity.positionX)
        jObject.put("positionY", saveEntity.positionY)
        jObject.put("positionZ", saveEntity.positionZ)
        jObject.put("score", saveEntity.score)
        jObject.put("seed", saveEntity.seed)

        val jString = jObject.toString()
        IOUtil.writeJsonToFile(this.fileLocation, jString)
    }

    /**
     * Checks if file exists
     * @return true if file exists else false
     */
    fun ifFileExists(): Boolean{
        return IOUtil.ifExists(this.fileLocation)
    }
}