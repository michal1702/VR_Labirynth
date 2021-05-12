package pl.polsl.vr_labirynth.model

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import pl.polsl.vr_labirynth.entities.SaveEntity

@RunWith(value = Parameterized::class)
class GameSaveModelTest(private val saveEntity: SaveEntity) {

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    companion object{
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Iterable<Array<SaveEntity>> {
            return arrayListOf(
                arrayOf(SaveEntity(0.2,0.1,1.1,50,12345))
            ).toList()
        }
    }

    @Test
    fun save() {
        val saveModel = GameSaveModel(context, GameSaveModel.SaveSlots.Slot1)
        saveModel.save(saveEntity)
    }

    @Test
    fun load(){
        val saveModel = GameSaveModel(context, GameSaveModel.SaveSlots.Slot1)
        val loadedSave = saveModel.load()
    }
}