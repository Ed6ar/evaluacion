package com.edgar.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.edgar.domine.utils.emojisFilter
import com.edgar.domine.utils.validateEmail
import com.edgar.domine.utils.validatePassword
import com.edgar.evaluacion.R
import com.edgar.evaluacion.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private var email: String = ""
    private var password: String = ""
    private var passwordConfirmation: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        setContent(
            email = arguments?.getString(EMAIL),
            password = arguments?.getString(PASSWORD)
        )

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
                    if(email != this) this@SignUpFragment.email = this
                }
            }
            etPassword.addTextChangedListener {
                it.toString().apply {
                    if(password != this) this@SignUpFragment.password = this
                    validatePassword(
                        view = binding.etPassword,
                        password = this,
                        passwordConfirmation = passwordConfirmation
                    )
                }
            }
            etConfirmPassword.addTextChangedListener {
                it.toString().apply {
                    if(passwordConfirmation != this) passwordConfirmation = this
                    validatePassword(
                        view = binding.etConfirmPassword,
                        password = this,
                        passwordConfirmation = password ?: ""
                    )
                }
            }

            btContinue.setOnClickListener {
                //TODO
            }

        }
    }
}