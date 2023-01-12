package com.zqf.datastoredev.util

import android.content.Context
import android.content.SharedPreferences
import com.zqf.datastoredev.App

val sp: SharedPreferences = App.context.getSharedPreferences("", Context.MODE_PRIVATE)

val editor: SharedPreferences.Editor = sp.edit()