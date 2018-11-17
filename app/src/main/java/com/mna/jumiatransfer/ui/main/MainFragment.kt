package com.mna.jumiatransfer.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.mna.jumiatransfer.MainActivity
import com.mna.jumiatransfer.R
import com.mna.jumiatransfer.databinding.MainFragmentBinding
import com.mna.jumiatransfer.ui.form.FormFragment
import com.mna.jumiatransfer.ui.main.MainViewModel

class MainFragment : Fragment() {

    private lateinit var mBinding: MainFragmentBinding
    private var mViewModel: MainViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = MainFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mBinding.handlers = this
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
