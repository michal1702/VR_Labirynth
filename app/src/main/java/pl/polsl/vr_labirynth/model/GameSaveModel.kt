package pl.polsl.vr_labirynth.model

import android.content.Context
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import pl.polsl.vr_labirynth.entities.SaveEntity
import pl.polsl.vr_labirynth.utils.IOUtil
import java.io.IOException

class GameSaveModel(context: Context, slotName: SaveSlots? = null) {

    private var fileLocation: String = ""

    enum class SaveSlots(val value: String){
        Slot1("save1"),
        Slot2("save2"),
        Slot3("save3")
    }

    init {
        fileLocation = if(slotName != null)
            context.cacheDir.absolutePath + "/" + slotName.value + ".txt"
        else context.cacheDir.absolutePath + "/"
    }

    /**
     * Loads game data from JSON format
     * @return JSON object or null in case of error
     */
    fun load(): JSONObject?{
        return try {
            JSONObject(IOUtil.readJsonFromFile(fileLocation))
        }catch (e: IOException){
            Log.e("Load error", "Cannot read JSON object")
            null
        }
    }

    /**
     * Saves game data in JSON format
     * @param saveEntity
     */
    fun save(saveEntity: SaveEntity){
        val jObject = JSONObject()
        val array = JSONArray()
        val map = saveEntity.map ?: intArrayOf(0)
        for(item in map){
            array.put(item)
        }
        jObject.put("columns", saveEntity.columns)
        jObject.put("rows", saveEntity.rows)
        jObject.put("positionX", saveEntity.positionX)
        jObject.put("positionY", saveEntity.positionY)
        jObject.put("positionZ", saveEntity.positionZ)
        jObject.put("points", saveEntity.points)
        jObject.put("hearts", saveEntity.hearts)
        jObject.put("map", array)

        val jString = jObject.toString()
        IOUtil.writeJsonToFile(fileLocation, jString)
    }

    /**
     * Checks if file exists
     * @return true if file exists else false
     */
    fun ifFileExists(): Boolean{
        return IOUtil.ifExists(fileLocation)
    }

    /**
     * Checks if file exists
     * @param slotName
     * @return true if file exists else false
     */
    fun ifFileExists(slotName: SaveSlots):Boolean{
        return IOUtil.ifExists(fileLocation + slotName.value + ".txt")
    }

    /**
     * Removes game save
     */
    fun removeSave(){
        if(IOUtil.ifExists(fileLocation)){
            IOUtil.removeFile(fileLocation)
        }
    }
}