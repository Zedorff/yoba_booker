package com.zedorff.yobabooker.ui.activities.account.fragments

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import com.zedorff.yobabooker.R
import com.zedorff.yobabooker.app.extensions.nonNullObserve
import com.zedorff.yobabooker.databinding.FragmentAccountBinding
import com.zedorff.yobabooker.ui.activities.account.AccountActivity
import com.zedorff.yobabooker.ui.activities.account.fragments.viewmodel.AccountViewModel
import com.zedorff.yobabooker.ui.activities.base.fragments.BaseFragment

class AccountFragment : BaseFragment<AccountViewModel>() {


    companion object {
        fun build() = AccountFragment()
        fun build(id: Long): AccountFragment {
            return AccountFragment().apply {
                arguments = Bundle().apply {
                    putLong(AccountActivity.KEY_ACCOUNT_ID, id)
                }
            }
        }
    }

    private lateinit var binding: FragmentAccountBinding
    private var accountId: Long? = null

    init {
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAccountBinding.inflate(inflater, container, false)
        binding.setLifecycleOwner(this)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        accountId = arguments?.getLong(AccountActivity.KEY_ACCOUNT_ID)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AccountViewModel::class.java)
        viewModel.setAccountId(accountId)
        viewModel.getAccount().nonNullObserve(this, {
            viewModel.setAccount(it)
        })
        binding.viewModel = viewModel
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_completable, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save -> {
                viewModel.saveAccount()
                activity?.finish()
            }
            android.R.id.home -> {
                activity?.finish()
            }
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }
}