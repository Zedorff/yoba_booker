package com.zedorff.yobabooker.ui.activities.main.fragments.accounts

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zedorff.yobabooker.databinding.FragmentAccountsBinding
import com.zedorff.yobabooker.ui.activities.base.fragments.BaseFragment
import com.zedorff.yobabooker.ui.activities.main.fragments.accounts.adapter.AccountsAdapter
import com.zedorff.yobabooker.ui.activities.main.fragments.accounts.view.AccountsFragmentView
import com.zedorff.yobabooker.ui.activities.main.fragments.accounts.viewmodel.AccountViewModel

class AccountsFragment: BaseFragment<AccountViewModel>(), AccountsFragmentView {

    private lateinit var binding: FragmentAccountsBinding
    private lateinit var adapter: AccountsAdapter

    companion object {
        fun build(): AccountsFragment {
            return AccountsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAccountsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = AccountsAdapter()
        binding.recycler.layoutManager = LinearLayoutManager(context)
        binding.recycler.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AccountViewModel::class.java)
        viewModel.getAccounts().observe(this, Observer(function = {
            it?.let {
                adapter.swapItems(it)
            }
        }))
    }
}