package com.edgar.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.edgar.evaluacion.R
import com.edgar.evaluacion.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding

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
    }
}