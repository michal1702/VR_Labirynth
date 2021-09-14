package pl.polsl.vr_labirynth.activities

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.webkit.WebChromeClient
import android.webkit.JavascriptInterface
import android.webkit.ValueCallback
import androidx.appcompat.app.AppCompatActivity
import android.util.JsonReader
import java.io.StringReader
import java.util.ArrayList
import pl.polsl.vr_labirynth.databinding.ActivityGameBinding
import pl.polsl.vr_labirynth.interfaces.IHideActionBar

class GameActivity : AppCompatActivity(), IHideActionBar {

    private lateinit var binding: ActivityGameBinding
    override val activityWindow: Window by lazy { this.window }
    private lateinit var dialog:Dialog
    private var exitGame = false;
    private var score = 0;

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dialog = Dialog(this)
        hideActionBar()

        val webSettings = binding.gameWebView.settings
        webSettings.javaScriptEnabled = true
        binding.gameWebView.webChromeClient = WebChromeClient()
        binding.gameWebView.addJavascriptInterface(this, "android")
        binding.gameWebView.loadUrl("file:///android_asset/index.html")
    }

    override fun onBackPressed() {
        val popupIntent = Intent(this, SavePopupActivity::class.java)
        binding.gameWebView.evaluateJavascript("saveGameState", ValueCallback<String>(){
            fun onReceiveValue(data: String){
                var reader : JsonReader = JsonReader(StringReader(data))
                reader.beginObject()
                while(reader.hasNext()){
                    var fieldName: String = reader.nextName()
                    if(fieldName.equals("offsetX")){
                        popupIntent.putExtra("offsetX", reader.nextDouble())
                    } else if(fieldName.equals("offsetZ")){
                        popupIntent.putExtra("offsetZ", reader.nextDouble())
                    } else if(fieldName.equals("positionX")){
                        popupIntent.putExtra("positionX", reader.nextDouble())
                    } else if(fieldName.equals("positionY")){
                        popupIntent.putExtra("positionY", reader.nextDouble())
                    } else if(fieldName.equals("positionZ")){
                        popupIntent.putExtra("positionZ", reader.nextDouble())
                    } else if(fieldName.equals("points")){
                        popupIntent.putExtra("points", reader.nextInt())
                    } else if(fieldName.equals("hearts")){
                        popupIntent.putExtra("hearts", reader.nextInt())
                    } else if(fieldName.equals("map")){

                        popupIntent.putIntegerArrayListExtra("map", readIntArray(reader))
                    } else {
                        reader.skipValue()
                    }
                    reader.endObject()
                }
            }
        })

     //   popupIntent.putExtra("score", this.score)
        startActivityForResult(popupIntent, 1)
    }

    fun readIntArray(reader: JsonReader) : ArrayList<Int> {
        var values : ArrayList<Int> = ArrayList<Int>()

        reader.beginArray()
        while(reader.hasNext()) {
            values.add(reader.nextInt())
        }
        reader.endArray()
        return values
    }

    @JavascriptInterface
    fun updateScore(){
        this.score++;
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK){
            val result = data?.getBooleanExtra("exitGame", false)
            if(result != null && result == true){
                finish()
            }
        }
    }
}