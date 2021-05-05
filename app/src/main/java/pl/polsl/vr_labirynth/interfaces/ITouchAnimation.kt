package pl.polsl.vr_labirynth.interfaces

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import pl.polsl.vr_labirynth.R

interface ITouchAnimation {

    /**
     * Method changes background color after a touch
     * @param view current view
     * @param event action performed on a button
     */
    private fun touchEvent(view: View, event: MotionEvent?){
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                view.setBackgroundResource(R.drawable.button_touched_background)
            }
            MotionEvent.ACTION_UP -> {
                view.setBackgroundResource(R.drawable.buttons_background)
            }
        }
    }

    /**
     * Method called on button touch. It calls touchEvent() which manipulates the background of a button
     * @param button button
     */
    @SuppressLint("ClickableViewAccessibility")
    fun buttonTouched(button: Button){
        button.setOnTouchListener { view, event ->
            touchEvent(view, event)
            view?.onTouchEvent(event) ?: true
        }
    }

    /**
     * Method called on image button touch. It calls touchEvent() which manipulates the background of a image button
     * @param imgButton button
     */
    @SuppressLint("ClickableViewAccessibility")
    fun imageButtonTouched(imgButton: ImageButton){
        imgButton.setOnTouchListener{view, event ->
            touchEvent(view, event)
            view?.onTouchEvent(event) ?: true
        }
    }
}