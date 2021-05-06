package pl.polsl.vr_labirynth.model

import android.content.Context
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import pl.polsl.vr_labirynth.activities.HighScoresActivity


class DatabaseManager {

    private val dataBaseRef = FirebaseDatabase.getInstance().reference

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
    fun getScoreBoardData(child: String, view: HighScoresActivity, applicationContext: Context){
        val ref = dataBaseRef.child(child).orderByChild("score").limitToLast(20)
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
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
                // Failed to read value
                println("Failed to read value.")
            }
        })
    }
}