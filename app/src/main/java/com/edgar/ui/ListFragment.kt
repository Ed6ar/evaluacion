package com.edgar.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.edgar.data.UserDataHolder
import com.edgar.evaluacion.R
import com.edgar.evaluacion.databinding.FragmentListBinding
import com.edgar.ui.adapters.UserDataListAdapter
import com.edgar.ui.viewModels.ListViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

const val CREATE = "create"
const val ACTION = "action"

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private lateinit var adapter: UserDataListAdapter
    private val listViewModel by activityViewModel<ListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)

        listViewModel.tryToGetUsersList()
        setContent()
        listeners()

        return binding.root
    }

    private fun setContent() {
        //Set label
        this@ListFragment.requireActivity().title =
            this@ListFragment.getString(R.string.List, UserDataHolder.email)

        adapter = UserDataListAdapter()
        binding.rvList.adapter = adapter
    }

    private fun listeners() {
        /*
        listViewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.listLoader.root.visibility = View.VISIBLE
                binding.rvList.visibility = View.GONE
            } else {
                binding.rvList.visibility = View.VISIBLE
                binding.listLoader.root.visibility = View.GONE
            }
        }
        */
        listViewModel.errorId.observe(viewLifecycleOwner) {
            if (it != null) {
                listViewModel.resetErrorMessage()
                val errorMessage = this@ListFragment.getString(it)
                Toast.makeText(this@ListFragment.requireContext(), errorMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        }
        listViewModel.users.observe(viewLifecycleOwner) {
            Log.e("datosEnList","data: $it")

            adapter.setData(it)
        }

    }

}