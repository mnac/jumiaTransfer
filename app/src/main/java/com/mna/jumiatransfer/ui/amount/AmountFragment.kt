package com.mna.jumiatransfer.ui.amount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.mna.jumiatransfer.databinding.AmountFragmentBinding
import com.mna.jumiatransfer.utils.PriceTextWatcher
import java.text.DecimalFormat
import java.text.NumberFormat

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
        mBinding.handlers = this
        mBinding.viewModel = mViewModel
        mBinding.amountEdtTxt.requestFocus()

        PriceTextWatcher(mBinding.amountEdtTxt)

        val numberFormat = NumberFormat.getCurrencyInstance() as DecimalFormat
        val positivePrefix = numberFormat.positivePrefix
        val positiveSuffix = numberFormat.positiveSuffix
        if (!positivePrefix.isNullOrBlank()) {
            mBinding.currencyPrefixTxtVw.text = positivePrefix
            mBinding.currencySuffixTxtVw.visibility = View.GONE
        } else {
            mBinding.currencySuffixTxtVw.text = positiveSuffix
            mBinding.currencyPrefixTxtVw.visibility = View.GONE
        }

    }

    fun onValidAmount(@Suppress("UNUSED_PARAMETER") v: View) {

    }

    companion object {
        fun newInstance(): AmountFragment {
            return AmountFragment()
        }
    }
}