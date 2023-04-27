package com.alex.scotiagit.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.alex.scotiagit.R
import com.alex.scotiagit.databinding.FragmentHomeBinding
import com.alex.scotiagit.ui.home.details.DetailsDialogFragment
import com.alex.scotiagit.ui.home.model.HomeUiModel
import com.alex.scotiagit.ui.home.model.HomeUiState
import com.alex.scotiagit.ui.home.model.RepoUiModel
import com.alex.scotiagit.ui.home.model.UserUiModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding

    private val reposAdapter = ReposAdapter { repoItem ->
        displayDetails(repoItem)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View? = binding?.root
        binding?.reposRecycler?.adapter = reposAdapter
        setupStateObserver()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.searchButton?.setOnClickListener {
            viewModel.loadGitInfo(binding?.searchInputLayout?.editText?.text?.toString())
        }
    }

    private fun setupStateObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is HomeUiState.InitState -> showIntroMessageState()
                        is HomeUiState.FullDataLoaded -> displayDataState(uiState.data)
                        is HomeUiState.Error -> showErrorState(uiState.exception)
                        is HomeUiState.Loading -> showLoadingState()
                        is HomeUiState.EmptyReposLoaded -> showEmptyMessageState(uiState.user)
                    }
                }
            }
        }
    }

    /* region Handling UI State */

    private fun displayDetails(repoItem: RepoUiModel) {
        DetailsDialogFragment.newInstance(
            repoItem.name,
            repoItem.description,
            repoItem.updatedAt,
            repoItem.forks,
            repoItem.stargazersCount
        ).show(childFragmentManager, DetailsDialogFragment.TAG)
    }

    private fun showLoadingState() {
        clearUI()
        binding?.run {
            statusView.visibility = View.VISIBLE
            message = getString(R.string.loading)
        }
    }

    private fun showErrorState(exception: Throwable) {
        clearUI()
        binding?.run {
            statusView.visibility = View.VISIBLE
            message = exception.message
        }
    }

    private fun displayDataState(homeUiModel: HomeUiModel) {
        binding?.run {
            photoImageView.visibility = View.VISIBLE
            statusView.visibility = View.INVISIBLE
            model = homeUiModel.user
        }
        reposAdapter.setData(homeUiModel.repos)
    }

    private fun showIntroMessageState() {
        Toast.makeText(context, R.string.main_screen_intro_message, Toast.LENGTH_SHORT).show()
        binding?.run {
            statusView.visibility = View.INVISIBLE
        }
    }

    private fun showEmptyMessageState(user: UserUiModel) {
        binding?.run {
            statusView.visibility = View.VISIBLE // we can move it in extensions
            message = getString(R.string.main_screen_list_is_empty)
            model = user
        }
        reposAdapter.setData(listOf())
    }

    /* endregion */

    private fun clearUI() {
        binding?.run {
            model = UserUiModel()
            photoImageView.visibility = View.INVISIBLE
            statusView.visibility = View.INVISIBLE
        }
        reposAdapter.setData(listOf())
    }
}