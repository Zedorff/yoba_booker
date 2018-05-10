package com.zedorff.yobabooker.ui.activities.transfer.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.DatePicker
import androidx.lifecycle.ViewModelProviders
import com.zedorff.yobabooker.R
import com.zedorff.yobabooker.app.extensions.*
import com.zedorff.yobabooker.databinding.FragmentTransferBinding
import com.zedorff.yobabooker.ui.activities.base.fragments.BaseFragment
import com.zedorff.yobabooker.ui.activities.transfer.TransferActivity
import com.zedorff.yobabooker.ui.activities.transfer.fragments.viewmodel.TransferViewModel
import java.util.*

class TransferFragment: BaseFragment<TransferViewModel>(), DatePickerDialog.OnDateSetListener {

    companion object {
        fun buildCreate(): TransferFragment {
            return TransferFragment()
        }

        fun buildEdit(id: Long?): TransferFragment {
            return TransferFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(TransferActivity.KEY_TRANSFER_ID, id)
                }
            }
        }
    }

    private lateinit var binding: FragmentTransferBinding
    private var transferId: Long? = null

    init {
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTransferBinding.inflate(layoutInflater, container, false)
        binding.setLifecycleOwner(this)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        transferId = arguments?.getLong(TransferActivity.KEY_TRANSFER_ID)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TransferViewModel::class.java)
        viewModel.setTransferId(transferId)
        viewModel.getTransfer().nonNullObserve(this, {
            viewModel.setTransfer(it)
            viewModel.getAccounts().nonNullObserve(this, {
                viewModel.setAccounts(it)
            })

            viewModel.getInCategory().nonNullObserve(this, {
                viewModel.setInCategory(it.id)
            })

            viewModel.getOutCategory().nonNullObserve(this, {
                viewModel.setOutCategory(it.id)
            })
        })

        binding.viewModel = viewModel
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        viewModel.setDate(calendar.timeInMillis)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_new_transfer, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save_transfer -> {
                viewModel.saveTransfer()
                activity?.finish()
                true
            }
            R.id.choose_date_transfer -> {
                val calendar = Calendar.getInstance().fromTimeInMillis(viewModel.getDate())
                DatePickerDialog(context, this, calendar.getYear(), calendar.getMonth(), calendar.getDayOfMonth()).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}