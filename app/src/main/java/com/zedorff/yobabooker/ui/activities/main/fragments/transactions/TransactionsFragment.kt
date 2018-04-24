package com.zedorff.yobabooker.ui.activities.main.fragments.transactions

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zedorff.yobabooker.R
import com.zedorff.yobabooker.app.enums.TransactionType
import com.zedorff.yobabooker.databinding.FragmentTransactionsBinding
import com.zedorff.yobabooker.model.db.entities.AccountEntity
import com.zedorff.yobabooker.model.db.entities.CategoryEntity
import com.zedorff.yobabooker.ui.activities.base.fragments.BaseFragment
import com.zedorff.yobabooker.ui.activities.main.fragments.transactions.adapter.TransactionsAdapter
import com.zedorff.yobabooker.ui.activities.main.fragments.transactions.viewmodel.TransactionsViewModel
import com.zedorff.yobabooker.ui.activities.transaction.TransactionActivity

class TransactionsFragment : BaseFragment<TransactionsViewModel>(), View.OnClickListener {

    private lateinit var binding: FragmentTransactionsBinding
    private lateinit var adapter: TransactionsAdapter
    private var categoryId: String? = null
    private var accountId: String? = null

    companion object {
        private const val KEY_CATEGORY_ID = "category_id"
        private const val KEY_CATEGORY_NAME = "category_name"
        private const val KEY_ACCOUNT_ID = "account_id"
        private const val KEY_ACCOUNT_NAME = "account_name"

        fun build(): TransactionsFragment = TransactionsFragment()
        fun build(category: CategoryEntity? = null, account: AccountEntity? = null): TransactionsFragment {
            return TransactionsFragment().apply {
                arguments = Bundle().apply {
                    category?.let {
                        putString(KEY_CATEGORY_ID, it.id.toString())
                        putString(KEY_CATEGORY_NAME, it.name)
                    }
                    account?.let {
                        putString(KEY_ACCOUNT_ID, it.id.toString())
                        putString(KEY_ACCOUNT_NAME, it.name)
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            categoryId = it.getString(KEY_CATEGORY_ID)
            accountId = it.getString(KEY_ACCOUNT_ID)
        }
        activity?.let {
            it.title = when {
                categoryId != null -> arguments?.getString(KEY_CATEGORY_NAME)
                accountId != null -> arguments?.getString(KEY_ACCOUNT_NAME)
                else -> resources.getString(R.string.text_title_transactions)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTransactionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = TransactionsAdapter()
        binding.recycler.adapter = adapter
        binding.fabNewIncome.setOnClickListener(this)
        binding.fabNewOutcome.setOnClickListener(this)
        binding.fabNewTransfer.setOnClickListener(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(TransactionsViewModel::class.java)
        viewModel.getTransactions(categoryId, accountId)
                .observe(this, Observer {
                    it?.let {
                        binding.empty = it.isEmpty()
                        adapter.swapItems(it)
                    }
                })
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.fab_new_income -> { TransactionActivity.startCreate(view.context, TransactionType.INCOME)}
            R.id.fab_new_outcome -> { TransactionActivity.startCreate(view.context, TransactionType.OUTCOME)}
            R.id.fab_new_transfer -> {}
        }
        binding.fabMenu.collapse()
    }
}