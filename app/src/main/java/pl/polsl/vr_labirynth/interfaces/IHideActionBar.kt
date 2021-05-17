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
        val currentApiVersion = Build.VERSION.SDK_INT;
        val flags = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        if (currentApiVersion >= Build.VERSION_CODES.KITKAT) {
            activityWindow.decorView.systemUiVisibility = flags
            val decorView = activityWindow.decorView
            decorView
                .setOnSystemUiVisibilityChangeListener { visibility ->
                    if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN === 0) {
                        decorView.systemUiVisibility = flags
                    }
                }
        }
    }
}