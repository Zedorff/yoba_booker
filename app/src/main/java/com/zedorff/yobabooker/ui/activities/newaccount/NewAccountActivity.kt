package com.zedorff.yobabooker.ui.activities.newaccount

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.zedorff.yobabooker.R
import com.zedorff.yobabooker.databinding.ActivityNewAccountBinding
import com.zedorff.yobabooker.ui.activities.base.BaseActivity
import com.zedorff.yobabooker.ui.activities.newaccount.fragments.NewAccountFragment

class NewAccountActivity: BaseActivity() {

    private lateinit var binding: ActivityNewAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_account)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        replaceFragment(R.id.container_new_account, NewAccountFragment.build())
    }
}