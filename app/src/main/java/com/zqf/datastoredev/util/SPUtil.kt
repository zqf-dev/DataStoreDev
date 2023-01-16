package com.zqf.datastoredev.util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.zqf.datastoredev.App

val pkName: String = App.context.packageName

val sp: SharedPreferences = App.context.getSharedPreferences(pkName, Context.MODE_PRIVATE)

val editor: SharedPreferences.Editor = sp.edit()

fun putString(key: String, value: String) {
    editor.apply {
        putString(key, value)
        commit()
    }
}

fun putInt(key: String, value: Int) {
    editor.apply {
        putInt(key, value)
        commit()
    }
}

fun putLong(key: String, value: Long) {
    editor.putLong(key, value)
    editor.commit()
}

fun putBoolean(key: String, value: Boolean) {
    editor.putBoolean(key, value)
    editor.commit()
}

fun putFloat(key: String, value: Float) {
    editor.putFloat(key, value)
    editor.commit()
}

