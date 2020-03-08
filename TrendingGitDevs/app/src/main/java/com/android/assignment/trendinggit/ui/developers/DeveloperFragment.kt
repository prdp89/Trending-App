package com.android.assignment.trendinggit.ui.developers

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.android.assignment.trendinggit.R
import com.android.assignment.trendinggit.databinding.FragmentDeveloperBinding
import com.android.assignment.trendinggit.ui.repository.RepositoryViewModel
import com.android.assignment.trendinggit.utils.AppExecutors
import com.android.assignment.trendinggit.utils.Status
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class DeveloperFragment : DaggerFragment() {

    @Inject
    lateinit var mViewModel: RepositoryViewModel

    @Inject
    lateinit var mAppExecutors: AppExecutors

    private var mBinding: FragmentDeveloperBinding? = null

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
        val dataBinding = DataBindingUtil.inflate<FragmentDeveloperBinding>(
            inflater,
            R.layout.fragment_developer,
            container,
            false
        )

        mBinding = dataBinding
        return mBinding?.root!!
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //(activity as DaggerAppCompatActivity).setSupportActionBar(toolbar)

        mViewModel.triggerDevLiveData(false)
        subscribeLiveData()
    }

    private fun subscribeLiveData() {
        mViewModel.devLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
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
