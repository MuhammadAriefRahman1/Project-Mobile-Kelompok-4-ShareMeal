package com.kelompok_4.share_meal.helpers

import android.net.Uri
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

object DbHelpers {

    fun fetchSingleDataByPath(path: String, callback: (DataSnapshot?) -> Unit) {
        val dbRef = FirebaseDatabase.getInstance().getReference(path)

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                callback(snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null)
            }

        })
    }


    fun fetchSingleDataByCondition(
        path: String,
        condition: Pair<String, String>,
        callback: (DataSnapshot?) -> Unit
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference(path)

        dbRef.orderByChild(condition.first).equalTo(condition.second)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    callback(snapshot)
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(null)
                }

            })
    }

    fun uploadImage(path: String, uri: Uri, callback: (String) -> Unit) {
        val storageRef = FirebaseStorage.getInstance().getReference(path)
        val uploadTask = storageRef.putFile(uri)

        uploadTask.addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener {
                callback(it.toString())
            }
        }
    }


    fun fetchDataByPath(path: String, callback: (DataSnapshot?) -> Unit) {
        val dbRef = FirebaseDatabase.getInstance().getReference(path)

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                callback(snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null)
            }

        })
    }

    fun deleteDataByPath(path: String, callback: () -> Unit = {}) {
        val dbRef = FirebaseDatabase.getInstance().getReference(path)

        dbRef.removeValue().addOnSuccessListener {
            callback()
        }
    }


    fun setDataByPath(path: String, data: Any, callback: () -> Unit = {}) {
        val dbRef = FirebaseDatabase.getInstance().getReference(path)

        dbRef.setValue(data).addOnSuccessListener {
            callback()
        }
    }


}