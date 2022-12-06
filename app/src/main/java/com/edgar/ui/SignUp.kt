package com.edgar.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.edgar.evaluacion.R
import com.edgar.evaluacion.databinding.FragmentSignUpBinding

class SignUp : Fragment() {

    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)

        setContent()

        return binding.root
    }

    private fun setContent() {
        // Set title
        this.requireActivity().title = this.getString(R.string.SignUp)
    }
}