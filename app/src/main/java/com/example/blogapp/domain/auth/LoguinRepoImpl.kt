package com.example.blogapp.domain.auth

import com.example.blogapp.data.remote.auth.LoguinDataSource
import com.google.firebase.auth.FirebaseUser

class LoguinRepoImpl(private val dataSource : LoguinDataSource) : LoguinRepo {
    override suspend fun signIn(email: String, password: String) = dataSource.signIn(email, password)
}