package com.aaron.notilistener.common

import android.content.Context
import android.widget.Toast

fun Context?.toast(
    msg: String?,
    duration: Int = Toast.LENGTH_SHORT,
) {
    Toast.makeText(this, msg, duration).show()
}