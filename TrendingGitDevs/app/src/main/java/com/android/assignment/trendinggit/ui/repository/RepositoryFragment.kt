package com.android.assignment.trendinggit.ui.repository

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.android.assignment.trendinggit.R
import com.android.assignment.trendinggit.databinding.FragmentRepositoryBinding
import com.android.assignment.trendinggit.utils.AppExecutors
import com.android.assignment.trendinggit.utils.Status
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class RepositoryFragment : DaggerFragment() {

    @Inject
    lateinit var mViewModel: RepositoryViewModel

    @Inject
    lateinit var mAppExecutors: AppExecutors

    private var mBinding: FragmentRepositoryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentRepositoryBinding>(
            inflater,
            R.layout.fragment_repository,
            container,
            false
        )

        mBinding = dataBinding
        return mBinding?.root!!
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //(activity as DaggerAppCompatActivity).setSupportActionBar(toolbar)

        mViewModel.triggerRepoLiveData(false)
        subscribeLiveData()
    }

    private fun subscribeLiveData() {
        mViewModel.repoLiveData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> {

                }
                Status.SUCCESS -> {

                }
                Status.ERROR -> {

                }
            }
        })
    }

}
