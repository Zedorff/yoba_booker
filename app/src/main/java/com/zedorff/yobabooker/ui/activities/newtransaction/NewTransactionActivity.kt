package com.zedorff.yobabooker.ui.activities.newtransaction

import android.arch.lifecycle.Observer
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.zedorff.yobabooker.R
import com.zedorff.yobabooker.databinding.ActivityNewTransactionBinding
import com.zedorff.yobabooker.model.db.dao.AccountDao
import com.zedorff.yobabooker.model.repository.YobaRepository
import com.zedorff.yobabooker.ui.activities.base.BaseActivity
import com.zedorff.yobabooker.ui.activities.newaccount.NewAccountActivity
import com.zedorff.yobabooker.ui.activities.newtransaction.fragments.NewTransactionFragment
import org.jetbrains.anko.intentFor
import javax.inject.Inject

class NewTransactionActivity : BaseActivity() {

    @Inject
    lateinit var repository: YobaRepository
    private lateinit var binding: ActivityNewTransactionBinding

    companion object {
        private const val KEY_IS_INCOME = "income"

        fun build(context: Context, isIncome: Boolean) {
            context.startActivity(context.intentFor<NewTransactionActivity>(KEY_IS_INCOME to isIncome))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_transaction)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val isIncome = intent.getBooleanExtra(KEY_IS_INCOME, true)
        binding.income = isIncome

        savedInstanceState
                ?: repository.getAllAccounts().observe(this, Observer {
                    it?.let {
                        if (it.isEmpty()) {
                            startActivity(intentFor<NewAccountActivity>())
                        } else {
                            replaceFragment(R.id.container_new_transaction, NewTransactionFragment.build(isIncome))
                        }
                    }
                })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}