package com.zedorff.yobabooker.ui.activities.main.fragments.piechart.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.zedorff.yobabooker.app.enums.TransactionType
import com.zedorff.yobabooker.app.extensions.fromTimeInMillis
import com.zedorff.yobabooker.app.extensions.getActualMonth
import com.zedorff.yobabooker.app.extensions.getYear
import com.zedorff.yobabooker.model.db.embeded.FullTransaction
import com.zedorff.yobabooker.model.repository.YobaRepository
import com.zedorff.yobabooker.ui.activities.base.fragments.viewmodel.BaseViewModel
import java.util.*
import javax.inject.Inject

class PieChartViewModel @Inject constructor(var repository: YobaRepository): BaseViewModel() {

    private var monthTransactions: LiveData<List<FullTransaction>> = MutableLiveData()
    private var graphTransactions: LiveData<List<FullTransaction>> = MutableLiveData()
    private var selectedCategory: MutableLiveData<Int> = MutableLiveData()
    private var dateLiveData: MutableLiveData<Long> = MutableLiveData()

    init {
        graphTransactions = Transformations.switchMap(selectedCategory, { selected ->
            Transformations.map(monthTransactions, {
                it.filter { it.category.id ==  selected}
            })
        })
        monthTransactions = Transformations.switchMap(dateLiveData, { millis ->
            val calendar = Calendar.getInstance().fromTimeInMillis(millis)
            return@switchMap Transformations.map(repository.getFullTransactionsByMonthInYear(calendar.getActualMonth(), calendar.getYear()), {
                it.filter { it.category.type == TransactionType.OUTCOME }
            })
        })
    }

    fun getOutcomeTransactions(): LiveData<List<FullTransaction>> {
        return monthTransactions
    }

    fun getGraphData(): LiveData<List<FullTransaction>> {
        return graphTransactions
    }

    fun onSliceClick(id: Int) {
        selectedCategory.value = id
    }

    fun onDateChanged(millis: Long) {
        dateLiveData.value = millis
        selectedCategory.value = -1
    }
}