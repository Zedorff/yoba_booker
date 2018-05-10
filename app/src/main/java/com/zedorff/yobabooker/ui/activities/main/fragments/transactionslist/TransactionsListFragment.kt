package com.zedorff.yobabooker.ui.activities.main.fragments.transactionslist

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.zedorff.dragandswiperecycler.helper.SDItemTouchHelper
import com.zedorff.yobabooker.R
import com.zedorff.yobabooker.app.enums.TransactionType
import com.zedorff.yobabooker.app.extensions.nonNullObserve
import com.zedorff.yobabooker.databinding.FragmentTransactionsListBinding
import com.zedorff.yobabooker.model.db.embeded.FullTransaction
import com.zedorff.yobabooker.ui.activities.base.fragments.BaseFragment
import com.zedorff.yobabooker.ui.activities.main.fragments.transactionslist.adapter.TransactionItem
import com.zedorff.yobabooker.ui.activities.main.fragments.transactionslist.adapter.TransactionListItem
import com.zedorff.yobabooker.ui.activities.main.fragments.transactionslist.adapter.TransactionsListAdapter
import com.zedorff.yobabooker.ui.activities.main.fragments.transactionslist.adapter.TransferItem
import com.zedorff.yobabooker.ui.activities.main.fragments.transactionslist.viewmodel.TransactionsListViewModel
import com.zedorff.yobabooker.ui.activities.transaction.TransactionActivity
import com.zedorff.yobabooker.ui.activities.transfer.TransferActivity
import kotlinx.coroutines.experimental.async

//TODO Need to group transfers into one deletable view
class TransactionsListFragment : BaseFragment<TransactionsListViewModel>(), View.OnClickListener {

    private lateinit var binding: FragmentTransactionsListBinding
    private lateinit var adapter: TransactionsListAdapter

    private var deletedItem: TransactionListItem? = null
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
        viewModel.getTransactions().nonNullObserve(this, {
            binding.empty = it.isEmpty()
            adapter.swapItems(processTransactions(it))
        })
    }

    private fun processTransactions(items: List<FullTransaction>): List<TransactionListItem> {
        val group = items.groupBy { it.transaction.transferId?.let { it > 0 } ?: false }
        return with(group) {
            val mappedItems: MutableList<TransactionListItem> = mutableListOf()
            val transactions = get(false)
            val transfers = get(true)
            return@with mappedItems.apply {
                transactions?.let {
                    addAll(it.map { TransactionItem(it) })
                }
                transfers?.let {
                    addAll(it.groupBy {
                        it.transaction.transferId
                    }.values.map {
                        TransferItem(it.first(), it.last())
                    })
                }
            }
        }
    }

    override fun swipeEnabled() = true

    //TODO move to viewModel, maybe
    override fun onSwiped(position: Int) {
        deletedItem = adapter.items[position]
        deletedPosition = position

        adapter.removeItem(position)

        val snackbar = Snackbar.make(binding.root, R.string.snackbar_text_transaction_deleted, BaseTransientBottomBar.LENGTH_LONG)
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
                            when (it.getType()) {
                                TransactionListItem.TYPE_TRANSACTION -> {
                                    viewModel.deleteTransaction((it as TransactionItem).transaction)
                                }
                                TransactionListItem.TYPE_TRANSFER -> {
                                    viewModel.deleteTransaction((it as TransferItem).transactionTo)
                                    viewModel.deleteTransaction(it.transactionFrom)
                                }
                            }
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