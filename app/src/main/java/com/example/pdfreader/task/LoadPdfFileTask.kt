package com.example.pdfreader.task

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.os.AsyncTask
import android.provider.MediaStore
import android.util.Log
import com.example.pdfreader.database.DataFile
import java.io.File

@SuppressLint("StaticFieldLeak")
class LoadPdfFile(private val activity: Activity, private var callback: ICallbackLoadFile) :
    AsyncTask<Void?, Void?, Void?>() {
    private val listData: ArrayList<DataFile> = ArrayList()

    override fun doInBackground(vararg voids: Void?): Void? {
        val contentResolver: ContentResolver = activity.contentResolver
        val uri = MediaStore.Files.getContentUri("external")
        val projection = arrayOf(
            MediaStore.Files.FileColumns.DATA,
            MediaStore.Files.FileColumns.MIME_TYPE
        )
        val selection = "${MediaStore.Files.FileColumns.MIME_TYPE}='application/pdf'"

        val cursor = contentResolver.query(uri, projection, selection, null, null)

        cursor?.use { c ->
            val pathIndex = c.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)

            while (c.moveToNext()) {
                val path = c.getString(pathIndex)
                listData.add(DataFile(path = path))
            }
        }
        cursor?.close()
        return null
    }

    override fun onPostExecute(aVoid: Void?) {
        super.onPostExecute(aVoid)
        if (callback != null) {
            callback.callbackLoadFile(TagLoadfile.LOAD_FILE_SUCCESS, listData)
        }
    }
}