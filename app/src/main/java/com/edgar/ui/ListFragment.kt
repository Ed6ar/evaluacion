package com.edgar.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.edgar.data.UserDataHolder
import com.edgar.evaluacion.R
import com.edgar.evaluacion.databinding.FragmentListBinding
import com.edgar.ui.adapters.UserDataListAdapter
import com.edgar.ui.viewModels.ListViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

const val CREATE = "create"
const val DATA = "data"

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

        //Set RecyclerView
        adapter = UserDataListAdapter(
            onClick = {
                val emailIntent = Intent(Intent.ACTION_SEND)
                emailIntent.type = "message/rfc822"

                emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(it.email!!))
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "sa")
                emailIntent.putExtra(Intent.EXTRA_TEXT, "as")

                this@ListFragment.requireContext().startActivity(Intent.createChooser(emailIntent, "Select email app, please"))
            }
        )
        binding.rvList.adapter = adapter

        binding.etSearch.addTextChangedListener {
            listViewModel.filter(it.toString())
        }

    }

    private fun listeners() {

        listViewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.listLoader.root.visibility = View.VISIBLE
                binding.rvList.visibility = View.GONE
            } else {
                binding.rvList.visibility = View.VISIBLE
                binding.listLoader.root.visibility = View.GONE
            }
        }

        listViewModel.errorId.observe(viewLifecycleOwner) {
            if (it != null) {
                listViewModel.resetErrorMessage()
                val errorMessage = this@ListFragment.getString(it)
                Toast.makeText(this@ListFragment.requireContext(), errorMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        }

        listViewModel.usersFiltered.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }

        binding.btAdd.setOnClickListener {
            NavHostFragment.findNavController(this@ListFragment)
                .navigate(
                    R.id.action_listFragment_to_createOrUpdateFragment,
                    bundleOf(DATA to null)
                )
        }

    }

}