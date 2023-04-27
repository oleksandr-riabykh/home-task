package com.alex.scotiagit.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alex.scotiagit.R
import com.alex.scotiagit.databinding.ItemGitRepoBinding
import com.alex.scotiagit.ui.home.model.RepoUiModel

class ReposAdapter(
    private val onClickItem: (item: RepoUiModel) -> Unit = {}
) : RecyclerView.Adapter<ReposAdapter.RepoViewHolder>() {

    private var mListOfItems: ArrayList<RepoUiModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RepoViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_git_repo, parent, false)
    )

    fun setData(items: List<RepoUiModel>) {
        val diffCallback = CollectionsDiffCallback(mListOfItems, items)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        mListOfItems.clear()
        mListOfItems.addAll(items)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = mListOfItems.size

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val item = mListOfItems[position]
        holder.bind(item) { onClickItem(it) }
    }

    class RepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: ItemGitRepoBinding = ItemGitRepoBinding.bind(view)
        fun bind(
            item: RepoUiModel,
            onClickItem: (item: RepoUiModel) -> Unit,
        ) {
            binding.model = item
            binding.root.setOnClickListener { onClickItem(item) }
        }
    }

    class CollectionsDiffCallback(
        private val oldList: List<RepoUiModel>,
        private val newList: List<RepoUiModel>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].name === newList[newItemPosition].name
        }

        /**
         * We can customize checking content
         */
        override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
            val (name, description, updatedAt) = oldList[oldPosition]
            val (name1, description1, updatedAt1) = newList[newPosition]

            return updatedAt == updatedAt1 && name == name1 && description == description1
        }
    }
}

