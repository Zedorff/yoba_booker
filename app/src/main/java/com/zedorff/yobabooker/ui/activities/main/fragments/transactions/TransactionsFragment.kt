package com.zedorff.yobabooker.ui.activities.main.fragments.transactions

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zedorff.yobabooker.databinding.FragmentTransactionsBinding
import com.zedorff.yobabooker.ui.activities.base.fragments.BaseFragment
import com.zedorff.yobabooker.ui.activities.main.fragments.transactions.adapter.TransactionsAdapter
import com.zedorff.yobabooker.ui.activities.main.fragments.transactions.viewmodel.TransactionsViewModel

class TransactionsFragment: BaseFragment<TransactionsViewModel>() {

    private lateinit var binding: FragmentTransactionsBinding
    private lateinit var adapter: TransactionsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTransactionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = TransactionsAdapter()
        binding.recycler.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TransactionsViewModel::class.java)
        viewModel.getTransactions().observe(this, Observer {
            it?.let {
                adapter.swapItems(it)
            }
        })
    }
}