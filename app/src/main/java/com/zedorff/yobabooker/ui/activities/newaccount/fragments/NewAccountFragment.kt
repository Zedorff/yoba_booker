package com.zedorff.yobabooker.ui.activities.newaccount.fragments

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import com.zedorff.yobabooker.R
import com.zedorff.yobabooker.databinding.FragmentNewAccountBinding
import com.zedorff.yobabooker.ui.activities.base.fragments.BaseFragment
import com.zedorff.yobabooker.ui.activities.newaccount.fragments.viewmodel.NewAccountViewModel
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

class NewAccountFragment : BaseFragment<NewAccountViewModel>() {

    private lateinit var binding: FragmentNewAccountBinding

    companion object {
        fun build(): NewAccountFragment = NewAccountFragment()
    }

    init {
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNewAccountBinding.inflate(inflater, container, false)
        binding.setLifecycleOwner(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.spinnerAccountCategory.adapter =
                ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item,
                        resources.getStringArray(R.array.accounts))
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NewAccountViewModel::class.java)
        binding.viewModel = viewModel
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_completable, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save -> {
                launch(UI) {
                    viewModel.saveAccount()
                    activity?.finish()
                }
            }
            android.R.id.home -> { activity?.finish() }
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }
}