package com.example.blogapp.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.blogapp.R
import com.example.blogapp.databinding.FragmentLoginBinding
import com.example.blogapp.databinding.FragmentRegisterBinding

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)
        getSignUpInfo()
    }

    private fun getSignUpInfo(){
        val username = binding.editUsername.text.toString()
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()
        val confirmPassword = binding.editTextConfirmPass.text.toString()

        binding.btnSignup.setOnClickListener {
            if(!password.equals(confirmPassword)){
                binding.editTextPassword.error = "Passwords does not match"
                binding.editTextConfirmPass.error = "Passwords does not match"
                //evita que el codigo continue
                return@setOnClickListener
            }
            else{
                Log.d("Signupdata", "data: $username, $email, $password")
            }
        }
    }
}