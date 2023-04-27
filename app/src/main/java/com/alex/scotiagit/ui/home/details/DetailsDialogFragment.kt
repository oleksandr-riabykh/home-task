package com.alex.scotiagit.ui.home.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alex.scotiagit.databinding.DialogDetailsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsDialogFragment : BottomSheetDialogFragment() {

    companion object {
        fun newInstance(
            name: String,
            description: String,
            updatedAt: String,
            forks: Int,
            stargazersCount: Int
        ) =
            DetailsDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_NAME, name)
                    putString(ARG_DESCRIPTION, description)
                    putString(ARG_UPDATED, updatedAt)
                    putInt(ARG_FORKS, forks)
                    putInt(ARG_STARGAZERS, stargazersCount)
                }
            }

        private const val ARG_NAME = "arg_name"
        private const val ARG_DESCRIPTION = "arg_description"
        private const val ARG_UPDATED = "arg_updated"
        private const val ARG_FORKS = "arg_forks"
        private const val ARG_STARGAZERS = "arg_stargazers"
        const val TAG = "DetailDialogFragment"
    }

    private var _binding: DialogDetailsBinding? = null

    private val binding get() = _binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.name = arguments?.getString(ARG_NAME)
        binding?.description = arguments?.getString(ARG_DESCRIPTION)
        binding?.updated = arguments?.getString(ARG_UPDATED)
        binding?.forks = arguments?.getInt(ARG_FORKS)
        binding?.stargazers = arguments?.getInt(ARG_STARGAZERS)
        binding?.closeButton?.setOnClickListener {
            dismissAllowingStateLoss()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = DialogDetailsBinding.inflate(inflater, container, false)
        return binding?.root
    }
}