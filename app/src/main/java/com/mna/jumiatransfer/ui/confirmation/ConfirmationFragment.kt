package com.mna.jumiatransfer.ui.confirmation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.mna.jumiatransfer.MainActivity
import com.mna.jumiatransfer.R
import com.mna.jumiatransfer.SharedViewModel
import com.mna.jumiatransfer.databinding.ConfirmationFragmentBinding
import com.mna.jumiatransfer.ui.intro.IntroFragment

class ConfirmationFragment : Fragment() {

    private lateinit var mBinding: ConfirmationFragmentBinding
    private var sharedViewModel: SharedViewModel? = null
    private var mViewModel: ConfirmationViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = ConfirmationFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sharedViewModel = activity?.let {
            ViewModelProviders.of(it).get(SharedViewModel::class.java)
        }
        mViewModel = ViewModelProviders.of(this).get(ConfirmationViewModel::class.java)
        mBinding.viewModel = mViewModel
        mBinding.handlers = this
    }

    fun onValidate(@Suppress("UNUSED_PARAMETER") v: View) {
        activity!!.supportFragmentManager
                .popBackStack(MainActivity.INTRO_FRAGMENT, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.container, IntroFragment.newInstance(), MainActivity.INTRO_FRAGMENT)
                .addToBackStack(MainActivity.INTRO_FRAGMENT)
                .commit()
    }

    companion object {
        private const val EMAIL_KEY = "email_key"
        private const val AMOUNT_KEY = "amount_key"

        fun newInstance(email: String, amount: Double): ConfirmationFragment {
            val fragment = ConfirmationFragment()
            val bundle = Bundle()
            bundle.putString(EMAIL_KEY, email)
            bundle.putDouble(AMOUNT_KEY, amount)
            fragment.arguments = bundle
            return fragment
        }
    }
}