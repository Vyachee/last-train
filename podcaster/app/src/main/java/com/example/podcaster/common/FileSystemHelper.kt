package com.example.podcaster.common

import android.content.Context
import android.util.Log
import java.io.File

class FileSystemHelper {

    fun saveFile(fileName: String, folderName: String, file: File) {

    }

    fun getRecords(context: Context): List<RecordItem> {

        val list = mutableListOf<RecordItem>()

        val basePath = context.externalCacheDir?.absolutePath + File.separator
        val dir = File(basePath)
        val dirs = dir.listFiles()

        for(d in dirs) {
            if(d.isDirectory) {
                for(i in d.listFiles()) {
                    if(i.isFile) {
                        Log.e("DEBUG", "name: ${i.absolutePath}")
                        list.add(
                            RecordItem(i.name, i.absolutePath)
                        )
                    }
                }
            }
        }

        return list
    }

}