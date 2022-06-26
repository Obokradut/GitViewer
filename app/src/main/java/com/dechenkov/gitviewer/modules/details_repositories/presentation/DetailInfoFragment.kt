package com.dechenkov.gitviewer.modules.details_repositories.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import br.tiagohm.markdownview.css.styles.Github
import com.dechenkov.gitviewer.R
import com.dechenkov.gitviewer.databinding.FragmentDetailsRepositoriesBinding
import com.dechenkov.gitviewer.modules.details_repositories.domain.DetailInfoViewModel
import com.dechenkov.gitviewer.modules.details_repositories.domain.DetailInfoViewModel.NavConsts.REPOSITORY_NAME
import com.dechenkov.gitviewer.modules.details_repositories.domain.DetailInfoViewModel.NavConsts.REPOSITORY_OWNER
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailInfoFragment : Fragment() {
    private lateinit var binding: FragmentDetailsRepositoriesBinding
    private val detailInfoViewModel: DetailInfoViewModel by viewModels()

    private val repoTitle: String
        get() = requireNotNull(requireArguments().getString("repoNameKey"))

    private val Fragment.supportActionBar : ActionBar?
        get() {
            return  try {
                (requireActivity() as AppCompatActivity).supportActionBar
            }
            catch (ex: Exception){
                null
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDetailsRepositoriesBinding.inflate(inflater, container, false)
        val css = Github()
        with(css) {
            addRule("body","background-color: ${getString(R.color.git_background).removeRange(1,3)}")
            addRule("body","color: white")
            addRule("body","margin: 0em")
        }
        binding.readmeView.addStyleSheet(css)
        detailInfoViewModel.state.observe(viewLifecycleOwner) { detailState ->
            with(binding) {
                supportActionBar?.title = repoTitle
                repoProgressbar.visibility =
                    if (detailState is DetailInfoViewModel.State.Loading) View.VISIBLE
                    else View.GONE

                if (detailState is DetailInfoViewModel.State.Loaded) {
                    detailsScrollContainer.visibility = View.VISIBLE
                    repositoryLink.text = detailState.githubRepo.url
                    repositoryLink.setOnClickListener {
                        detailInfoViewModel.onUrlClick(detailState.githubRepo.url)
                    }
                    licenseName.text = detailState.githubRepo.license
                    starsCount.text = detailState.githubRepo.stargazersCount.toString()
                    forksCount.text = detailState.githubRepo.forksCount.toString()
                    watchesCount.text = detailState.githubRepo.watchersCount.toString()

                    readmeProgressbar.visibility =
                        if (detailState.readmeState is DetailInfoViewModel.ReadmeState.Loading) View.VISIBLE
                        else View.GONE
                    readmeView.visibility =
                        when (detailState.readmeState){
                            is DetailInfoViewModel.ReadmeState.Loaded -> View.VISIBLE
                            is DetailInfoViewModel.ReadmeState.Empty -> View.VISIBLE
                            else -> View.GONE
                        }

                    readmeView.loadMarkdown(
                        when (detailState.readmeState){
                            is DetailInfoViewModel.ReadmeState.Loaded -> detailState.readmeState.markdown
                            is DetailInfoViewModel.ReadmeState.Empty -> "No README.md"
                            else -> ""
                        }
                    )
                }
                else if (detailState is DetailInfoViewModel.State.ConnectionError) {
                    repoConnectionErrorState.root.visibility = View.VISIBLE
                }
                else if (detailState is DetailInfoViewModel.State.Error) {
                    repoErrorState.root.visibility = View.VISIBLE
                    repoErrorState.errorDescription.text = detailState.error
                }
                else {
                    detailsScrollContainer.visibility = View.GONE
                    repositoryLink.text = null
                    licenseName.text = null
                    starsCount.text = null
                    forksCount.text = null
                    watchesCount.text = null

                    repoConnectionErrorState.root.visibility = View.GONE

                    repoErrorState.root.visibility = View.GONE
                    repoErrorState.errorDescription.text = null
                }
            }
        }
        return binding.root
    }

    companion object {
        fun createArguments(repoOwner: String, repoName: String): Bundle {
            return bundleOf(
                REPOSITORY_OWNER to repoOwner,
                REPOSITORY_NAME to repoName
            )
        }
    }
}