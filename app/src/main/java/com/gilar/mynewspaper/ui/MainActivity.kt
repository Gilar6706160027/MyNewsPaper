package com.gilar.mynewspaper.ui

import am.appwise.components.ni.NoInternetDialog
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.gilar.mynewspaper.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.gilar.mynewspaper.database.ArticleDatabase
import com.gilar.mynewspaper.repository.NewsRepository
import com.gilar.mynewspaper.util.setOnItemReselectedListener
import com.gilar.mynewspaper.util.slideDown
import com.gilar.mynewspaper.util.slideUp
import com.gilar.mynewspaper.util.swipeDetector.SwipeActions
import com.gilar.mynewspaper.util.swipeDetector.SwipeGestureDetector
import github.com.st235.lib_expandablebottombar.navigation.ExpandableBottomBarNavigationUI
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var viewModel: NewsViewModel
    lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    lateinit var newsRepository: NewsRepository
    lateinit var noInternetDialog: NoInternetDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MyNewsPaper)
        newsRepository = NewsRepository(this, ArticleDatabase(this))


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)

        detectSwipeGestures()

        noInternetDialog = noInternetAlert()

        val navController = findNavController(R.id.nav_host_fragment)
        ExpandableBottomBarNavigationUI.setupWithNavController(
            bottom_bar,
            navController
        )
        bottom_bar.setOnItemReselectedListener { view, menuItem ->
            viewModel.currentNewsPosition = 0
            navController.navigate(menuItem.itemId)
        }

    }

    private fun detectSwipeGestures() {
        val swipeGestureDetector = SwipeGestureDetector(object : SwipeActions {
            override fun onSwipeLeft() {}

            override fun onSwipeUp() {
                if (bottom_bar.isVisible) bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                else bottom_bar.slideUp()
            }

            override fun onSwipeDown() {
                bottom_bar.slideDown()
            }
        })

        val gestureDetectorCompat = GestureDetectorCompat(applicationContext, swipeGestureDetector)
        btn_swipe_up.setOnTouchListener { view, motionEvent ->
            gestureDetectorCompat.onTouchEvent(motionEvent)
            view.performClick()
            true
        }
    }

    private fun restartActivity() {
        val options = ActivityOptions.makeCustomAnimation(
            this,
            android.R.anim.fade_in,
            android.R.anim.fade_out
        )
        startActivity(Intent(applicationContext, MainActivity::class.java), options.toBundle())
        finish()
    }

    private fun noInternetAlert() = NoInternetDialog.Builder(this)
        .setCancelable(false)
        .setDialogRadius(50f)
        .setBgGradientCenter(resources.getColor(R.color.light_blue))
        .setBgGradientStart(resources.getColor(R.color.light_blue))
        .setBgGradientEnd(resources.getColor(R.color.light_blue))
        .setButtonColor(resources.getColor(R.color.white))
        .setButtonIconsColor(resources.getColor(R.color.light_blue))
        .setButtonTextColor(resources.getColor(R.color.black))
        .setWifiLoaderColor(resources.getColor(R.color.light_blue))
        .build()


    override fun onDestroy() {
        super.onDestroy()
        noInternetDialog.onDestroy()
    }


    override fun onBackPressed() {
        if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        } else {
            super.onBackPressed()
        }

    }

}