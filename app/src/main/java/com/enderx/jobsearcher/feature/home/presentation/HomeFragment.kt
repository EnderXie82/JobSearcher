package com.enderx.jobsearcher.feature.home.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController
import com.enderx.jobsearcher.BuildConfig
import com.enderx.jobsearcher.R
import com.enderx.jobsearcher.core.component.recyclerView.RecyclerViewViewHolder
import com.enderx.jobsearcher.core.component.toolbar.ToolbarData
import com.enderx.jobsearcher.databinding.FragmentHomeBinding
import com.enderx.jobsearcher.databinding.ItemHistoryListBinding
import com.enderx.jobsearcher.feature.JobFragment
import com.enderx.jobsearcher.feature.home.model.data.History
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.KClass

@AndroidEntryPoint
class HomeFragment : JobFragment<HomeViewModel, FragmentHomeBinding>(),
    TextWatcher,
    View.OnClickListener {

    override fun getToolbarData(): ToolbarData = ToolbarData(
        title = getString(R.string.app_name),
        showNavigationIcon = false
    )

    override fun getViewModelClass(): KClass<HomeViewModel> = HomeViewModel::class

    override fun getContentLayoutId(): Int = R.layout.fragment_home

    override fun onBindView() {
        with(dataBinding) {
            viewModel = this@HomeFragment.viewModel
            sharedViewModel = this@HomeFragment.sharedViewModel
            etJob.addTextChangedListener(this@HomeFragment)
            onClickSearchBtn = this@HomeFragment
            recyclerView.addItemBindings(object :
                RecyclerViewViewHolder<ItemHistoryListBinding, History> {
                override fun bind(binder: ItemHistoryListBinding, item: History) {
                    with(binder) {
                        dto = item
                        onItemClickListener = View.OnClickListener {
                            etJob.setText(item.text)
                        }
                        executePendingBindings()
                    }
                }

                override fun inflate(parent: ViewGroup): ItemHistoryListBinding =
                    ItemHistoryListBinding.inflate(layoutInflater, parent, false)
            })
            tvVersion.text = String.format(getString(R.string.version), BuildConfig.VERSION_NAME)
            executePendingBindings()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.checkFirstLaunch()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.isFirstLaunchLiveData.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.splash)
        }
        viewModel.navigateToResult.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.search_result)
        }
        viewModel.onSearchSuccess.observe(viewLifecycleOwner) {
            dataBinding.btnSearch.isEnabled = true
            if (it != null) {
                sharedViewModel.updateSearchResult(it)
            }
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun afterTextChanged(text: Editable?) {
        text?.let {
            if (text.isNotEmpty()) {
                viewModel.setSearchBtnEnable(true)
            } else {
                viewModel.setSearchBtnEnable(false)
            }
        }
    }

    override fun onClick(text: View?) {
        closeKeyBoard()
        with(dataBinding) {
            btnSearch.isEnabled = false
            etJob.text.toString().let {
                viewModel?.fetchJobs(it)
                sharedViewModel?.handleHistoryList(it)
            }
        }
    }

    private fun closeKeyBoard() {
        requireActivity().currentFocus?.let { view ->
            val imm =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}