package com.zedorff.yobabooker.ui.activities.main.fragments.transactionslist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.BaseTransientBottomBar
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zedorff.dragandswiperecycler.helper.SDItemTouchHelper
import com.zedorff.yobabooker.R
import com.zedorff.yobabooker.app.enums.TransactionType
import com.zedorff.yobabooker.databinding.FragmentTransactionsListBinding
import com.zedorff.yobabooker.model.db.embeded.FullTransaction
import com.zedorff.yobabooker.ui.activities.base.fragments.BaseFragment
import com.zedorff.yobabooker.ui.activities.main.fragments.transactionslist.adapter.TransactionsListAdapter
import com.zedorff.yobabooker.ui.activities.main.fragments.transactionslist.viewmodel.TransactionsListViewModel
import com.zedorff.yobabooker.ui.activities.transaction.TransactionActivity
import kotlinx.coroutines.experimental.async
import com.zedorff.yobabooker.ui.activities.transfer.TransferActivity

class TransactionsListFragment : BaseFragment<TransactionsListViewModel>(), View.OnClickListener {

    private lateinit var binding: FragmentTransactionsListBinding
    private lateinit var adapter: TransactionsListAdapter

    private var deletedItem: FullTransaction? = null
    private var deletedPosition: Int? = null

    companion object {
        fun build(): TransactionsListFragment = TransactionsListFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.title = resources.getString(R.string.text_title_transactions)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTransactionsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = TransactionsListAdapter()
        binding.recycler.adapter = adapter
        SDItemTouchHelper(this).attachToRecyclerView(binding.recycler)
        binding.fabNewIncome.setOnClickListener(this)
        binding.fabNewOutcome.setOnClickListener(this)
        binding.fabNewTransfer.setOnClickListener(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(TransactionsListViewModel::class.java)
        viewModel.getTransactions().observe(this, Observer {
            it?.let {
                binding.empty = it.isEmpty()
                adapter.swapItems(it)
            }
        })
    }

    override fun swipeEnabled() = true

    //TODO move to viewModel, maybe
    override fun onSwiped(position: Int) {
        deletedItem = adapter.items[position]
        deletedPosition = position

        adapter.removeItem(position)

        val snackbar = Snackbar.make(binding.root, R.string.snackbar_text_transaction_deleted, Snackbar.LENGTH_LONG)
        snackbar.setAction(R.string.snackbar_text_undo, {
            if (deletedItem != null && deletedPosition != null) {
                adapter.addItem(deletedItem!!, deletedPosition!!)
            }
        })
        snackbar.setActionTextColor(Color.YELLOW)
        snackbar.addCallback(object : Snackbar.Callback() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                if (event != BaseTransientBottomBar.BaseCallback.DISMISS_EVENT_ACTION) {
                    async {
                        deletedItem?.let {
                            viewModel.deleteTransaction(it)
                        }
                        deletedItem = null
                        deletedPosition = null
                    }
                }
            }
        })
        snackbar.show()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.fab_new_income -> {
                TransactionActivity.startCreate(view.context, TransactionType.INCOME)
            }
            R.id.fab_new_outcome -> {
                TransactionActivity.startCreate(view.context, TransactionType.OUTCOME)
            }
            R.id.fab_new_transfer -> {
                TransferActivity.startCreate(view.context)
            }
        }
        binding.fabMenu.collapse()
    }
}