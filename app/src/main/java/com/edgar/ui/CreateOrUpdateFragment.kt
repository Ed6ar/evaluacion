package com.edgar.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.edgar.evaluacion.R
import com.edgar.evaluacion.databinding.FragmentCreateOrUpdateBinding

class CreateOrUpdateFragment : Fragment() {

    private lateinit var binding: FragmentCreateOrUpdateBinding
    private lateinit var action: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        action = arguments?.getString(ACTION)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateOrUpdateBinding.inflate(inflater, container, false)

        setContent()

        return binding.root
    }

    private fun setContent(){
        // Set label
        val labelId = if(action == CREATE) R.string.Create else R.string.Update
        this.requireActivity().title = this.getString(labelId)


    }
}