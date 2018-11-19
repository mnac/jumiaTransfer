package com.mna.jumiatransfer.ui.summary

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.mna.jumiatransfer.Main
import com.mna.jumiatransfer.MainActivity
import com.mna.jumiatransfer.R
import com.mna.jumiatransfer.SharedViewModel
import com.mna.jumiatransfer.databinding.SummaryFragmentBinding
import com.mna.jumiatransfer.model.TransferStatus
import com.mna.jumiatransfer.repository.RepositoryCallback
import com.mna.jumiatransfer.ui.DataConverters
import com.mna.jumiatransfer.ui.confirmation.ConfirmationFragment

class SummaryFragment : Fragment() {

    private lateinit var mBinding: SummaryFragmentBinding
    private var sharedViewModel: SharedViewModel? = null
    private var mViewModel: SummaryViewModel? = null
    private lateinit var mMain: Main

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mMain = context as Main
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = SummaryFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sharedViewModel = activity?.let {
            ViewModelProviders.of(it).get(SharedViewModel::class.java)
        }
        mViewModel = ViewModelProviders.of(this).get(SummaryViewModel::class.java)
        mViewModel!!.isLoading.set(false)
        mBinding.viewModel = mViewModel
        mBinding.handlers = this
        sharedViewModel?.let {
            mBinding.emailValue.text = it.email.value!!
            mBinding.amountValue.text = DataConverters.fromPrice(it.amount.value!!)
            mBinding.walletIdValue.text = it.walletId.value!!
        }
    }

    fun onConfirmTransfer(@Suppress("UNUSED_PARAMETER") v: View) {
        mViewModel?.isLoading?.set(true)
        sharedViewModel?.let {
            mMain.getTransferRepository().transfer(it.getAmount(), it.getWalletId(), it.getEmail(),
                    object : RepositoryCallback<TransferStatus> {
                        override fun onSuccess(data: TransferStatus) {
                            goToConfirmationFragment(data.userName, data.amount)
                            mViewModel?.isLoading?.set(false)
                        }
                    })
        }
    }

    private fun goToConfirmationFragment(email: String, amount: Double) {
        activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.container, ConfirmationFragment.newInstance(email, amount), MainActivity.CONFIRMATION_FRAGMENT)
                .addToBackStack(MainActivity.CONFIRMATION_FRAGMENT)
                .commit()
    }

    companion object {
        fun newInstance(): SummaryFragment {
            return SummaryFragment()
        }
    }
}