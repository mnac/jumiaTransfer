package com.mna.jumiatransfer.ui.main

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.mna.jumiatransfer.MainActivity
import com.mna.jumiatransfer.R
import com.mna.jumiatransfer.databinding.MainFragmentBinding
import com.mna.jumiatransfer.ui.form.FormFragment

class MainFragment : Fragment() {

    private lateinit var mBinding: MainFragmentBinding
    private var mViewModel: MainViewModel? = null


    @StringRes
    private val highlightResources = intArrayOf(
            R.string.vp_highlight_case_1,
            R.string.vp_highlight_case_2,
            R.string.vp_highlight_case_3)

    private var currentItem = 0
    private var maxItem = 0
    private val highlightHandler = Handler()
    private val runnable: Runnable = object : Runnable {
        override fun run() {
            highlightHandler.postDelayed(this, 3000)
            currentItem++
            if (currentItem >= maxItem) {
                currentItem = 0
            }
            mBinding.textSwitcher.setText(context?.getString(highlightResources[currentItem]))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = MainFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mBinding.handlers = this
        startHighlightAnimation()
    }

    private fun startHighlightAnimation() {
        maxItem = highlightResources.size

        val inAnim = AnimationUtils.loadAnimation(context, R.anim.slide_in_top)
        inAnim.duration = 250
        val outAnim = AnimationUtils.loadAnimation(context, R.anim.slide_out_bottom)
        outAnim.duration = 250
        mBinding.textSwitcher.inAnimation = inAnim
        mBinding.textSwitcher.outAnimation = outAnim

        highlightHandler.post(runnable)
    }

    fun onClickGoToTransfer(@Suppress("UNUSED_PARAMETER") v: View) {
        if (isAdded) {
            activity!!.supportFragmentManager.beginTransaction()
                    .replace(R.id.container, FormFragment.newInstance(), MainActivity.FORM_FRAGMENT)
                    .addToBackStack(MainActivity.FORM_FRAGMENT)
                    .commit()
        }
    }

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

}
