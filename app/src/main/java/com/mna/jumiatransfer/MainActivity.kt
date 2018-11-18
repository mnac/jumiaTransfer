package com.mna.jumiatransfer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.mna.jumiatransfer.repository.TransferRepository
import com.mna.jumiatransfer.repository.TransferRepositoryImpl
import com.mna.jumiatransfer.repository.UserRepository
import com.mna.jumiatransfer.repository.UserRepositoryImpl
import com.mna.jumiatransfer.ui.intro.IntroFragment


class MainActivity : AppCompatActivity(), Main {

    companion object {
        const val INTRO_FRAGMENT = "intro_fragment"
        const val FORM_FRAGMENT = "form_fragment"
        const val AMOUNT_FRAGMENT = "amount_fragment"
        const val SUMMARY_FRAGMENT = "summary_fragment"
        const val CONFIRMATION_FRAGMENT = "confirmation_fragment"
    }

    private lateinit var userRepository: UserRepository
    private lateinit var transferRepository: TransferRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            setIntroFragment()
        }

        supportActionBar?.elevation = 0F

        userRepository = UserRepositoryImpl(applicationContext)
        transferRepository = TransferRepositoryImpl()
    }

    override fun onBackPressed() {
        when {
            supportFragmentManager.findFragmentByTag(CONFIRMATION_FRAGMENT) != null -> {
                popCurrentFragment(INTRO_FRAGMENT)
                setIntroFragment()
            }
            supportFragmentManager.findFragmentByTag(SUMMARY_FRAGMENT) != null ->
                popCurrentFragment(SUMMARY_FRAGMENT)
            supportFragmentManager.findFragmentByTag(AMOUNT_FRAGMENT) != null ->
                popCurrentFragment(AMOUNT_FRAGMENT)
            supportFragmentManager.findFragmentByTag(FORM_FRAGMENT) != null ->
                popCurrentFragment(FORM_FRAGMENT)
            supportFragmentManager.findFragmentByTag(INTRO_FRAGMENT) != null -> {
                finish()
            }
            else -> {
                super.onBackPressed()
            }
        }
    }

    private fun popCurrentFragment(tag: String) {
        supportFragmentManager.popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    private fun setIntroFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, IntroFragment.newInstance(), INTRO_FRAGMENT)
                .addToBackStack(INTRO_FRAGMENT)
                .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        userRepository.clear()
        transferRepository.clear()
    }

    override fun getUserRepository(): UserRepository {
        return userRepository
    }

    override fun getTransferRepository(): TransferRepository {
        return transferRepository
    }
}
