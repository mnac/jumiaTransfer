package com.mna.jumiatransfer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.mna.jumiatransfer.ui.main.MainFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance(), MAIN_FRAGMENT)
                    .commitNow()
        }

        supportActionBar?.elevation = 0F
    }

    override fun onBackPressed() {
        when {
            supportFragmentManager.findFragmentByTag(CONFIRMATION_FRAGMENT) != null ->
                popCurrentFragment(CONFIRMATION_FRAGMENT)
            supportFragmentManager.findFragmentByTag(SUMMARY_FRAGMENT) != null ->
                popCurrentFragment(SUMMARY_FRAGMENT)
            supportFragmentManager.findFragmentByTag(AMOUNT_FRAGMENT) != null ->
                popCurrentFragment(AMOUNT_FRAGMENT)
            supportFragmentManager.findFragmentByTag(FORM_FRAGMENT) != null ->
                popCurrentFragment(FORM_FRAGMENT)
            else -> {
                super.onBackPressed()
            }
        }
    }

    private fun popCurrentFragment(tag: String) {
        supportFragmentManager.popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    companion object {
        const val MAIN_FRAGMENT = "main_fragment"
        const val FORM_FRAGMENT = "form_fragment"
        const val AMOUNT_FRAGMENT = "amount_fragment"
        const val SUMMARY_FRAGMENT = "summary_fragment"
        const val CONFIRMATION_FRAGMENT = "confirmation_fragment"
    }
}
