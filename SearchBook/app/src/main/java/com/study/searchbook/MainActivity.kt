package com.study.searchbook

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.study.searchbook.base.BaseActivity
import com.study.searchbook.databinding.ActivityMainBinding
import com.study.searchbook.view.BookViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MainActivity"

// Hilt 적용
@AndroidEntryPoint

// BaseActivity 적용
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    lateinit var navController: NavController

    override fun init() {
        initNavigation()
    }

    private fun initNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    // 홈 화면에서 뒤로가기 2번 클릭 시 종료
    var waitTime = 0L
    override fun onBackPressed() {
        if (navController.currentDestination?.id == R.id.bookFragment) {
            if (System.currentTimeMillis() - waitTime >= 2000) {
                waitTime = System.currentTimeMillis()
                showToast("뒤로가기를 한 번 더 누르시면 종료됩니다")
            } else {
                finish()
            }
        } else {
            super.onBackPressed()
        }
    }
}