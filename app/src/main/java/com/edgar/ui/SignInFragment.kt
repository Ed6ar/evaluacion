package com.edgar.ui

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.edgar.domine.utils.emojisFilter
import com.edgar.evaluacion.R
import com.edgar.evaluacion.databinding.FragmentSignInBinding


const val EMAIL = "email"

class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private var email: String = ""
    private var password: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        setContent()
        return binding.root
    }

    private fun setContent() {
        // Set title
        this.requireActivity().title = this.getString(R.string.SignIn)

        binding.apply {

            etEmail.filters = arrayOf(emojisFilter())
            etPassword.filters = arrayOf(emojisFilter())

            //TextFields
            etEmail.addTextChangedListener {
                it.toString().apply {
                    validateEmail(this)
                    if(email != this) email = this
                }
            }
            etPassword.addTextChangedListener {
                it.toString().apply {
                    validatePassword(this)
                    if(password != this) password = this
                }
            }

            //OnClick
            btSignUp.setOnClickListener {
                Log.e("email", email)

                NavHostFragment.findNavController(this@SignInFragment)
                    .navigate(
                        R.id.action_signInFragment_to_signUp,
                        bundleOf(EMAIL to email)
                    )
            }
            btSignIn.setOnClickListener {

            }
        }
    }

    private fun validateEmail(email: String) {
        binding.etEmail.error = if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches())
            this.getString(R.string.InvalidEmail)
        else ""
    }

    private fun validatePassword(password: String) {
        binding.etPassword.error = if(password.isEmpty())
            this.getString(R.string.InvalidPassword)
        else ""
    }

}



