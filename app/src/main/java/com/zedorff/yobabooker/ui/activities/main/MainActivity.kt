package com.zedorff.yobabooker.ui.activities.main

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import com.zedorff.yobabooker.R
import com.zedorff.yobabooker.databinding.ActivityMainBinding
import com.zedorff.yobabooker.model.db.entities.AccountEntity
import com.zedorff.yobabooker.model.db.entities.CategoryEntity
import com.zedorff.yobabooker.ui.activities.base.BaseActivity
import com.zedorff.yobabooker.ui.activities.main.fragments.accounts.AccountsFragment
import com.zedorff.yobabooker.ui.activities.main.fragments.categories.CategoriesFragment
import com.zedorff.yobabooker.ui.activities.main.fragments.piechart.PieChartFragment
import com.zedorff.yobabooker.ui.activities.main.fragments.transactions.TransactionsFragment
import com.zedorff.yobabooker.ui.activities.main.view.MainActivityView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener,
        MainActivityView {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, binding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener(this)
        savedInstanceState ?: replaceFragment(R.id.container_main, TransactionsFragment.build())
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_transactions -> {
                replaceFragment(R.id.container_main, TransactionsFragment.build())
            }
            R.id.nav_categories -> {
                replaceFragment(R.id.container_main, CategoriesFragment.build())
            }
            R.id.nav_pie_charts -> {
                replaceFragment(R.id.container_main, PieChartFragment.build())
            }
            R.id.nav_accounts -> {
                replaceFragment(R.id.container_main, AccountsFragment.build())
            }
        }

        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun openTransactionsForCategory(category: CategoryEntity) {
        replaceFragment(R.id.container_main, TransactionsFragment.build(category = category))
    }

    override fun openTransactionsForAccount(account: AccountEntity) {
        replaceFragment(R.id.container_main, TransactionsFragment.build(account = account))
    }
}
