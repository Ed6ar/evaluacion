package com.edgar.ui.utils

import android.text.InputFilter

fun emojisFilter(): InputFilter {
    return InputFilter { source, start, end, _, _, _ ->
        var holder = ""
        for (index in start until end) {
            val type = Character.getType(source[index])
            if (type == Character.SURROGATE.toInt() || type == Character.OTHER_SYMBOL.toInt()) {
                return@InputFilter holder
            }else {
                holder += source[index]
            }
        }
        null
    }
}