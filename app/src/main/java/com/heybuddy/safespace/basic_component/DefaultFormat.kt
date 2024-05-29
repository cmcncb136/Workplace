package com.heybuddy.safespace.basic_component

import android.icu.text.DecimalFormat

class DefaultFormat {
    companion object{
        val numberFormat = DecimalFormat("#,###")
        fun defaultNumberFormat(x: Int): String{
            return numberFormat.format(x)
        }
    }
}