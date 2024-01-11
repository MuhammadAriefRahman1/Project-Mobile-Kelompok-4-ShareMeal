package com.kelompok_4.share_meal.helpers

import android.app.Activity
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.kelompok_4.share_meal.R

object Helpers {

    fun overridePendingEnterTransition(context: Activity) {
        context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    fun overridePendingExitTransition(context: Activity) {
        context.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    fun showToast(context: Activity, message: String) {
        Toast.makeText(context, message, android.widget.Toast.LENGTH_SHORT).show()
    }

    fun getUid(): String {
        return FirebaseAuth.getInstance().currentUser!!.uid ?: ""
    }


    fun makeAlertDialog(
        context: Activity,
        title: String,
        message: String,
        optionalCallback: () -> Unit = {}
    ) {
        val builder = android.app.AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
            optionalCallback()
        }
        builder.setOnCancelListener { dialog ->
            dialog.dismiss()
            optionalCallback()
        }
        builder.show()
    }

    fun alertDialogWithBtn(
        context: Activity,
        title: String,
        message: String,
        positiveText: String,
        negativeText: String,
        positiveCallback: () -> Unit = {},
        negativeCallback: () -> Unit = {}
    ) {
        val builder = android.app.AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(positiveText) { dialog, _ ->
            dialog.dismiss()
            positiveCallback()
        }
        builder.setNegativeButton(negativeText) { dialog, _ ->
            dialog.dismiss()
            negativeCallback()
        }
        builder.setOnCancelListener { dialog ->
            dialog.dismiss()
            negativeCallback()
        }
        builder.show()

    }

    fun getCurrentDate(): String {
        val date = java.util.Calendar.getInstance().time
        val formatter = java.text.SimpleDateFormat("dd-MM-yyyy")
        return formatter.format(date)
    }
}