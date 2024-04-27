package com.uts_rajaallifin.githubuser.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uts_rajaallifin.githubuser.databinding.ItemUserRowBinding
import com.uts_rajaallifin.githubuser.model.Items

class ListGitUserAdapter(private val listGitUser: List<Items>): RecyclerView.Adapter<ListGitUserAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(var binding: ItemUserRowBinding) : RecyclerView.ViewHolder(binding.root) { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemUserRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listGitUser[position]
        holder.binding.apply {
            Glide.with(root.context)
                .load(user.avatar_url)
                .circleCrop()
                .into(holder.binding.ivUser)
            tvUsername.text = user.login

            holder.itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(listGitUser[holder.absoluteAdapterPosition])
            }
        }
    }

    override fun getItemCount(): Int = listGitUser.size


    interface OnItemClickCallback {
        fun onItemClicked(data: Items)
    }
}