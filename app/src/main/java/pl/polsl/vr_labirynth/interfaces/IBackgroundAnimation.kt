package pl.polsl.vr_labirynth.interfaces

import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable

interface IBackgroundAnimation {

    val animationBackground: Drawable

    /**
     * Method animates background of the activity
     */
    fun animateBackground(){
        val animation: AnimationDrawable = animationBackground as AnimationDrawable
        animation.setEnterFadeDuration(2000)
        animation.setExitFadeDuration(4000)
        animation.start()
    }
}