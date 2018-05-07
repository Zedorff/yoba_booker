package com.zedorff.yobabooker.ui.activities.account

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.zedorff.yobabooker.R
import com.zedorff.yobabooker.app.enums.UiType
import com.zedorff.yobabooker.databinding.ActivityAccountBinding
import com.zedorff.yobabooker.ui.activities.base.BaseActivity
import com.zedorff.yobabooker.ui.activities.account.fragments.AccountFragment
import org.jetbrains.anko.intentFor

class AccountActivity: BaseActivity() {


    companion object {

        const val KEY_UI_TYPE = "ui_type"
        const val KEY_ACCOUNT_ID = "account_id"

        fun build(context: Context) {
            context.startActivity(context.intentFor<AccountActivity>(KEY_UI_TYPE to UiType.CREATE))
        }

        fun build(context: Context, id: String) {
            context.startActivity(context.intentFor<AccountActivity>(
                    KEY_UI_TYPE to UiType.EDIT,
                    KEY_ACCOUNT_ID to id))
        }
    }

    private lateinit var binding: ActivityAccountBinding
    private lateinit var uiType: UiType
    private var accountId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_account)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        uiType = intent.getSerializableExtra(KEY_UI_TYPE) as UiType
        accountId = intent.getStringExtra(KEY_ACCOUNT_ID)

        if (savedInstanceState != null) return

        when(intent.getSerializableExtra(KEY_UI_TYPE)) {
            UiType.CREATE -> { replaceFragment(R.id.container_new_account, AccountFragment.build()) }
            UiType.EDIT -> { replaceFragment(R.id.container_new_account, AccountFragment.build(accountId)) }
            else -> { replaceFragment(R.id.container_new_account, AccountFragment.build()) }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}