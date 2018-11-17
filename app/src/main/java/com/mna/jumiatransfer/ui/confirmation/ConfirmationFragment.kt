package com.mna.jumiatransfer.ui.confirmation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.mna.jumiatransfer.databinding.ConfirmationFragmentBinding

class ConfirmationFragment : Fragment() {

    private lateinit var mBinding: ConfirmationFragmentBinding
    private var mViewModel: ConfirmationViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = ConfirmationFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(ConfirmationViewModel::class.java)
    }

    companion object {
        fun newInstance(): ConfirmationFragment {
            return ConfirmationFragment()
        }
    }
}