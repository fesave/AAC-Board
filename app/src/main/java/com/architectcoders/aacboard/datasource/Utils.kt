package com.architectcoders.aacboard.datasource

import android.content.Context
import com.architectcoders.aacboard.R
import com.architectcoders.aacboard.domain.data.Response
import com.architectcoders.aacboard.domain.data.Response.Failure
import com.architectcoders.aacboard.domain.data.Response.Success

import java.io.IOException
import com.architectcoders.aacboard.domain.data.Error
import com.architectcoders.aacboard.domain.data.Error.*
import retrofit2.HttpException

private const val HTTP_NOT_FOUND =404

fun Error.getMessage(context: Context) : String {
   return when (this) {
            is Server -> context.getString(R.string.serverError, code)
            is NoMatchFound -> context.getString(R.string.noMatchFoundError)
            is Connectivity -> context.getString(R.string.connectivityError)
            is Unknown -> message.ifEmpty {context.getString(R.string.unknownError)}
        }
}

fun Throwable.toError(): Error = when (this) {
    is IOException -> Connectivity
    is HttpException -> if (code()== HTTP_NOT_FOUND) NoMatchFound
                        else Server(code())
    else -> Unknown(message ?: "")
}

inline fun <T> tryCall(action:  () -> T): Response<T> = try {
    Success(action())
} catch (e: Exception) {
    Failure(e.toError())
}