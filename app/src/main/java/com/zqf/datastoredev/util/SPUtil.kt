package com.zqf.datastoredev.util

import android.content.Context
import android.content.SharedPreferences
import com.zqf.datastoredev.App

val pkName: String = App.context.packageName

val sp: SharedPreferences = App.context.getSharedPreferences(pkName, Context.MODE_PRIVATE)

val editor: SharedPreferences.Editor = sp.edit()

fun putString(key: String, value: String) {
    editor.putString(key, value)
    editor.commit()
}

fun putInt(key: String, value: Int) {
    editor.putInt(key, value)
    editor.commit()
}

