package com.example.blogapp.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.blogapp.R
import com.example.blogapp.data.remote.auth.AuthDataSource
import com.example.blogapp.databinding.FragmentRegisterBinding
import com.example.blogapp.domain.auth.AuthRepoImpl
import com.example.blogapp.presentation.auth.AuthViewModel
import com.example.blogapp.presentation.auth.AuthViewModelFactory
import com.example.blogapp.core.Result

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(
            AuthRepoImpl(
        AuthDataSource()
    )
        )  }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)
        signUp()
    }

    private fun signUp(){
        binding.btnSignup.setOnClickListener {
            val username = binding.editUsername.text.toString().trim()
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            val confirmPassword = binding.editTextConfirmPass.text.toString().trim()
            //evita que el codigo continue y vuelve al punto de partida return@onClickListener
            if (validateUserData(password, confirmPassword, username, email)) return@setOnClickListener
            createuser(email, password, username)
        }
    }

    private fun createuser(email: String, password: String, username: String) {
        viewModel.signUp(email,password, username).observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Result.loading ->{
                    binding.progressbar.visibility = View.VISIBLE
                    binding.btnSignup.isEnabled = false
                }
                is Result.Success ->{
                    binding.progressbar.visibility = View.GONE
                    findNavController().navigate(R.id.action_registerFragment_to_homeScreenFragment)
                }
                is Result.Failure ->{
                    binding.progressbar.visibility = View.GONE
                    binding.btnSignup.isEnabled = true
                    Toast.makeText(requireContext(), "Error: ${result.exception}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun validateUserData(
        password: String,
        confirmPassword: String,
        username: String,
        email: String
    ) : Boolean {
        if (!password.equals(confirmPassword)) {
            binding.editTextPassword.error = "Passwords does not match"
            binding.editTextConfirmPass.error = "Passwords does not match"

            if (username.isEmpty()) {
                binding.editUsername.error = "Username is empty."
            }
            if (email.isEmpty()) {
                binding.editTextEmail.error = "Email is empty."
            }
            if (password.isEmpty()) {
                binding.editTextPassword.error = "Password is empty."
            }
            if (confirmPassword.isEmpty()) {
                binding.editTextConfirmPass.error = "Password confirmation is empty."
            }
        } else {
            Log.d("Signupdata", "data: $username, $email, $password")
            return false
        }
        return true
    }
}