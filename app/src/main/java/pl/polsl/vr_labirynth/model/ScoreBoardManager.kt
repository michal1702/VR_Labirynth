package pl.polsl.vr_labirynth.model

import android.content.Context
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import pl.polsl.vr_labirynth.R
import pl.polsl.vr_labirynth.activities.HighScoresActivity

class ScoreBoardManager(private var view: HighScoresActivity, private var context: Context) {

    private val scoreBoard: TableLayout = view.findViewById(R.id.scoreboardTableLayout)

    /**
     * Creates row and adds it to a score board
     * @param place place in ranking
     * @param playerName name of the player
     * @param score score of the player
     */
    fun createRow(place: Int, playerName: String, score: Int){
        val layoutParameters = TableRow.LayoutParams(115, 90)
        val row = TableRow(context)
        val placeTextView = TextView(context)
        val playerNameTextView = TextView(context)
        val scoreTextView = TextView(context)
        row.layoutParams = layoutParameters

        customizeTextViewRow(placeTextView, layoutParameters, place.toString())
        customizeTextViewRow(playerNameTextView, layoutParameters, playerName)
        customizeTextViewRow(scoreTextView, layoutParameters, score.toString())

        row.addView(placeTextView)
        row.addView(playerNameTextView)
        row.addView(scoreTextView)

        scoreBoard.addView(row, TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT))
    }

    /**
     * Method responsible for styling text fields in a certain row
     * @param textField textView
     * @param parameters layout parameters
     * @param textValue value of the text view as a String
     */
    private fun customizeTextViewRow(textField: TextView, parameters: TableRow.LayoutParams, textValue: String){
        textField.layoutParams = parameters
        textField.text = textValue
        textField.typeface = ResourcesCompat.getFont(context, R.font.itim_regular)
        textField.textSize = 22.0F
        textField.setTextColor(ContextCompat.getColor(context, R.color.text_white))
        textField.setShadowLayer(10F, 1F, 1F, ContextCompat.getColor(context, R.color.black))
        textField.textAlignment = View.TEXT_ALIGNMENT_CENTER
        textField.setPadding(0,12,0,0)
    }
}