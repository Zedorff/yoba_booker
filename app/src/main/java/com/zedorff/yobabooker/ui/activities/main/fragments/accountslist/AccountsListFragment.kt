package com.zedorff.yobabooker.ui.activities.main.fragments.accountslist

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zedorff.yobabooker.R
import com.zedorff.yobabooker.app.listeners.ViewHolderClickListener
import com.zedorff.yobabooker.databinding.FragmentAccountsListBinding
import com.zedorff.yobabooker.model.db.embeded.FullAccount
import com.zedorff.yobabooker.ui.activities.base.fragments.BaseFragment
import com.zedorff.yobabooker.ui.activities.main.fragments.accountslist.adapter.AccountsListAdapter
import com.zedorff.yobabooker.ui.activities.main.fragments.accountslist.viewmodel.AccountsListViewModel
import com.zedorff.yobabooker.ui.activities.main.view.MainActivityView
import com.zedorff.yobabooker.ui.activities.account.AccountActivity
import javax.inject.Inject

class AccountsListFragment: BaseFragment<AccountsListViewModel>(), ViewHolderClickListener<FullAccount>, View.OnClickListener {

    @Inject lateinit var view: MainActivityView
    private lateinit var binding: FragmentAccountsListBinding
    private lateinit var adapter: AccountsListAdapter

    companion object {
        fun build(): AccountsListFragment {
            return AccountsListFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.title = resources.getString(R.string.text_title_accounts)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAccountsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = AccountsListAdapter(this)
        binding.recycler.layoutManager = LinearLayoutManager(context)
        binding.recycler.adapter = adapter
        binding.fabNewAccount.setOnClickListener(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AccountsListViewModel::class.java)
        viewModel.getAccounts().observe(this, Observer({
            it?.let {
                adapter.swapItems(it)
            }
        }))
    }

    override fun onClick(item: FullAccount) {
        activity?.let {
            AccountActivity.build(it, item.account.id.toString())
        }
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.fab_new_account -> {AccountActivity.build(view.context)}
        }
    }
}