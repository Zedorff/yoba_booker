package com.zedorff.yobabooker.ui.activities.newtransaction.fragments

import android.app.DatePickerDialog
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import android.widget.DatePicker
import com.zedorff.yobabooker.R
import com.zedorff.yobabooker.app.extensions.getDayOfMonth
import com.zedorff.yobabooker.app.extensions.getMonth
import com.zedorff.yobabooker.app.extensions.getYear
import com.zedorff.yobabooker.databinding.FragmentNewTransactionBinding
import com.zedorff.yobabooker.ui.activities.base.fragments.BaseFragment
import com.zedorff.yobabooker.ui.activities.newtransaction.fragments.viewmodel.NewTransactionViewModel
import java.util.*

class NewTransactionFragment : BaseFragment<NewTransactionViewModel>(), DatePickerDialog.OnDateSetListener {
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

    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        viewModel.setDate(calendar.timeInMillis)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_new_transaction, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_transaction -> {
                viewModel.saveTransaction()
                activity?.finish()
            }
            R.id.choose_date_transaction -> {
                val calendar = Calendar.getInstance()
                DatePickerDialog(context, this, calendar.getYear(), calendar.getMonth(), calendar.getDayOfMonth()).show()
            }
            android.R.id.home -> { activity?.finish() }
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }
}