package com.architectcoders.aacboard.domain.data

sealed interface Error {
    class Server(val code: Int) : Error
    object NoMatchFound: Error
    object Connectivity : Error
    class Unknown(val message: String) : Error
}