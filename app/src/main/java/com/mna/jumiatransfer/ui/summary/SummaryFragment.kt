package com.mna.jumiatransfer.ui.summary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.mna.jumiatransfer.MainActivity
import com.mna.jumiatransfer.R
import com.mna.jumiatransfer.databinding.SummaryFragmentBinding
import com.mna.jumiatransfer.ui.confirmation.ConfirmationFragment

class SummaryFragment : Fragment() {

    private lateinit var mBinding: SummaryFragmentBinding
    private var mViewModel: SummaryViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = SummaryFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(SummaryViewModel::class.java)
        mBinding.viewModel = mViewModel
        mBinding.handlers = this
    }

    fun onConfirmTransfer(@Suppress("UNUSED_PARAMETER") v: View) {
        activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.container, ConfirmationFragment.newInstance(), MainActivity.CONFIRMATION_FRAGMENT)
                .addToBackStack(MainActivity.CONFIRMATION_FRAGMENT)
                .commit()
    }

    companion object {
        fun newInstance(): SummaryFragment {
            return SummaryFragment()
        }
    }
}