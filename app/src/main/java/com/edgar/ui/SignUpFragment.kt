package com.edgar.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.edgar.data.UserData
import com.edgar.data.services.register.RegisterRequest
import com.edgar.domine.utils.emojisFilter
import com.edgar.domine.utils.validateEmail
import com.edgar.domine.utils.validatePassword
import com.edgar.evaluacion.R
import com.edgar.evaluacion.databinding.FragmentSignUpBinding
import com.edgar.ui.viewModels.MainViewModel
import com.edgar.ui.viewModels.SignUpViewModel
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class SignUpFragment : Fragment() {

    private val signUpViewModel by activityViewModel<SignUpViewModel>()
    private val mainViewModel by activityViewModel<MainViewModel>()

    private lateinit var binding: FragmentSignUpBinding
    private var emailHolder: String = ""
    private var passwordHolder: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)

        setContent(
            email = arguments?.getString(EMAIL),
            password = arguments?.getString(PASSWORD)
        )

        listeners()

        return binding.root
    }

    private fun setContent(email: String?, password: String?) {
        // Set title
        this.requireActivity().title = this.getString(R.string.SignUp)

        binding.apply {

            etEmail.apply {
                filters = arrayOf(emojisFilter())
                setText(email)
            }
            etPassword.apply {
                filters = arrayOf(emojisFilter())
                setText(password)
            }

            //TextFields
            etEmail.addTextChangedListener {
                it.toString().apply {
                    validateEmail(view = binding.etEmail, email = this)
                    if(email != this) emailHolder = this
                }
                validateButtonState()
            }
            etPassword.addTextChangedListener {
                it.toString().apply {
                    if(password != this) passwordHolder = this
                    validatePassword(
                        view = binding.etPassword,
                        password = this
                    )
                }
                validateButtonState()
            }

            btContinue.setOnClickListener {
                signUpViewModel.tryToRegisterUser(
                    registerRequest = RegisterRequest(
                        email = emailHolder,
                        password = passwordHolder
                    ),
                    onSuccess = { token ->
                        saveUser(userData = UserData(
                            token = token,
                            email = emailHolder
                        ))

                        mainViewModel.navigateToUserActivity(state = true)
                    }
                )
            }

        }
    }

    private fun listeners(){
        signUpViewModel.loading.observe(viewLifecycleOwner) {
            if(it){
                binding.pbLoader.root.visibility = View.VISIBLE
                binding.llContent.visibility = View.GONE
            } else {
                binding.llContent.visibility = View.VISIBLE
                binding.pbLoader.root.visibility = View.GONE
            }
        }

        signUpViewModel.errorId.observe(viewLifecycleOwner) {
            if(it != null){
                signUpViewModel.resetErrorMessage()
                val errorMessage = this@SignUpFragment.getString(it)
                Toast.makeText(this@SignUpFragment.requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateButtonState(){
        binding.btContinue.isEnabled = binding.etEmail.error.isNullOrEmpty() && binding.etPassword.error.isNullOrEmpty()
    }

    private fun saveUser(userData: UserData){
        val sharedPref = this.requireActivity().getPreferences(Context.MODE_PRIVATE)
        with(sharedPref.edit()){
            putString(this@SignUpFragment.getString(R.string.userData), Gson().toJson(userData))
            apply()
        }
    }
}