package com.example.pdfreader.task

import android.annotation.SuppressLint
import android.app.Activity
import android.database.Cursor
import android.provider.MediaStore
import com.example.pdfreader.database.DataFile
import java.io.File
import java.util.Collections
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@SuppressLint("StaticFieldLeak")
class LoadPdfFileTask(private val activity: Activity, private var callback: ICallbackLoadFile?) {
    private var listData: MutableList<DataFile>? = null
    var pool: ExecutorService = Executors.newCachedThreadPool()

    fun execute() {
        Thread {
            listData = Collections.synchronizedList(ArrayList())
            executeLoadFile()
            activity.runOnUiThread {
                if (callback != null) {
                    callback!!.callbackLoadFile(TagLoadfile.LOAD_FILE_SUCCESS, ArrayList(listData))
                }
                callback = null
            }
        }.start()
    }

    @SuppressLint("Recycle")
    private fun executeLoadFile() {
        val table = MediaStore.Files.getContentUri("external")
        val selection = "_data LIKE '%.pdf'"
        var cursor: Cursor? = null
        try {
            cursor = activity.contentResolver.query(table, null, selection, null, "date_added DESC")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            if (cursor == null || cursor.count <= 0 || !cursor.moveToFirst()) {
                return
            }
            val data = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)
            do {
                val path = cursor.getString(data)
                val file = File(path)
                if (file.length() == 0L) {
                    continue
                }
                pool.submit {
                    listData?.add(DataFile(id = 0, path = path))
                }
            } while (cursor.moveToNext())

            try {
                pool.awaitTermination(1, TimeUnit.SECONDS)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            pool.shutdown()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}