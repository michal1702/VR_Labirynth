package pl.polsl.vr_labirynth.interfaces

import android.os.Build
import android.view.View
import android.view.Window

interface IHideActionBar {
    val activityWindow: Window

    /**
     * Hides action bar depending on sdk version
     */
    fun hideActionBar(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
            activityWindow.setDecorFitsSystemWindows(false)
        else
            activityWindow.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    }
}