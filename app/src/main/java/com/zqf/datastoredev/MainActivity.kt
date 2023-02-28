package com.zqf.datastoredev

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.zqf.datastoredev.util.KillCheckService
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

    private fun initSpUtil() {
        putString("string_key", "123")
        val spStrV = sp.getString("string_key", "")
        putInt("int_key", 123)
        val spIntV = sp.getInt("int_key", 0)
        Log.e("TAG", "spStrValue: $spStrV spIntValue: $spIntV")
        startService(Intent(this, KillCheckService::class.java))
    }

    private fun initFiles() {
        /**
         * TODO 写
         * MODE_PRIVATE：默认方式【其中创建的文件只能被调用应用程序(或所有共享相同的用户ID)】
         * MODE_WORLD_READABLE：允许所有其他应用程序具有读访问权限
         * MODE_WORLD_WRITEABLE：允许所有其他应用程序具有写访问权限
         * MODE_APPEND：如果文件已经存在，然后将数据写入现有文件的末尾
         * MODE_MULTI_PROCESS：
         * MODE_ENABLE_WRITE_AHEAD_LOGGING：
         * MODE_NO_LOCALIZED_COLLATORS：
         *
         */
        val fos: FileOutputStream = openFileOutput("testFile.txt", MODE_PRIVATE)
        fos.write("123".toByteArray())
        fos.close()
        /**
         * TODO 读
         */
        val fis: FileInputStream = openFileInput("testFile.txt")
        val buffer = ByteArray(1024)
        val readCount = fis.read(buffer)
        val str = String(buffer, 0, readCount, Charset.defaultCharset())
        Log.e("TAG", "str::>> $str")
        fis.close()
    }
}