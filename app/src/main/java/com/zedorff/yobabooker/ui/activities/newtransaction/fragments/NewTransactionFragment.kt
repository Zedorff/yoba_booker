package com.zedorff.yobabooker.ui.activities.newtransaction.fragments

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import com.zedorff.yobabooker.R
import com.zedorff.yobabooker.app.extensions.then
import com.zedorff.yobabooker.databinding.FragmentNewTransactionBinding
import com.zedorff.yobabooker.ui.activities.base.fragments.BaseFragment
import com.zedorff.yobabooker.ui.activities.newtransaction.fragments.viewmodel.NewTransactionViewModel

class NewTransactionFragment : BaseFragment<NewTransactionViewModel>() {

    private lateinit var binding: FragmentNewTransactionBinding

    companion object {
        const val KEY_IS_INCOME = "is_income"

        fun build(income: Boolean): NewTransactionFragment {
            val fragment = NewTransactionFragment()
            val bundle = Bundle()
            bundle.putBoolean(KEY_IS_INCOME, income)
            fragment.arguments = bundle
            return fragment
        }
    }

    init {
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNewTransactionBinding.inflate(inflater, container, false)
        binding.setLifecycleOwner(this)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NewTransactionViewModel::class.java)
        arguments?.getBoolean(KEY_IS_INCOME, false)?.let { viewModel.setIsIncome(it) }
        binding.viewModel = viewModel
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_completable, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save -> {
                viewModel.saveTransaction() then {
                    activity?.finish()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}