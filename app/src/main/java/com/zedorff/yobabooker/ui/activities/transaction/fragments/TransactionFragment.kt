package com.zedorff.yobabooker.ui.activities.transaction.fragments

import android.app.DatePickerDialog
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.DatePicker
import com.zedorff.yobabooker.R
import com.zedorff.yobabooker.app.extensions.getDayOfMonth
import com.zedorff.yobabooker.app.extensions.getMonth
import com.zedorff.yobabooker.app.extensions.getYear
import com.zedorff.yobabooker.databinding.FragmentNewTransactionBinding
import com.zedorff.yobabooker.ui.activities.base.fragments.BaseFragment
import com.zedorff.yobabooker.ui.activities.transaction.TransactionActivity
import com.zedorff.yobabooker.ui.activities.transaction.TransactionActivity.TransactionType
import com.zedorff.yobabooker.ui.activities.transaction.fragments.viewmodel.TransactionViewModel
import java.util.*

class TransactionFragment : BaseFragment<TransactionViewModel>(), DatePickerDialog.OnDateSetListener {

    companion object {
        fun buildCreate(type: TransactionType): TransactionFragment {
//            val fragment = TransactionFragment()
//            val bundle = Bundle()
//            bundle.putSerializable(TransactionActivity.KEY_TRANSACTION_TYPE, type)
//            fragment.arguments = bundle
//            return fragment
            return TransactionFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(TransactionActivity.KEY_TRANSACTION_TYPE, type)
                }
            }
        }

        fun buildEdit(type: TransactionType, id: String?): TransactionFragment {
            return TransactionFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(TransactionActivity.KEY_TRANSACTION_TYPE, type)
                    putString(TransactionActivity.KEY_TRANSACTION_ID, id)
                }
            }
        }
    }

    private lateinit var binding: FragmentNewTransactionBinding
    private lateinit var transactionType: TransactionType
    private var transactionId: String? = null

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
        transactionType = arguments?.getSerializable(TransactionActivity.KEY_TRANSACTION_TYPE) as TransactionType
        transactionId = arguments?.getString(TransactionActivity.KEY_TRANSACTION_ID)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TransactionViewModel::class.java)
        viewModel.setTransactionId(transactionId)
        viewModel.getTransaction().observe(this, android.arch.lifecycle.Observer {
            it?.let {
                viewModel.setTransaction(it, transactionType)
            }
        })
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
                viewModel.getDate().value?.let {
                    calendar.timeInMillis = it
                }
                DatePickerDialog(context, this, calendar.getYear(), calendar.getMonth(), calendar.getDayOfMonth()).show()
            }
            android.R.id.home -> {
                activity?.finish()
            }
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }
}