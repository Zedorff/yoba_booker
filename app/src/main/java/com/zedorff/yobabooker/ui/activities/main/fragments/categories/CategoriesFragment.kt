package com.zedorff.yobabooker.ui.activities.main.fragments.categories

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zedorff.yobabooker.databinding.FragmentCategoriesBinding
import com.zedorff.yobabooker.ui.activities.base.fragments.BaseFragment
import com.zedorff.yobabooker.ui.activities.main.fragments.categories.adapter.CategoriesAdapter
import com.zedorff.yobabooker.ui.activities.main.fragments.categories.view.CategoriesFragmentView
import com.zedorff.yobabooker.ui.activities.main.fragments.categories.viewmodel.CategoriesViewModel

class CategoriesFragment: BaseFragment<CategoriesViewModel>(), CategoriesFragmentView {

    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var adapter: CategoriesAdapter

    companion object {
        fun build(): CategoriesFragment = CategoriesFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CategoriesAdapter()
        binding.recycler.adapter = adapter
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
}