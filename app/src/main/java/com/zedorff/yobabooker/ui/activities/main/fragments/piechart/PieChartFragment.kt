package com.zedorff.yobabooker.ui.activities.main.fragments.piechart

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zedorff.yobabooker.databinding.FragmentPieChartBinding
import com.zedorff.yobabooker.ui.activities.base.fragments.BaseFragment
import com.zedorff.yobabooker.ui.activities.main.fragments.piechart.viewmodel.PieChartViewModel
import com.zedorff.yobabooker.ui.views.PieChartView

class PieChartFragment : BaseFragment<PieChartViewModel>(), PieChartView.OnPieChartClickListener {

    private lateinit var binding: FragmentPieChartBinding

    companion object {
        fun build(): PieChartFragment = PieChartFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPieChartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPieChart.setOnSliceClickListener(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(PieChartViewModel::class.java)
        binding.monthSelector.selectedTimeInMillis.observe(this, Observer {
            it?.let {
                viewModel.onDateChanged(it)
            }
        })

        viewModel.getOutcomeTransactions().observe(this, Observer {
            it?.let {
                binding.viewPieChart.setTransactions(it)
                binding.viewPieChartLegend.setCategories(it.map { it.category }.distinct())
                binding.hasData = !it.isEmpty()
            }
        })
        viewModel.getGraphData().observe(this, Observer {
            it?.let {
                binding.viewColumnBarGraph.setTransactions(it)
            }
        })
    }

    override fun onSliceClick(categoryId: Int) {
        viewModel.onSliceClick(categoryId)
    }
}