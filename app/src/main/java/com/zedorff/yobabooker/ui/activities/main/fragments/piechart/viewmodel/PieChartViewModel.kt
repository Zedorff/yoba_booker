package com.zedorff.yobabooker.ui.activities.main.fragments.piechart.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import com.zedorff.yobabooker.model.db.embeded.FullTransaction
import com.zedorff.yobabooker.model.repository.YobaRepository
import com.zedorff.yobabooker.ui.activities.base.fragments.viewmodel.BaseViewModel
import javax.inject.Inject

class PieChartViewModel @Inject constructor(var repository: YobaRepository): BaseViewModel() {

    fun getOutcomeTransactions(month: Int, year: Int): LiveData<List<FullTransaction>> {
        return Transformations.map(repository.getFullTransactionsByMonthInYear(month, year), {
            it.filter { it.category.type == 1 }
        })
    }
}