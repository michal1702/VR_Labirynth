package pl.polsl.vr_labirynth.model

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import pl.polsl.vr_labirynth.R
import pl.polsl.vr_labirynth.activities.HighScoresActivity


class DatabaseManager {

    private val dataBaseRef = FirebaseDatabase.getInstance().reference
    private var connected: Boolean = false

    /**
     * Checks if device is connected to the internet
     * @param cm Connectivity Manager
     */
    fun checkInternetConnection(cm: ConnectivityManager) {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
                val cap = cm.getNetworkCapabilities(cm.activeNetwork)
                connected = cap?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
                val networks = cm.allNetworks
                for (n in networks) {
                    val nInfo = cm.getNetworkInfo(n)
                    if (nInfo != null && nInfo.isConnected) {
                        connected = true
                        break
                    } else connected = false
                }
            }
            else -> {
                val networks = cm.allNetworkInfo
                for (nInfo in networks) {
                    if (nInfo != null && nInfo.isConnected) {
                        connected = true
                        break
                    } else connected = false
                }
            }
        }
    }

    /**
     * Adds data of any type to child directory
     * @param data
     * @param child directory
     */
    fun addData(data: Any, child: String) {
        val ref = dataBaseRef.child(child)
        ref.child(ref.push().key!!).setValue(data)
    }

    /**
     * Gets high scores records from the database and inserts it into table layout
     * @param child child directory of a database
     * @param view HighScoresActivity
     * @param applicationContext context
     */
    fun getScoreBoardData(child: String, view: HighScoresActivity, applicationContext: Context) {
        val noInternetTextView =
            view.findViewById<TextView>(R.id.internetProblemTextView)
        if (connected) {
            val ref = dataBaseRef.child(child).orderByChild("score").limitToLast(20)
            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    noInternetTextView.text = ""
                    val iterable = dataSnapshot.children
                    val scoreBoard = ScoreBoardManager(view, applicationContext)
                    var place = 1
                    for (data in iterable.reversed()) {
                        val value = data.value as Map<*, *>
                        scoreBoard.createRow(
                            place,
                            value["playerName"].toString(),
                            value["score"].toString().toInt()
                        )
                        place++
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
        } else noInternetTextView.text = "No internet\nCheck your connection"
    }
}