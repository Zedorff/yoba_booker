package com.zedorff.yobabooker.ui.activities.main.fragments.categorieslist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.zedorff.yobabooker.R
import com.zedorff.yobabooker.app.enums.TransactionType
import com.zedorff.yobabooker.app.listeners.ViewHolderClickListener
import com.zedorff.yobabooker.databinding.FragmentCategoriesListBinding
import com.zedorff.yobabooker.model.db.entities.CategoryEntity
import com.zedorff.yobabooker.ui.activities.base.fragments.BaseFragment
import com.zedorff.yobabooker.ui.activities.main.fragments.categorieslist.adapter.CategoriesListAdapter
import com.zedorff.yobabooker.ui.activities.main.fragments.categorieslist.viewmodel.CategoriesListViewModel

class CategoriesListFragment : BaseFragment<CategoriesListViewModel>(),
        ViewHolderClickListener<CategoryEntity> {

    private lateinit var binding: FragmentCategoriesListBinding
    private lateinit var adapter: CategoriesListAdapter

    companion object {
        fun build(): CategoriesListFragment = CategoriesListFragment()
    }

    init {
        setHasOptionsMenu(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.title = resources.getString(R.string.text_title_categories)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCategoriesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CategoriesListAdapter(this)
        binding.recycler.adapter = adapter
        binding.recycler.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CategoriesListViewModel::class.java)
        viewModel.setCategoryType(TransactionType.OUTCOME)
        viewModel.getCategories().observe(this, Observer {
            it?.let {
                adapter.swapItems(it)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_categories, menu)
        menu?.findItem(R.id.action_settings)?.isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_categories_swap -> {
                when (viewModel.getCategoryType()) {
                    TransactionType.OUTCOME -> {
                        item.setIcon(R.drawable.ic_negative_categories_white_24dp)
                        viewModel.setCategoryType(TransactionType.INCOME)
                    }
                    TransactionType.INCOME -> {
                        item.setIcon(R.drawable.ic_positive_categories_white_24dp)
                        viewModel.setCategoryType(TransactionType.OUTCOME)
                    }
                    else -> {
                        viewModel.setCategoryType(TransactionType.OUTCOME)
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onClick(item: CategoryEntity) {
    }
}