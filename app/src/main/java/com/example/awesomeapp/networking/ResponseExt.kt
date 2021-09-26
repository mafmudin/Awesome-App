package com.example.awesomeapp.networking

import org.json.JSONObject
import retrofit2.Response

const val KEY_EMPTY = "Data tidak ditemukan"
const val KEY_UNAUTHORIZED = "Sepertinya Anda telah logout dari Aplikasi"

fun Response<*>.getError(): Throwable {
    val errorBody = errorBody()?.string() ?: ""
    return Throwable(
        try {
            val json = JSONObject(errorBody)
            json.getString("user_message")
        } catch (e: Exception) {
            errorBody
        }
    )
}

fun Response<*>.getErrorCode(errorCode : Int): Throwable {
    val errorBody = errorBody()?.string() ?: ""
    return Throwable(
            when(errorCode){
                404 -> KEY_EMPTY
                401 -> KEY_UNAUTHORIZED
                else -> ""
            }
    )
}

fun getMessageCode(message : String): Throwable {
    return Throwable(message)
}

/**
 * https://futurestud.io/tutorials/retrofit-2-simple-error-handling
 */
fun <T> Response<T>.parse(success: (t: T) -> Unit, error: (e: Throwable) -> Unit) {
    if (isSuccessful) {
        //Error code from 200 - 299
        body()?.let {
            success(it)
        } ?: kotlin.run {
            error(getError())
        }
    } else {
        //Error code from 400 - 599
        return error(
            when (code()) {
//                404 -> Throwable("Data tidak ditemukan")
                404 -> getErrorCode(code())
                401 -> getErrorCode(code())
                else -> getError()
            }
        )
    }
}