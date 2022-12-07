package com.edgar.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.edgar.data.services.usersList.UsersListData
import com.edgar.evaluacion.R
import com.edgar.evaluacion.databinding.FragmentCreateOrUpdateBinding
import com.edgar.ui.utils.emojisFilter
import com.edgar.ui.utils.validateEmail
import com.google.gson.Gson

class CreateOrUpdateFragment : Fragment() {

    private lateinit var binding: FragmentCreateOrUpdateBinding
    private var usersListData: UsersListData? = null
    private var nameHolder: String = ""
    private var lastNameHolder: String = ""
    private var emailHolder: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.getString(DATA)?.let {
            usersListData = Gson().fromJson(it, UsersListData::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateOrUpdateBinding.inflate(inflater, container, false)

        setContent()
        listeners()

        return binding.root
    }

    private fun setContent(){
        // Set label
        val labelId = if(usersListData != null) {
            binding.apply {
                btAddRegister.visibility = View.INVISIBLE
                btEdit.visibility = View.VISIBLE
            }
            R.string.Update
        }else{
            binding.apply {
                btEdit.visibility = View.INVISIBLE
                btAddRegister.visibility = View.VISIBLE
            }
            R.string.Create
        }
        this.requireActivity().title = this.getString(labelId)

        binding.apply {

            etName.filters = arrayOf(emojisFilter())
            etLastName.filters = arrayOf(emojisFilter())
            etEmailToUpdate.filters = arrayOf(emojisFilter())

            etName.addTextChangedListener {
                etName.error = if(it.toString().isEmpty()) {
                    this@CreateOrUpdateFragment.getString(R.string.InvalidName)
                } else {
                    null
                }
                nameHolder = it.toString()
                validateButtonState()
            }

            etLastName.addTextChangedListener {
                etLastName.error = if(it.toString().isEmpty()) {
                    this@CreateOrUpdateFragment.getString(R.string.InvalidLastName)
                } else {
                    null
                }
                lastNameHolder = it.toString()
                validateButtonState()
            }

            etEmailToUpdate.addTextChangedListener {
                validateEmail(view = etEmailToUpdate, email = it.toString())
                emailHolder = it.toString()
                validateButtonState()
            }

        }
    }

    private fun validateButtonState(){
        if(usersListData != null) {
            binding.btEdit.isEnabled = nameHolder.isNotEmpty() && lastNameHolder.isNotEmpty() && binding.etEmailToUpdate.error.isNullOrEmpty()
        }else{
            binding.btAddRegister.isEnabled = nameHolder.isNotEmpty() && lastNameHolder.isNotEmpty() && binding.etEmailToUpdate.error.isNullOrEmpty()
        }
    }

    private fun listeners(){
        binding.btEdit.setOnClickListener {
            //TODO
        }

        binding.btAddRegister.setOnClickListener {
            //TODO
        }
    }
}