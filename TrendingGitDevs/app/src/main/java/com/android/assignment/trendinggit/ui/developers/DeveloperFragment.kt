package com.android.assignment.trendinggit.ui.developers

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.android.assignment.trendinggit.R
import com.android.assignment.trendinggit.databinding.FragmentDeveloperBinding
import com.android.assignment.trendinggit.datasource.roomdb.entity.TrendingDevEntity
import com.android.assignment.trendinggit.utils.AppExecutors
import com.android.assignment.trendinggit.utils.ApplicationUtils
import com.android.assignment.trendinggit.utils.Status
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_repository.*
import javax.inject.Inject

class DeveloperFragment : DaggerFragment() {

    @Inject
    lateinit var mViewModel: TrendingDevViewModel

    @Inject
    lateinit var mAppExecutors: AppExecutors

    private var mBinding: FragmentDeveloperBinding? = null

    private var mAdapter: DevListAdapter? = null

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
        initRecyclerView()
        observeViewEvents()
    }

    private fun initRecyclerView() {
        val adapter = DevListAdapter(mAppExecutors) {
        }

        this.mBinding?.rvDevList?.adapter = adapter
        this.mAdapter = adapter
    }

    private fun subscribeLiveData() {
        mViewModel.devLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when (it.status) {
                Status.LOADING -> {
                    showShimmer()
                }
                Status.SUCCESS -> {
                    mBinding?.rvDevList?.visibility = View.VISIBLE
                    mBinding?.tvEmpty?.visibility = View.GONE

                    val result = it.data as List<TrendingDevEntity>

                    if (result.isNotEmpty()) {
                        mAdapter?.submitList(result)
                        mAdapter?.notifyDataSetChanged()
                    } else {
                        showEmptyLayout()
                    }
                    stopShimmer()
                }
                Status.ERROR -> {
                    showEmptyLayout()
                    stopShimmer()
                    ApplicationUtils.showSnackBar(
                        mBinding?.root,
                        getString(R.string.txt_error_api)
                    )
                }
            }
        })
    }

    private fun observeViewEvents() {

        mBinding?.swipeRefreshLayout?.setOnRefreshListener {
            if (ApplicationUtils.isNetworkAvailable(context!!)) {
                mViewModel.refreshDevLiveData().observe(viewLifecycleOwner, Observer {
                    mViewModel.triggerDevLiveData(true)

                    mBinding?.rvDevList?.visibility = View.GONE
                    showShimmer()
                })
            } else {
                ApplicationUtils.showSnackBar(mBinding?.root, getString(R.string.txt_alien))
            }
            swipe_refresh_layout.isRefreshing = false
        }
    }

    private fun showShimmer() {
        mBinding?.shimmerViewContainer?.visibility = View.VISIBLE
        mBinding?.shimmerViewContainer?.startShimmer()
    }

    private fun stopShimmer() {
        mBinding?.shimmerViewContainer?.stopShimmer()
        mBinding?.shimmerViewContainer?.visibility = View.GONE
    }

    private fun showEmptyLayout() {
        mBinding?.rvDevList?.visibility = View.GONE
        mBinding?.tvEmpty?.visibility = View.VISIBLE
        mBinding?.tvEmpty?.text = getString(R.string.it_s_empty_here)
    }
}
