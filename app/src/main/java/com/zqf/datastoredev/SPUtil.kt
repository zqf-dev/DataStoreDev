package com.zqf.datastoredev

import android.content.Context
import android.content.SharedPreferences

val sp: SharedPreferences =
    App.context.getSharedPreferences("datastore", Context.MODE_PRIVATE)