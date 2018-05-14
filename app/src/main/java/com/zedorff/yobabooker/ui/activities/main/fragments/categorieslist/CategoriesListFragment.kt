package com.zedorff.yobabooker.ui.activities.main.fragments.categorieslist

import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProviders
import com.zedorff.dragandswiperecycler.helper.SDHelperListener
import com.zedorff.dragandswiperecycler.helper.SDItemTouchHelper
import com.zedorff.yobabooker.R
import com.zedorff.yobabooker.app.enums.TransactionType
import com.zedorff.yobabooker.app.extensions.nonNullObserve
import com.zedorff.yobabooker.databinding.FragmentCategoriesListBinding
import com.zedorff.yobabooker.ui.activities.base.fragments.BaseFragment
import com.zedorff.yobabooker.ui.activities.main.fragments.categorieslist.adapter.CategoriesListAdapter
import com.zedorff.yobabooker.ui.activities.main.fragments.categorieslist.viewmodel.CategoriesListViewModel
import kotlinx.coroutines.experimental.async

class CategoriesListFragment : BaseFragment<CategoriesListViewModel>(), SDHelperListener {

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
        val touchHelper = SDItemTouchHelper(this)
        adapter = CategoriesListAdapter(touchHelper)
        binding.recycler.adapter = adapter
        touchHelper.attachToRecyclerView(binding.recycler)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CategoriesListViewModel::class.java)
        viewModel.setCategoryType(TransactionType.OUTCOME)
        viewModel.getCategories().nonNullObserve(this, {
            adapter.swapItems(it)
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


    override fun dragDropEnabled() = true

    override fun onDragged(from: Int, to: Int) {
        adapter.onMoved(from, to)
    }

    //TODO figure out why this became so laggy
    override fun onDragDropEnded() {
        async {
            viewModel.updateCategoriesOrder(adapter.items)
        }
    }
}