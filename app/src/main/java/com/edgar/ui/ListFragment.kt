package com.edgar.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.edgar.data.UserDataHolder
import com.edgar.evaluacion.R
import com.edgar.evaluacion.databinding.FragmentListBinding

const val CREATE = "create"
const val ACTION = "action"

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)

        setContent()

        return binding.root
    }

    private fun setContent(){
        //Set label
        this@ListFragment.requireActivity().title = this@ListFragment.getString(R.string.List, UserDataHolder.email)
    }

}