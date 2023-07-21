package com.apex.codeassesment.ui.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.apex.codeassesment.data.model.User
import com.apex.codeassesment.databinding.ItemUsersBinding
import com.sample.extn.loadImage


@SuppressLint("NotifyDataSetChanged")
internal class UsersAdapter(private val itemClickListener: (User) -> Unit) :
    Adapter<UsersAdapter.ItemHolder>() {

    private var userList = mutableListOf<User>()

    inner class ItemHolder(private val binding: ItemUsersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(itemdata: User) {
            binding.userNameTextView.text ="${itemdata.name?.first} ${itemdata.name?.last}"
            binding.userImageView.loadImage(itemdata.picture?.thumbnail?:"")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder =
        ItemHolder(ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bindData(userList[position])
        holder.itemView.setOnClickListener {
            itemClickListener(userList[position])
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun updateList(items: List<User>) {
        userList = items.toMutableList()
        notifyDataSetChanged()
    }


}
