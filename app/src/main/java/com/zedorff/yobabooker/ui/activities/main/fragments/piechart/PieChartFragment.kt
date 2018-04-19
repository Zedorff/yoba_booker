package com.zedorff.yobabooker.ui.activities.main.fragments.piechart

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zedorff.yobabooker.databinding.FragmentPieChartBinding
import com.zedorff.yobabooker.ui.activities.base.fragments.BaseFragment
import com.zedorff.yobabooker.ui.activities.main.fragments.piechart.viewmodel.PieChartViewModel
import java.util.*

class PieChartFragment : BaseFragment<PieChartViewModel>() {

    private lateinit var binding: FragmentPieChartBinding

    companion object {
        fun build(): PieChartFragment = PieChartFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPieChartBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(PieChartViewModel::class.java)
        binding.monthSelector.selectedTimeInMillis.observe(this, Observer {
            it?.let {
                var calendar: Calendar = Calendar.getInstance().apply { timeInMillis = it }
                var month = calendar.get(Calendar.MONTH) + 1
                var year = calendar.get(Calendar.YEAR)
                viewModel.getOutcomeTransactions(month, year).observe(this, Observer {
                    it?.let {
                        binding.viewPieChart.setTransactions(it)
                        binding.pieChartLegend.setCategories(it.map { it.category.name })
                    }
                })
            }
        })
    }
}