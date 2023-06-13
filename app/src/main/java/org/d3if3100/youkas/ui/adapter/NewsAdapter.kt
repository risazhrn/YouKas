package org.d3if3100.youkas.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.d3if3100.youkas.R
import org.d3if3100.youkas.databinding.ItemNewsBinding
import org.d3if3100.youkas.db.entity.Article


class NewsAdapter: RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    private val data = mutableListOf<Article>()

    fun updateData(newData: List<Article>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }
    class ViewHolder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Article) = with(binding) {
            newsTitle.text = data.title
            imageView.contentDescription = data.title
            Glide.with(imageView.context).load(data.urlToImage)
                .error(R.drawable.baseline_broken_image_24).into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNewsBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size


    override fun onBindViewHolder(holder:  ViewHolder, position: Int) {
        holder.bind(data[position])
    }
}