package com.zedorff.yobabooker.ui.activities.transaction.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.DatePicker
import androidx.lifecycle.ViewModelProviders
import com.zedorff.yobabooker.R
import com.zedorff.yobabooker.app.enums.TransactionType
import com.zedorff.yobabooker.app.extensions.*
import com.zedorff.yobabooker.databinding.FragmentTransactionBinding
import com.zedorff.yobabooker.ui.activities.base.fragments.BaseFragment
import com.zedorff.yobabooker.ui.activities.transaction.TransactionActivity
import com.zedorff.yobabooker.ui.activities.transaction.fragments.viewmodel.TransactionViewModel
import java.util.*

class TransactionFragment : BaseFragment<TransactionViewModel>(), DatePickerDialog.OnDateSetListener {

    companion object {
        fun buildCreate(type: TransactionType): TransactionFragment {
            return TransactionFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(TransactionActivity.KEY_TRANSACTION_TYPE, type)
                }
            }
        }

        fun buildEdit(type: TransactionType, id: Long): TransactionFragment {
            return TransactionFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(TransactionActivity.KEY_TRANSACTION_TYPE, type)
                    putLong(TransactionActivity.KEY_TRANSACTION_ID, id)
                }
            }
        }
    }

    private lateinit var binding: FragmentTransactionBinding
    private lateinit var transactionType: TransactionType
    private var transactionId: Long? = null

    init {
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTransactionBinding.inflate(inflater, container, false)
        binding.setLifecycleOwner(this)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        transactionType = arguments?.getSerializable(TransactionActivity.KEY_TRANSACTION_TYPE) as TransactionType
        transactionId = arguments?.getLong(TransactionActivity.KEY_TRANSACTION_ID, 0L)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TransactionViewModel::class.java)

        viewModel.setTransactionId(transactionId)
        viewModel.setTransactionType(transactionType)
        viewModel.getTransaction().nonNullObserve(this, {
            viewModel.setTransaction(it)
            viewModel.getCategories().nonNullObserve(this, {
                viewModel.setCategories(it)
            })

            viewModel.getAccounts().nonNullObserve(this, {
                viewModel.setAccounts(it)
            })
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
                val calendar = Calendar.getInstance().fromTimeInMillis(viewModel.getDate())
                DatePickerDialog(context, this, calendar.getYear(), calendar.getMonth(), calendar.getDayOfMonth()).show()
            }
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }
}