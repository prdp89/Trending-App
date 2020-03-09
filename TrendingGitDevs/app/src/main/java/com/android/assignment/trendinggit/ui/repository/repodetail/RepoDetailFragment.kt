package com.android.assignment.trendinggit.ui.repository.repodetail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.android.assignment.trendinggit.R
import com.android.assignment.trendinggit.databinding.FragmentRepoDetailBinding
import com.android.assignment.trendinggit.utils.AppExecutors
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class RepoDetailFragment : DaggerFragment() {

    companion object {
        private const val REPO_ID = "repoId"
        fun newInstance(repoId: Int): RepoDetailFragment = RepoDetailFragment().apply {
            arguments = Bundle().apply {
                this.putInt(REPO_ID, repoId)
            }
        }
    }

    private var mRepoId: Int? = null

    @Inject
    lateinit var mViewModel: RepoDetailViewModel

    @Inject
    lateinit var mAppExecutors: AppExecutors

    private var mBinding: FragmentRepoDetailBinding? = null

    private var mAdapter: RepoDevListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { bundle ->
            mRepoId = bundle.getInt(REPO_ID)
        }
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentRepoDetailBinding>(
            inflater,
            R.layout.fragment_repo_detail,
            container,
            false
        )

        mBinding = dataBinding

        //setHasOptionsMenu(true)
        return mBinding?.root!!
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mRepoId?.let { mViewModel.triggerRepoLiveData(it) }
        subscribeLiveData()
        observeViewEvents()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val adapter = RepoDevListAdapter(mAppExecutors) {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it.href))
            if (null != browserIntent.resolveActivity(activity?.packageManager!!))
                startActivity(browserIntent)
        }

        this.mBinding?.rvRepoDevList?.adapter = adapter
        this.mAdapter = adapter
    }

    private fun observeViewEvents() {
        var isToolbarShown = false

        mBinding?.toolbar?.setOnClickListener {
            this.activity?.supportFragmentManager?.popBackStack()
        }

        mBinding?.scrollview?.setOnScrollChangeListener(
            NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->

                // User scrolled past image to height of toolbar and the title text is
                // underneath the toolbar, so the toolbar should be shown.
                val shouldShowToolbar = scrollY > mBinding?.toolbar?.height!!

                // The new state of the toolbar differs from the previous state; update
                // appbar and toolbar attributes.
                if (isToolbarShown != shouldShowToolbar) {
                    isToolbarShown = shouldShowToolbar

                    // Use shadow animator to add elevation if toolbar is shown
                    mBinding?.appbar?.isActivated = shouldShowToolbar

                    // Show the plant name if toolbar is shown
                    mBinding?.toolbarLayout?.isTitleEnabled = shouldShowToolbar
                }
            }
        )
    }

    private fun subscribeLiveData() {
        mViewModel.repoDetailLiveData.observe(viewLifecycleOwner, Observer {
            if (it?.mTrendingRepoEntity != null) {
                mBinding?.entity = it

                if (null != it.mTrendingRepoDevList && it.mTrendingRepoDevList?.isNotEmpty()!!) {
                    mAdapter?.submitList(it.mTrendingRepoDevList)
                    mAdapter?.notifyDataSetChanged()
                } else {
                    mBinding?.group?.visibility = View.GONE
                }
            }
        })
    }
}
