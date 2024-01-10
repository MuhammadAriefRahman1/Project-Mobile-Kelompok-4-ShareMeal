package com.kelompok_4.share_meal.helpers

import android.app.Activity
import com.kelompok_4.share_meal.R

object Helpers {

    fun overridePendingEnterTransition(context: Activity) {
        context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    fun overridePendingExitTransition(context: Activity) {
        context.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}