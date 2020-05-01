package com.nahman.currentweatherapp.model

import android.content.res.AssetManager
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class IOK {

    companion object {

        @Throws(IOException::class)
        fun readURL(http: String?): String? {
            val url = URL(http)
            //HTTP + S
            val con = url.openConnection() as HttpURLConnection
            //HttpsUrlConnection extends HttpUtlConnection extends URLConnection extends Object
            val inputStream = con.inputStream
            return read(
                inputStream
            )
        }

        @Throws(IOException::class)
        fun read(inputStream: InputStream?): String? {
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                var line: String? = null
                val sb = StringBuilder()
                while (reader.readLine().also { line = it } != null) {
                    sb.append(line).append(System.lineSeparator())
                }
                return sb.toString()
            }
        }


        @Throws(IOException::class)
        fun read(am: AssetManager, fileName: String?): String? {
            //InputStream in = am.open(fileName);
            return read(
                am.open(
                    fileName!!
                )
            )
        }
    }

}