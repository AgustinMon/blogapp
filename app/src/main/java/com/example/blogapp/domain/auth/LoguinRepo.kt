package com.example.blogapp.domain.auth

import com.google.firebase.auth.FirebaseUser

interface LoguinRepo {
    suspend fun signIn(email: String, password: String) : FirebaseUser?
}