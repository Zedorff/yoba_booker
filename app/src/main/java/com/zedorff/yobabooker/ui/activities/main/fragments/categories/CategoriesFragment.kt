package com.zedorff.yobabooker.ui.activities.main.fragments.categories

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zedorff.yobabooker.R
import com.zedorff.yobabooker.app.listeners.ViewHolderClickListener
import com.zedorff.yobabooker.databinding.FragmentCategoriesBinding
import com.zedorff.yobabooker.model.db.entities.CategoryEntity
import com.zedorff.yobabooker.ui.activities.base.fragments.BaseFragment
import com.zedorff.yobabooker.ui.activities.main.fragments.categories.adapter.CategoriesAdapter
import com.zedorff.yobabooker.ui.activities.main.fragments.categories.viewmodel.CategoriesViewModel
import com.zedorff.yobabooker.ui.activities.main.view.MainActivityView
import javax.inject.Inject

class CategoriesFragment: BaseFragment<CategoriesViewModel>(), ViewHolderClickListener<CategoryEntity> {

    @Inject lateinit var view: MainActivityView
    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var adapter: CategoriesAdapter

    companion object {
        fun build(): CategoriesFragment = CategoriesFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.title = resources.getString(R.string.text_title_categories)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CategoriesAdapter(this)
        binding.recycler.adapter = adapter
        binding.recycler.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CategoriesViewModel::class.java)
        viewModel.getCategories().observe(this, Observer {
            it?.let {
                adapter.swapItems(it)
            }
        })
    }

    override fun onClick(item: CategoryEntity) {
        view.openTransactionsForCategory(item)
    }
}