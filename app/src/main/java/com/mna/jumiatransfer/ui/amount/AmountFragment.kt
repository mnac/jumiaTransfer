package com.mna.jumiatransfer.ui.amount

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.mna.jumiatransfer.MainActivity
import com.mna.jumiatransfer.R
import com.mna.jumiatransfer.SharedViewModel
import com.mna.jumiatransfer.databinding.AmountFragmentBinding
import com.mna.jumiatransfer.ui.summary.SummaryFragment
import com.mna.jumiatransfer.utils.PriceTextWatcher
import java.text.DecimalFormat
import java.text.NumberFormat

class AmountFragment : Fragment() {

    private lateinit var mBinding: AmountFragmentBinding
    private var sharedViewModel: SharedViewModel? = null
    private var mViewModel: AmountViewModel? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context != null) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = AmountFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sharedViewModel = activity?.let {
            ViewModelProviders.of(it).get(SharedViewModel::class.java)
        }
        mViewModel = ViewModelProviders.of(this).get(AmountViewModel::class.java)
        mBinding.handlers = this
        mBinding.viewModel = mViewModel
        mBinding.amountEdtTxt.requestFocus()

        PriceTextWatcher(mBinding.amountEdtTxt)
        mBinding.amountEdtTxt.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrBlank()) {
                    onAmountValid()
                }
            }
        })

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
        if (isAdded) {
            when {
                mViewModel!!.amount <= 0 -> {
                    onAmountError(R.string.amount_empty)
                    return
                }
                else -> {
                    sharedViewModel?.amount?.value = mViewModel!!.amount
                    onAmountValid()
                }
            }
            activity!!.supportFragmentManager.beginTransaction()
                    .replace(R.id.container, SummaryFragment.newInstance(), MainActivity.SUMMARY_FRAGMENT)
                    .addToBackStack(MainActivity.SUMMARY_FRAGMENT)
                    .commit()
        }
    }

    private fun onAmountError(@StringRes error: Int) {
        mBinding.amountEdtTxt.requestFocus()
        mBinding.amountEdtTxt.error = getString(error)
    }

    private fun onAmountValid() {
        mBinding.amountEdtTxt.error = null
    }

    companion object {
        fun newInstance(): AmountFragment {
            return AmountFragment()
        }
    }
}