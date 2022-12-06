package com.edgar.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.edgar.domine.utils.emojisFilter
import com.edgar.domine.utils.validateEmail
import com.edgar.domine.utils.validatePassword
import com.edgar.evaluacion.R
import com.edgar.evaluacion.databinding.FragmentSignInBinding


const val EMAIL = "email"
const val PASSWORD = "password"

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
                    validateEmail(view = binding.etEmail, email = this)
                    if(email != this) email = this
                }
            }
            etPassword.addTextChangedListener {
                it.toString().apply {
                    validatePassword(
                        view = binding.etPassword,
                        password = this,
                        passwordConfirmation = ""
                    )
                    if(password != this) password = this
                }
            }

            //OnClick
            btSignUp.setOnClickListener {
                Log.e("email", email)

                NavHostFragment.findNavController(this@SignInFragment)
                    .navigate(
                        R.id.action_signInFragment_to_signUp,
                        bundleOf(
                            EMAIL to email,
                            PASSWORD to password
                        )
                    )
            }
            btSignIn.setOnClickListener {

            }
        }
    }

}



