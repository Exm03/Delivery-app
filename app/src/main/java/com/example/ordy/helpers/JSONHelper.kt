package com.example.ordy.helpers

import android.content.Context
import com.example.ordy.Models.Cart
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader

class JSONHelper {

    companion object {
        private val filename = "cart.json"

        fun expotrToJSON(context: Context, dataList: List<Cart?>?) {
            val jsonString = Gson().toJson(dataList)

            val file: FileOutputStream? = context.openFileOutput(filename, Context.MODE_PRIVATE)

            file?.write(jsonString.toByteArray())
            file?.close()
        }

        fun importFromJSON(context: Context): List<Cart?>? {

            val file = File(context.filesDir, filename)
            if (!file.exists()) return null

            val fileInputStream: FileInputStream? = context.openFileInput(filename)
            val inputStreamReader = InputStreamReader(fileInputStream)

            val listType = object : TypeToken<List<Cart>>() {}.type
            val dataItems: List<Cart?>? =
                Gson().fromJson(inputStreamReader, Array<Cart?>::class.java).toList()

            inputStreamReader.close()
            fileInputStream?.close()

            return dataItems

        }
    }
}