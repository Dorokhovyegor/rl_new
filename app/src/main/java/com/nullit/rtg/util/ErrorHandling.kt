package com.nullit.rtg.util

import android.util.Log
import org.json.JSONException
import org.json.JSONObject

class ErrorHandling{

    companion object{

        private val TAG: String = "AppDebug"
        const val UNABLE_TO_RESOLVE_HOST = "Unable to resolve host"
        const val UNABLE_TODO_OPERATION_WO_INTERNET = "Can't do that operation without an internet connection"
        const val ERROR_SAVE_ACCOUNT_PROPERTIES = "Error saving account properties.\nTry restarting the app."
        const val ERROR_SAVE_AUTH_TOKEN = "Error saving authentication token.\nTry restarting the app."
        const val ERROR_SOMETHING_WRONG_WITH_IMAGE = "Something went wrong with the image."
        const val ERROR_MUST_SELECT_IMAGE = "You must select an image."
        const val GENERIC_AUTH_ERROR = "Error"
        const val PAGINATION_DONE_ERROR = "Invalid page."
        const val ERROR_CHECK_NETWORK_CONNECTION = "Check network connection."
        const val ERROR_UNKNOWN = "Unknown error"

        fun parseDetailJsonResponse(rawJson: String?): String{
            try{
                if(!rawJson.isNullOrBlank()){
                    val messageString = JSONObject(rawJson).get("message") as String
                    // localize string
                    return messageString
                }
            }catch (e: JSONException){
                Log.e(TAG, "parseDetailJsonResponse: ${e.message}")
                e.printStackTrace()
            }
            return ""
        }
    }
}