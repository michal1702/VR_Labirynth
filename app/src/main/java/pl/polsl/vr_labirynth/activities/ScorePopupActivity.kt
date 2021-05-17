package pl.polsl.vr_labirynth.activities

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import pl.polsl.vr_labirynth.databinding.ActivityScorePopupBinding
import pl.polsl.vr_labirynth.entities.PlayerScore
import pl.polsl.vr_labirynth.interfaces.IHideActionBar
import pl.polsl.vr_labirynth.interfaces.IToast
import pl.polsl.vr_labirynth.interfaces.ITouchAnimation
import pl.polsl.vr_labirynth.model.DatabaseManager


class ScorePopupActivity : AppCompatActivity(), IHideActionBar, IToast, ITouchAnimation {

    private lateinit var binding: ActivityScorePopupBinding
    override val activityContext: Context = this
    override val activityWindow: Window by lazy { this.window }

    private val submitButtonOnClickListener = View.OnClickListener { submitButtonClicked() }

    private var userScore: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScorePopupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideActionBar()

        applyWindowParams()

        userScore = intent.getIntExtra("score", -1)

        buttonTouched(binding.submitButton)
        buttonTouched(binding.cancelButton)
        binding.scoreTextView.text = userScore.toString()
        binding.cancelButton.setOnClickListener{finish()}
        binding.submitButton.setOnClickListener(submitButtonOnClickListener)
    }

    /**
     * Submit button clicked
     */
    private fun submitButtonClicked(){
        val usernameText = binding.usernameEditText.text
        if(usernameText.isEmpty()){
            shortToast("Enter a name")
        } else {
            val db = DatabaseManager()
            db.addData(PlayerScore(usernameText.toString(), userScore), "Users")
            finish()
        }
    }

    /**
     * Changes size of a popup activity
     */
    private fun applyWindowParams(){
        val screenMetrics = getDisplayMetrics()
        window.setLayout(
            (screenMetrics["width"]!! * 0.7).toInt(),
            (screenMetrics["height"]!! * 0.8).toInt()
        )
        val layoutParams = window.attributes
        layoutParams.gravity = Gravity.CENTER
        layoutParams.x = 0
        layoutParams.y = -20

        window.attributes = layoutParams
    }

    /**
     * Gets metrics of a screen
     * @return Map<String, Int>
     */
    private fun getDisplayMetrics(): Map<String, Int> {
        val map: HashMap<String, Int> = HashMap()
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics: WindowMetrics = this.windowManager.currentWindowMetrics
            map["width"] = windowMetrics.bounds.width()
            map["height"] = windowMetrics.bounds.height()
            map
        } else {
            val displayMetrics = DisplayMetrics()
            this.windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.widthPixels
            map["width"] = displayMetrics.widthPixels
            map["height"] = displayMetrics.heightPixels
            map
        }
    }
}