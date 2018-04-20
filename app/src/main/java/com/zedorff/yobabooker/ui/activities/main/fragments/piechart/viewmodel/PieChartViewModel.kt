package com.zedorff.yobabooker.ui.activities.main.fragments.piechart.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.zedorff.yobabooker.model.db.embeded.FullTransaction
import com.zedorff.yobabooker.model.repository.YobaRepository
import com.zedorff.yobabooker.ui.activities.base.fragments.viewmodel.BaseViewModel
import javax.inject.Inject

class PieChartViewModel @Inject constructor(var repository: YobaRepository): BaseViewModel() {

    private var monthTransactions: MutableLiveData<List<FullTransaction>> = MutableLiveData()
    private var selectedCategory: MutableLiveData<Int> = MutableLiveData()
    private var graphTransactions: LiveData<List<FullTransaction>> = MutableLiveData()

    init {
        graphTransactions = Transformations.switchMap(selectedCategory, { selected ->
            Transformations.map(monthTransactions, {
                it.filter { it.category.id ==  selected}
            })
        })
    }

    fun getOutcomeTransactions(month: Int, year: Int): LiveData<List<FullTransaction>> {
        return Transformations.map(repository.getFullTransactionsByMonthInYear(month, year), {
            val data = it.filter { it.category.type == 1 }
            monthTransactions.value = data
            return@map data
        })
    }

    fun getGraphData(): LiveData<List<FullTransaction>> {
        return graphTransactions
    }

    fun onSliceClick(id: Int) {
        selectedCategory.value = id
    }
}