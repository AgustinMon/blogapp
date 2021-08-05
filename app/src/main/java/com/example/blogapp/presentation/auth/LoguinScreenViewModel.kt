package com.example.blogapp.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.blogapp.core.Resource
import com.example.blogapp.domain.auth.LoguinRepo
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class LoguinScreenViewModel(private val repo: LoguinRepo) : ViewModel() {
    fun signIn(email: String, password: String) = liveData(Dispatchers.IO){
        emit(Resource.loading())
        try {
            emit(Resource.Success(repo.signIn(email, password)))
        }catch (e : Exception){
            emit(Resource.Failure(e))
        }
    }
}

class LoguinScreenViewModelFactory(private val repo: LoguinRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoguinScreenViewModel(repo) as T
    }

}