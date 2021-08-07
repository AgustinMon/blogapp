package com.example.blogapp.core

sealed class Result<out T> {
    class loading<out T>: Result<T>()
    data class Success<out T>(val data: T): Result<T>()
    data class Failure(val exception: Exception): Result<Nothing>()
}