package com.mna.jumiatransfer.ui.amount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.mna.jumiatransfer.databinding.AmountFragmentBinding

class AmountFragment : Fragment() {

    private lateinit var mBinding: AmountFragmentBinding
    private var mViewModel: AmountViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = AmountFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(AmountViewModel::class.java)
    }

    companion object {
        fun newInstance(): AmountFragment {
            return AmountFragment()
        }
    }
}