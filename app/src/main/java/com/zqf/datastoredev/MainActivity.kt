package com.zqf.datastoredev

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.zqf.datastoredev.util.putInt
import com.zqf.datastoredev.util.putString
import com.zqf.datastoredev.util.sp
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initSpUtil()
        initFiles()
    }

    private fun initFiles() {
        //写
        val fos: FileOutputStream = openFileOutput("testFile.txt", MODE_PRIVATE)
        fos.write("123".toByteArray())
        fos.close()
        //读
        val fis: FileInputStream = openFileInput("testFile.txt")
        val buffer = ByteArray(1024)
        val readCount = fis.read(buffer)
        val str = String(buffer, 0, readCount, Charset.defaultCharset())
        Log.e("TAG", "str::>> $str")
        fis.close()
    }

    private fun initSpUtil() {
        putString("string_key", "123")
        val spStrV = sp.getString("string_key", "")
        putInt("int_key", 123)
        val spIntV = sp.getInt("int_key", 0)
        Log.e("TAG", "spV: $spStrV spIntV: $spIntV")
    }
}