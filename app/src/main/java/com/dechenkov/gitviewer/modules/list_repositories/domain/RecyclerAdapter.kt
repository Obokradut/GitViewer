package com.dechenkov.gitviewer.modules.list_repositories.domain

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dechenkov.gitviewer.R
import com.dechenkov.gitviewer.databinding.RepositoryItemBinding
import com.dechenkov.gitviewer.shared.models.Repo

class RecyclerAdapter (
    private val onRepositoryClick: NavigationFunction
) : RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder>()/*RecyclerView.Adapter<RepositoryItemViewHolder>()*/ {
    var repositoriesList: List<Repo> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class RecyclerHolder(item: View) : RecyclerView.ViewHolder(item){
        val binding = RepositoryItemBinding.bind(item)
        fun bind(repo: Repo) = with(binding){
            repositoryTitle.text = repo.name
            repositoryLanguage.text = repo.language
            repositoryDescription.text = repo.description
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.repository_item,parent,false)
        return RecyclerHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
        holder.bind(repositoriesList[position])
        val item = repositoriesList[position]
        with(holder.binding) {
            root.setOnClickListener {
                onRepositoryClick(item.name, item.owner)
            }
            repositoryLanguage.setTextColor(item.color ?: Color.WHITE)
        }
    }

    override fun getItemCount(): Int {
        return repositoriesList.size
    }
}

typealias NavigationFunction = (repository: String, owner: String) -> Unit