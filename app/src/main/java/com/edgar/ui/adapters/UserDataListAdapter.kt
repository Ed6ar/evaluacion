package com.edgar.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.edgar.data.services.usersList.UsersListData
import com.edgar.evaluacion.R
import com.edgar.evaluacion.databinding.UserCardBinding

class UserDataListAdapter: RecyclerView.Adapter<UserDataListAdapter.ViewHolder>(){

    private var usersList: List<UsersListData> = listOf()

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val binding = UserCardBinding.bind(view)

        fun setContent(dataItem: UsersListData) {
            binding.apply {

                if(dataItem.first_name != null && dataItem.last_name != null){
                    tvName.visibility = View.VISIBLE
                    tvName.text = dataItem.first_name.plus(" ").plus(dataItem.last_name)
                }else{
                    tvName.visibility = View.GONE
                }
                if(dataItem.email != null){
                    tvEmail.visibility = View.VISIBLE
                    tvEmail.text = dataItem.email
                }else{
                    tvEmail.visibility = View.GONE
                }

                Glide.with(this.root)
                    .load(dataItem.avatar)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(ivUser)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_card, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setContent(usersList[position])
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    internal fun setData(usersList: List<UsersListData>){
        Log.e("datosEnList","dentro del adapter data: $usersList")

        this.usersList = usersList
        this.notifyDataSetChanged()
    }

}