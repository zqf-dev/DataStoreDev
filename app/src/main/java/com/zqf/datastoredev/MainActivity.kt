package com.zqf.datastoredev

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.zqf.datastoredev.util.putString
import com.zqf.datastoredev.util.sp
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initSpUtil()
        initFiles()
    }

    private fun initFiles() {
        val fos: FileOutputStream = openFileOutput("testFile.txt", MODE_PRIVATE)
        fos.write("123".toByteArray())
    }

    private fun initSpUtil() {
        putString("string_key", "123")
        val spStrV = sp.getString("string_key", "")
        Log.e("TAG", "spV: $spStrV")
    }
}