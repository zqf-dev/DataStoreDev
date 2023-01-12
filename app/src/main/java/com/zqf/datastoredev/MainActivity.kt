package com.zqf.datastoredev

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.zqf.datastoredev.util.editor
import com.zqf.datastoredev.util.sp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUtil()
    }

    private fun initUtil() {
        editor.putString("string_key", "123")
        editor.commit()
        val spStrValue = sp.getString("string_key", "")
        Log.e("TAG", "spV: $spStrValue")
    }
}