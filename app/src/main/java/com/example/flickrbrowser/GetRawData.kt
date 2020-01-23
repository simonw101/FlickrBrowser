package com.example.flickrbrowser

import android.os.AsyncTask
import android.util.Log
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

private const val TAG = "GetRawData"

enum class DownloadStatus {

    OK, IDLE, NOT_INITIALIZED, FAILED_OR_EMPTY, PERMISSIONS_ERROR, ERROR

}

class GetRawData(private val listener: OndownloadComplete) : AsyncTask<String, Void, String>() {

    private var downloadStatus = DownloadStatus.IDLE

    interface OndownloadComplete {

        fun onDownloadComplete(data: String, status: DownloadStatus)

    }
    override fun onPostExecute(result: String) {
        Log.d(TAG, "onPostExecute called")

        listener.onDownloadComplete(result, downloadStatus)
    }

    override fun doInBackground(vararg params: String?): String {

        if (params[0] == null) {

            downloadStatus = DownloadStatus.NOT_INITIALIZED
            return "No URL Specified"

        }

        try {
            downloadStatus = DownloadStatus.OK
            return URL(params[0]).readText()
        } catch (e: Exception) {

            val errorMessage = when (e) {

                is MalformedURLException -> {
                    downloadStatus = DownloadStatus.NOT_INITIALIZED
                    "doInBackground: Invalid URL ${e.message}"
                }

                is IOException -> {
                    downloadStatus = DownloadStatus.FAILED_OR_EMPTY
                    "doInBackground: IO Exception reading data ${e.message}"
                }

                is SecurityException -> {
                    downloadStatus = DownloadStatus.PERMISSIONS_ERROR
                    "doInBackground: Security Exception: needs permission? ${e.message}"
                }

                else -> {

                    downloadStatus = DownloadStatus.ERROR
                    "Unknown Error: ${e.message}"

                }

            }

            Log.e(TAG, errorMessage)

            return errorMessage

        }

    }
}