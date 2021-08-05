package com.example.blogapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.blogapp.R
import com.example.blogapp.core.Resource
import com.example.blogapp.data.remote.auth.LoguinDataSource
import com.example.blogapp.databinding.FragmentLoginBinding
import com.example.blogapp.domain.auth.LoguinRepoImpl
import com.example.blogapp.presentation.auth.LoguinScreenViewModel
import com.example.blogapp.presentation.auth.LoguinScreenViewModelFactory
import com.google.firebase.auth.FirebaseAuth

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val firebaseAuth by lazy { FirebaseAuth.getInstance()  }
    private val viewModel by viewModels<LoguinScreenViewModel> {LoguinScreenViewModelFactory(LoguinRepoImpl(
        LoguinDataSource()
    ))  }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        isUserLoggedIn()
        doLoguin()
        goToSignUpPage()
    }

    private fun isUserLoggedIn(){
        // si el usuario esta logueado
        // solo se ejecuta si el valor no es null
        firebaseAuth.currentUser?.let {
            findNavController().navigate(R.id.action_loginFragment_to_homeScreenFragment)
        }
    }

    private fun doLoguin(){
        binding.btnSignin.setOnClickListener {
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            validateCredentials(email, password)
            signIn(email, password)
        }
    }

    private fun goToSignUpPage(){
        binding.txtSignup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun validateCredentials(email : String, password : String){
        if(email.isEmpty()){
            binding.editTextEmail.error = "Email is empty"
            return
        }
        if(password.isEmpty()){
            binding.editTextPassword.error = "Password is empty"
            return
        }
    }

    private fun signIn(email : String, password : String){
        viewModel.signIn(email, password).observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Resource.loading ->{
                    binding.progressbar.visibility = View.VISIBLE
                    binding.btnSignin.isEnabled = false
                }
                is Resource.Success ->{
                    binding.progressbar.visibility = View.GONE
                    findNavController().navigate(R.id.action_loginFragment_to_homeScreenFragment)
                }
                is Resource.Failure ->{
                    binding.progressbar.visibility = View.GONE
                    binding.btnSignin.isEnabled = true
                    Toast.makeText(requireContext(), "Error: ${result.exception}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}