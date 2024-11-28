package com.dicoding.picodiploma.loginwithanimation.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.data.response.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.databinding.ItemListBinding

class StoryAdapter(private val onClick: (ListStoryItem) -> Unit) : RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {
    private val list = ArrayList<ListStoryItem>()

    fun setStories(stories: List<ListStoryItem>) {
        list.clear()
        list.addAll(stories)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val story = list[position]
        holder.bind(story, onClick)
    }

    override fun getItemCount(): Int = list.size

    inner class StoryViewHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(story: ListStoryItem, onClick: (ListStoryItem) -> Unit) {
            binding.tvName.text = story.name
            binding.tvDesc.text = story.description
            Glide.with(binding.root.context)
                .load(story.photoUrl)
                .into(binding.imgItemPhoto)

            binding.root.setOnClickListener {
                onClick(story)
            }
        }


    }
}
