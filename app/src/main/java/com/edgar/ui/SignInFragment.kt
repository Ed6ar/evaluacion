package com.edgar.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.edgar.data.UserData
import com.edgar.data.services.signIn.SignInRequest
import com.edgar.ui.utils.emojisFilter
import com.edgar.ui.utils.validateEmail
import com.edgar.ui.utils.validatePassword
import com.edgar.evaluacion.R
import com.edgar.evaluacion.databinding.FragmentSignInBinding
import com.edgar.ui.viewModels.MainViewModel
import com.edgar.ui.viewModels.SignInViewModel
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.activityViewModel

const val EMAIL = "email"
const val PASSWORD = "password"

class SignInFragment : Fragment() {

    private val signInViewModel by activityViewModel<SignInViewModel>()
    private val mainViewModel by activityViewModel<MainViewModel>()

    private lateinit var binding: FragmentSignInBinding
    private var emailHolder: String = ""
    private var passwordHolder: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        setContent()
        listeners()
        return binding.root
    }

    private fun setContent() {
        // Set title
        this.requireActivity().title = this.getString(R.string.SignIn)

        binding.apply {

            etEmail.filters = arrayOf(emojisFilter())
            etPassword.filters = arrayOf(emojisFilter())

            etEmail.addTextChangedListener {
                it.toString().apply {
                    validateEmail(view = binding.etEmail, email = this)
                    if (emailHolder != this) emailHolder = this
                }
                validateButtonState()
            }
            etPassword.addTextChangedListener {
                it.toString().apply {
                    validatePassword(
                        view = binding.etPassword,
                        password = this
                    )
                    if (passwordHolder != this) passwordHolder = this
                }
                validateButtonState()
            }

            //OnClick
            btSignUp.setOnClickListener {
                Log.e("email", emailHolder)

                NavHostFragment.findNavController(this@SignInFragment)
                    .navigate(
                        R.id.action_signInFragment_to_signUp,
                        bundleOf(
                            EMAIL to emailHolder,
                            PASSWORD to passwordHolder
                        )
                    )
            }
            btSignIn.setOnClickListener {
                signInViewModel.tryToSignIn(
                    signInRequest = SignInRequest(
                        email = emailHolder,
                        password = passwordHolder
                    ),
                    onSuccess = { token ->
                        saveUser(userData = UserData(
                            token = token,
                            email = emailHolder
                        ))
                    }
                )
            }
        }
    }

    private fun listeners() {
        signInViewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.pbLoader.root.visibility = View.VISIBLE
                binding.llContent.visibility = View.GONE
            } else {
                binding.llContent.visibility = View.VISIBLE
                binding.pbLoader.root.visibility = View.GONE
            }
        }
        signInViewModel.errorId.observe(viewLifecycleOwner) {
            if(it != null){
                signInViewModel.resetErrorMessage()
                val errorMessage = this@SignInFragment.getString(it)
                Toast.makeText(this@SignInFragment.requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateButtonState(){
        binding.btSignIn.isEnabled = binding.etEmail.error.isNullOrEmpty() && binding.etPassword.error.isNullOrEmpty()
    }

    private fun saveUser(userData: UserData){
        val sharedPref = this.requireActivity().getPreferences(Context.MODE_PRIVATE)
        with(sharedPref.edit()){

            val userDataAsString = Gson().toJson(userData)
            mainViewModel.navigateToUserActivity(userData = userDataAsString)

            putString(this@SignInFragment.getString(R.string.userData), userDataAsString)
            apply()
        }
    }

}



