package com.kelompok_4.share_meal.helpers

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RealtimeDB {
    private var db: FirebaseDatabase

    init {
        db = FirebaseDatabase.getInstance()
    }

    fun getSingleData(path: String, callback: (snapshot: DataSnapshot?) -> Unit) {
        val dbRef = db.getReference(path)

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                callback(snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null)
            }
        })
    }
}