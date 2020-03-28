package com.android.assignment.trendinggit.ui.repository.repodetail

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.transition.TransitionInflater
import com.android.assignment.trendinggit.R
import com.android.assignment.trendinggit.databinding.FragmentRepoDetailBinding
import com.android.assignment.trendinggit.utils.AppExecutors
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
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

        postponeEnterTransition()
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move);

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
        mBinding?.toolbar?.setOnClickListener {
            this.activity?.supportFragmentManager?.popBackStack()
        }
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

                mBinding?.executePendingBindings()
                startPostponedEnterTransition()
            }
        })
    }
}
