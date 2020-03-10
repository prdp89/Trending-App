package com.android.assignment.trendinggit.ui.repository

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.android.assignment.trendinggit.R
import com.android.assignment.trendinggit.databinding.FragmentRepositoryBinding
import com.android.assignment.trendinggit.datasource.roomdb.entity.TrendingRepoEntity
import com.android.assignment.trendinggit.ui.repository.repodetail.RepoDetailFragment
import com.android.assignment.trendinggit.utils.AppExecutors
import com.android.assignment.trendinggit.utils.ApplicationUtils
import com.android.assignment.trendinggit.utils.Status
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_repository.*
import javax.inject.Inject

class RepositoryFragment : DaggerFragment() {

    var mViewModel: RepositoryViewModel? = null

    @Inject
    lateinit var mAppExecutors: AppExecutors

    private var mBinding: FragmentRepositoryBinding? = null

    private var mAdapter: RepoListAdapter? = null

    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory

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

        mViewModel = ViewModelProviders.of(activity!!, mViewModelFactory)
            .get(RepositoryViewModel::class.java)

        mViewModel?.triggerRepoLiveData(false)

        subscribeLiveData()
        initRecyclerView()
        observeViewEvents()
    }

    private fun initRecyclerView() {
        val adapter = RepoListAdapter(mAppExecutors) {
            activity?.supportFragmentManager?.beginTransaction()
                ?.addToBackStack(null)
                ?.replace(R.id.container, RepoDetailFragment.newInstance(it.id!!))
                ?.commit()
        }

        this.mBinding?.rvRepoList?.adapter = adapter
        this.mAdapter = adapter
    }

    private fun observeViewEvents() {

        mBinding?.swipeRefreshLayout?.setOnRefreshListener {
            if (ApplicationUtils.isNetworkAvailable(context!!)) {
                mViewModel?.refreshRepoLiveData()?.observe(viewLifecycleOwner, Observer {
                    mViewModel?.triggerRepoLiveData(true)

                    mBinding?.rvRepoList?.visibility = View.GONE
                    showShimmer()
                })
            } else {
                ApplicationUtils.showSnackBar(mBinding?.root, getString(R.string.txt_alien))
            }
            swipe_refresh_layout.isRefreshing = false
        }
    }

    private fun subscribeLiveData() {
        mViewModel?.repoLiveData?.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> {
                    showShimmer()
                }
                Status.SUCCESS -> {
                    mBinding?.rvRepoList?.visibility = View.VISIBLE
                    mBinding?.tvEmpty?.visibility = View.GONE

                    stopShimmer()

                    val result = it.data as List<TrendingRepoEntity>

                    if (result.isNotEmpty()) {
                        mAdapter?.submitList(result)
                        mAdapter?.notifyDataSetChanged()
                    } else {
                        showEmptyLayout()
                    }
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

        mViewModel?.mFilteredRepoData?.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                mAdapter?.submitList(it)
                mAdapter?.notifyDataSetChanged()
            }
        })
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
        mBinding?.rvRepoList?.visibility = View.GONE
        mBinding?.tvEmpty?.visibility = View.VISIBLE
        mBinding?.tvEmpty?.text = getString(R.string.it_s_empty_here)
    }

}
