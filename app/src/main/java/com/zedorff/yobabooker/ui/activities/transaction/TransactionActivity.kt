package com.zedorff.yobabooker.ui.activities.transaction

import android.arch.lifecycle.Observer
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.zedorff.yobabooker.R
import com.zedorff.yobabooker.app.enums.TransactionType
import com.zedorff.yobabooker.app.enums.UiType
import com.zedorff.yobabooker.databinding.ActivityTransactionBinding
import com.zedorff.yobabooker.model.repository.YobaRepository
import com.zedorff.yobabooker.ui.activities.account.AccountActivity
import com.zedorff.yobabooker.ui.activities.base.BaseActivity
import com.zedorff.yobabooker.ui.activities.transaction.fragments.TransactionFragment
import org.jetbrains.anko.intentFor
import javax.inject.Inject

class TransactionActivity : BaseActivity() {

    companion object {

        const val KEY_TRANSACTION_TYPE = "transaction_type"
        const val KEY_TRANSACTION_ID = "transaction_id"
        const val KEY_UI_TYPE = "ui_type"

        fun startCreate(context: Context, type: TransactionType) {
            context.startActivity(context.intentFor<TransactionActivity>(
                    KEY_TRANSACTION_TYPE to type,
                    KEY_UI_TYPE to UiType.CREATE)
            )
        }

        fun startEdit(context: Context, type: TransactionType, id: String) {
            context.startActivity(context.intentFor<TransactionActivity>(
                    KEY_TRANSACTION_TYPE to type,
                    KEY_UI_TYPE to UiType.EDIT,
                    KEY_TRANSACTION_ID to id)
            )
        }
    }

    @Inject
    lateinit var repository: YobaRepository
    private lateinit var binding: ActivityTransactionBinding
    private lateinit var transactionType: TransactionType
    private lateinit var uiType: UiType
    private var transactionId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_transaction)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        transactionType = intent.getSerializableExtra(KEY_TRANSACTION_TYPE) as TransactionType
        uiType = intent.getSerializableExtra(KEY_UI_TYPE) as UiType
        transactionId = intent.getStringExtra(KEY_TRANSACTION_ID)

        binding.type = transactionType

        if (savedInstanceState != null) return

        repository.getAllAccounts().observe(this, Observer {
            it?.let {
                if (it.isEmpty()) {
                    startActivity(intentFor<AccountActivity>())
                } else {
                    when(uiType) {
                        UiType.EDIT -> {replaceFragment(R.id.container_new_transaction, TransactionFragment.buildEdit(transactionType, transactionId))}
                        UiType.CREATE -> {replaceFragment(R.id.container_new_transaction, TransactionFragment.buildCreate(transactionType)) }
                    }
                }
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}