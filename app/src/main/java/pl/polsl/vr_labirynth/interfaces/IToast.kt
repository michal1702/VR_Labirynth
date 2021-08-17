package pl.polsl.vr_labirynth.interfaces

import android.content.Context
import android.widget.Toast

interface IToast {
    val activityContext: Context

    /**
     * Shows short toast on the screen
     */
    fun shortToast(message: String){
        Toast.makeText(this.activityContext, message,Toast.LENGTH_SHORT).show()
    }

    /**
     * Shows long toast on the screen
     */
    fun longToast(message: String){
        Toast.makeText(this.activityContext, message,Toast.LENGTH_LONG).show()
    }
}