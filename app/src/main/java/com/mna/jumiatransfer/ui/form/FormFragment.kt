package com.mna.jumiatransfer.ui.form

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.mna.jumiatransfer.databinding.FormFragmentBinding

class FormFragment : Fragment() {

    private lateinit var mBinding: FormFragmentBinding
    private var mViewModel: FormViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = FormFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(FormViewModel::class.java)
    }

    companion object {
        fun newInstance(): FormFragment {
            return FormFragment()
        }
    }
}