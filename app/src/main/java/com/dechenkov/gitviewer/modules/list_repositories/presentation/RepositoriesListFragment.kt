package com.dechenkov.gitviewer.modules.list_repositories.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.dechenkov.gitviewer.R
import com.dechenkov.gitviewer.databinding.FragmentListRepositoriesBinding
import com.dechenkov.gitviewer.modules.list_repositories.domain.RecyclerAdapter
import com.dechenkov.gitviewer.modules.list_repositories.domain.RepositoriesListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RepositoriesListFragment : Fragment() {
    private lateinit var binding: FragmentListRepositoriesBinding
    private val repoListViewModel: RepositoriesListViewModel by viewModels()

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListRepositoriesBinding.inflate(inflater, container, false)

        val adapter = RecyclerAdapter(onRepositoryClick = repoListViewModel::onRepositoryClick)
        binding.reposRecycler.adapter = adapter

        val decorator = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL).apply {
            setDrawable(resources.getDrawable(R.drawable.divider))
        }
        binding.reposRecycler.addItemDecoration(decorator)


        repoListViewModel.state.observe(viewLifecycleOwner) {
            with(binding) {
                reposProgressbar.visibility =
                    if (it is RepositoriesListViewModel.State.Loading) View.VISIBLE
                    else View.GONE

                reposEmptyState.root.visibility =
                    if (it is RepositoriesListViewModel.State.Empty) View.VISIBLE
                    else View.GONE

                reposConnectionErrorState.root.visibility =
                    if (it is RepositoriesListViewModel.State.ConnectionError) View.VISIBLE
                    else View.GONE

                if (it is RepositoriesListViewModel.State.Loaded) {
                    reposRecycler.visibility = View.VISIBLE
                    adapter.repositoriesList = it.repos as ArrayList
                    it.apply {
                        reposRecycler.adapter = adapter
                    }
                } else {
                    reposErrorState.root.visibility = View.GONE
                }

                if (it is RepositoriesListViewModel.State.Error) {
                    reposEmptyState.root.visibility = View.VISIBLE
                    reposErrorState.errorDescription.text = it.error
                } else {
                    reposErrorState.root.visibility = View.GONE
                }

                reposEmptyState.refreshButton.setOnClickListener {
                    repoListViewModel.stateUpdate()
                }
                reposConnectionErrorState.refreshButton.setOnClickListener {
                    repoListViewModel.stateUpdate()
                }
                reposErrorState.refreshButton.setOnClickListener {
                    repoListViewModel.stateUpdate()
                }
            }
        }
        return binding.root
    }
}
