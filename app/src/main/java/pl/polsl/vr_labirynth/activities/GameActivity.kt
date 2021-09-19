package pl.polsl.vr_labirynth.activities

import android.annotation.SuppressLint
import android.webkit.ConsoleMessage
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.JsonReader
import android.util.Log
import android.view.Window
import android.webkit.JavascriptInterface
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import androidx.appcompat.app.AppCompatActivity
import pl.polsl.vr_labirynth.databinding.ActivityGameBinding
import pl.polsl.vr_labirynth.interfaces.IHideActionBar
import java.io.StringReader
import java.util.*

class GameActivity : AppCompatActivity(), IHideActionBar {

    private lateinit var binding: ActivityGameBinding
    override val activityWindow: Window by lazy { this.window }
    private lateinit var dialog: Dialog
    private var exitGame = false;
    private var score = 0;
//	private lateinit var intent : Intent

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //	this.intent = getIntent()

        dialog = Dialog(this)
        hideActionBar()

        val webSettings = binding.gameWebView.settings
        webSettings.javaScriptEnabled = true
        binding.gameWebView.webChromeClient = object : WebChromeClient() {
            override fun onConsoleMessage(message: ConsoleMessage): Boolean {
                Log.e("CONSOLE", "${message.message()}")
                return true
            }
        }
        binding.gameWebView.addJavascriptInterface(this, "android")
        binding.gameWebView.loadUrl("file:///android_asset/index.html")
    }

    override fun onBackPressed() {
        binding.gameWebView.evaluateJavascript("saveGameState();", ValueCallback<String>() {
            Log.i("received: ", it)
            val reader = JsonReader(StringReader(it))
            reader.beginObject()
            val popupIntent = Intent(this, SavePopupActivity::class.java).apply {
                while (reader.hasNext()) {
                    when (reader.nextName()) {
                        "columns" -> {
                            putExtra("columns", reader.nextInt())
                        }
                        "rows" -> {
                            putExtra("rows", reader.nextInt())
                        }
                        "positionX" -> {
                            putExtra("positionX", reader.nextDouble())
                        }
                        "positionY" -> {
                            putExtra("positionY", reader.nextDouble())
                        }
                        "positionZ" -> {
                            putExtra("positionZ", reader.nextDouble())
                        }
                        "points" -> {
                            putExtra("points", reader.nextInt())
                        }
                        "hearts" -> {
                            putExtra("hearts", reader.nextInt())
                        }
                        "map" -> {
                            val map = readIntArray(reader)
                            putIntegerArrayListExtra("map", map)
                        }
                        else -> {
                            reader.skipValue()
                        }
                    }
                }
                reader.endObject()
            }
            startActivityForResult(popupIntent, 1)
        })

    }
	
	

    private fun readIntArray(reader: JsonReader): ArrayList<Int> {
        val values: ArrayList<Int> = ArrayList<Int>()

        reader.beginArray()
        while (reader.hasNext()) {
            values.add(reader.nextInt())
        }
        reader.endArray()
        return values
    }
	@JavascriptInterface
	fun gameOver(points: Int) {
       
            val scoreIntent = Intent(this, ScorePopupActivity::class.java).apply {
               
                            putExtra("score", points)
                     
            startActivityForResult(popupIntent, 1)
        })

    }
    @JavascriptInterface
    fun checkLoad(): Boolean {
        val extras: Bundle? = this.intent.extras
        return if (extras != null) {
            Log.e("check", "TRUE")
            extras.containsKey("columns") && extras.containsKey("rows") && extras.containsKey(
                    "positionX"
                ) && extras.containsKey("positionY") && extras.containsKey("positionZ") && extras.containsKey(
                    "points"
                ) && extras.containsKey("hearts") && extras.containsKey("map")

        } else {
            Log.e("check", "FALSE")
            false;
        }
    }

    @JavascriptInterface
    fun getColumns(): Int {
        return this.intent.getIntExtra("columns", 0)
    }

    @JavascriptInterface
    fun getRows(): Int {
        return this.intent.getIntExtra("rows", 0)
    }

    @JavascriptInterface
    fun getPositionX(): Double {
        return this.intent.getDoubleExtra("positionX", 0.0)
    }

    @JavascriptInterface
    fun getPositionY(): Double {
        return this.intent.getDoubleExtra("positionY", 0.0)
    }

    @JavascriptInterface
    fun getPositionZ(): Double {
        return this.intent.getDoubleExtra("positionZ", 0.0)
    }

    @JavascriptInterface
    fun getPoints(): Int {
        return this.intent.getIntExtra("points", 0)
    }

    @JavascriptInterface
    fun getHearts(): Int {
        return this.intent.getIntExtra("hearts", 0)
    }

    @JavascriptInterface
    fun getMap(): String {
        val map = intent.getIntegerArrayListExtra("map")
        val positions = map?.toIntArray() ?: intArrayOf(0)
        var mapAsString = ""
        for(item in positions){
            mapAsString += "$item|"
        }
        return mapAsString.take(mapAsString.length-1)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK) {
            val result = data?.getBooleanExtra("exitGame", false)
            if (result != null && result == true) {
                finish()
            }
        }
    }
}