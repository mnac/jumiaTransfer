package com.mna.jumiatransfer.ui.form

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mna.jumiatransfer.Main
import com.mna.jumiatransfer.MainActivity
import com.mna.jumiatransfer.R
import com.mna.jumiatransfer.databinding.FormFragmentBinding
import com.mna.jumiatransfer.repository.RepositoryCallback
import com.mna.jumiatransfer.ui.ItemClick
import com.mna.jumiatransfer.ui.amount.AmountFragment

class FormFragment : Fragment() {

    private lateinit var mBinding: FormFragmentBinding
    private lateinit var mViewModel: FormViewModel
    private lateinit var mMain: Main

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mMain = context as Main
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = FormFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(FormViewModel::class.java)
        mBinding.viewModel = mViewModel
        mBinding.handlers = this

        mBinding.emailEdtTxt.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!mViewModel.isEmailEmpty() && !mViewModel.isInvalidEmail()) {
                    onEmailValid()
                }
            }
        })

        mBinding.walletEdtTxt.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!mViewModel.isWalletIdEmpty() && !mViewModel.isWalletIdInvalid()) {
                    onWalletIdValid()
                }
            }
        })


        mMain.getUserRepository().getWalletIdsHistory(object: RepositoryCallback<ArrayList<String>> {
            override fun onSuccess(data: ArrayList<String>) {
                onHistory(data)
            }
        })

        mMain.getUserRepository().getEmail(object: RepositoryCallback<String> {
            override fun onSuccess(data: String) {
                mViewModel.email.set(data)
            }
        })
    }

    private fun onHistory(data: ArrayList<String>) {
        if (data.isNotEmpty()) {
            mBinding.walletIdsRecyclerVw.layoutManager = LinearLayoutManager(context)
            mBinding.walletIdsRecyclerVw.adapter = HistoryAdapter(data, object : ItemClick<String> {
                override fun onClick(data: String) {
                    onClickHistoryItem(data)
                }
            })

            mBinding.walletIdsTitleTxtVw.visibility = View.VISIBLE
            mBinding.walletIdsRecyclerVw.visibility = View.VISIBLE
        } else {
            mBinding.walletIdsTitleTxtVw.visibility = View.GONE
            mBinding.walletIdsRecyclerVw.visibility = View.GONE
        }
    }

    private fun onClickHistoryItem(walletId: String) {
        mBinding.viewModel?.walletId?.set(walletId)
    }

    fun onValidForm(@Suppress("UNUSED_PARAMETER") v: View) {
        if (isAdded) {
            when {
                mViewModel.isWalletIdEmpty() -> {
                    onWalletIdError(R.string.form_wallet_id_empty)
                    return
                }
                mViewModel.isWalletIdInvalid() -> {
                    onWalletIdError(R.string.form_wallet_id_invalid)
                    return
                }
                else -> {
                    onWalletIdValid()
                }
            }

            when {
                mViewModel.isEmailEmpty() -> {
                    onEmailError(R.string.form_email_empty)
                    return
                }
                mViewModel.isInvalidEmail() -> {
                    onEmailError(R.string.form_email_invalid)
                    return
                }
                else -> {
                    onEmailValid()
                }
            }

            mMain.getUserRepository().updateEmail(mViewModel.email.get()!!)
            mMain.getUserRepository().addWalletIdInHistory(mViewModel.walletId.get()!!)

            activity!!.supportFragmentManager.beginTransaction()
                    .replace(R.id.container, AmountFragment.newInstance(), MainActivity.AMOUNT_FRAGMENT)
                    .addToBackStack(MainActivity.AMOUNT_FRAGMENT)
                    .commit()
        }
    }

    private fun onWalletIdError(@StringRes error: Int) {
        mBinding.walletEdtTxt.requestFocus()
        mBinding.walletIdTxtInputLyt.error = getString(error)
    }

    private fun onWalletIdValid() {
        mBinding.walletIdTxtInputLyt.error = null
    }

    private fun onEmailError(@StringRes error: Int) {
        mBinding.emailEdtTxt.requestFocus()
        mBinding.emailTxtInputLyt.error = getString(error)
    }

    private fun onEmailValid() {
        mBinding.emailTxtInputLyt.error = null
    }

    companion object {
        fun newInstance(): FormFragment {
            return FormFragment()
        }
    }
}