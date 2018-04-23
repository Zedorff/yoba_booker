package com.zedorff.yobabooker.ui.activities.newaccount

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.zedorff.yobabooker.R
import com.zedorff.yobabooker.databinding.ActivityAccountBinding
import com.zedorff.yobabooker.ui.activities.base.BaseActivity
import com.zedorff.yobabooker.ui.activities.newaccount.fragments.NewAccountFragment
import org.jetbrains.anko.intentFor

class NewAccountActivity: BaseActivity() {

    private lateinit var binding: ActivityAccountBinding

    companion object {
        fun build(context: Context) {
            context.startActivity(context.intentFor<NewAccountActivity>())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_account)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        savedInstanceState ?: replaceFragment(R.id.container_new_account, NewAccountFragment.build())
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}