package com.architectcoders.aacboard.domain.data

//https://stackoverflow.com/a/59422833
sealed class Response <out T> {
    class Success<T>(val result: T): Response<T>()
    class Failure (val error: Error): Response<Nothing>()
}