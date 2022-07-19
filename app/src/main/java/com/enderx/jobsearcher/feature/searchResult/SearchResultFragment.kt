package com.enderx.jobsearcher.feature.searchResult

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.enderx.jobsearcher.R
import com.enderx.jobsearcher.core.component.recyclerView.RecyclerViewViewHolder
import com.enderx.jobsearcher.core.component.toolbar.ToolbarData
import com.enderx.jobsearcher.databinding.FragmentSearchResultBinding
import com.enderx.jobsearcher.databinding.ItemResultListBinding
import com.enderx.jobsearcher.feature.JobFragment
import com.enderx.jobsearcher.feature.home.model.response.Job
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.KClass

@AndroidEntryPoint
class SearchResultFragment : JobFragment<SearchResultViewModel, FragmentSearchResultBinding>() {
    override fun getViewModelClass(): KClass<SearchResultViewModel> = SearchResultViewModel::class

    override fun getContentLayoutId(): Int = R.layout.fragment_search_result

    override fun getToolbarData(): ToolbarData = ToolbarData(
        title = getString(R.string.search_result)
    )

    override fun onBindView() {
        with(dataBinding) {
            viewModel = this@SearchResultFragment.viewModel
            sharedViewModel = this@SearchResultFragment.sharedViewModel
            recyclerView.addItemBindings(object :
                RecyclerViewViewHolder<ItemResultListBinding, Job> {
                override fun bind(binder: ItemResultListBinding, item: Job) {
                    with(binder) {
                        dto = item
                        onItemClickListener = View.OnClickListener {
                            this@SearchResultFragment.sharedViewModel.updateSelectedJob(item)
                            findNavController().navigate(R.id.detail)
                        }
                        executePendingBindings()
                    }
                }

                override fun inflate(parent: ViewGroup): ItemResultListBinding =
                    ItemResultListBinding.inflate(layoutInflater, parent, false)
            })
            executePendingBindings()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}