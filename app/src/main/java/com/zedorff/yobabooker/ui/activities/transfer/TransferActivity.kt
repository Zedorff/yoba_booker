package com.zedorff.yobabooker.ui.activities.transfer

import android.content.Context
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.zedorff.yobabooker.R
import com.zedorff.yobabooker.app.enums.UiType
import com.zedorff.yobabooker.app.extensions.nonNullObserve
import com.zedorff.yobabooker.databinding.ActivityTransferBinding
import com.zedorff.yobabooker.model.repository.YobaRepository
import com.zedorff.yobabooker.ui.activities.account.AccountActivity
import com.zedorff.yobabooker.ui.activities.base.BaseActivity
import com.zedorff.yobabooker.ui.activities.transfer.fragments.TransferFragment
import org.jetbrains.anko.intentFor
import javax.inject.Inject

class TransferActivity : BaseActivity() {

    companion object {
        const val KEY_TRANSFER_ID = "transfer_id"
        const val KEY_UI_TYPE = "ui_type"

        fun startCreate(context: Context) {
            context.startActivity(context.intentFor<TransferActivity>(KEY_UI_TYPE to UiType.CREATE)
            )
        }

        fun startEdit(context: Context, id: Long) {
            context.startActivity(context.intentFor<TransferActivity>(
                    KEY_UI_TYPE to UiType.EDIT,
                    KEY_TRANSFER_ID to id)
            )
        }
    }

    @Inject
    lateinit var repository: YobaRepository
    private lateinit var binding: ActivityTransferBinding
    private lateinit var uiType: UiType
    private var transferId: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_transfer)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        uiType = intent.getSerializableExtra(TransferActivity.KEY_UI_TYPE) as UiType
        transferId = intent.getLongExtra(TransferActivity.KEY_TRANSFER_ID, 0L)


        if (savedInstanceState != null) return

        repository.loadAllAccounts().nonNullObserve(this, {
            if (it.isEmpty()) {
                AccountActivity.build(this)
            } else {
                when (uiType) {
                    UiType.EDIT -> {
                        replaceFragment(R.id.container_new_transfer, TransferFragment.buildEdit(transferId))
                    }
                    UiType.CREATE -> {
                        replaceFragment(R.id.container_new_transfer, TransferFragment.buildCreate())
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