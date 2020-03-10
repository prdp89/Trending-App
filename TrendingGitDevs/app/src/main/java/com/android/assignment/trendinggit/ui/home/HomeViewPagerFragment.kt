package com.android.assignment.trendinggit.ui.home

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.android.assignment.trendinggit.R
import com.android.assignment.trendinggit.databinding.FragmentHomeViewPagerBinding
import com.android.assignment.trendinggit.datasource.roomdb.entity.TrendingDevEntity
import com.android.assignment.trendinggit.datasource.roomdb.entity.TrendingRepoEntity
import com.android.assignment.trendinggit.ui.developers.TrendingDevViewModel
import com.android.assignment.trendinggit.ui.repository.RepositoryViewModel
import com.android.assignment.trendinggit.utils.ApplicationUtils
import com.android.assignment.trendinggit.utils.Status
import com.google.android.material.tabs.TabLayoutMediator
import com.miguelcatalan.materialsearchview.MaterialSearchView
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_home_view_pager.*
import javax.inject.Inject

class HomeViewPagerFragment : DaggerFragment() {

    companion object {
        fun newInstance() = HomeViewPagerFragment()
    }

    private var mBinding: FragmentHomeViewPagerBinding? = null

    private var mTrendingRepoList = ArrayList<TrendingRepoEntity>()
    private var mTrendingDevList = ArrayList<TrendingDevEntity>()

    private var mRepoListViewModel: RepositoryViewModel? = null
    private var mTrendingDevViewModel: TrendingDevViewModel? = null

    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentHomeViewPagerBinding>(
            inflater,
            R.layout.fragment_home_view_pager,
            container,
            false
        )

        val tabLayout = binding.tabs
        val viewPager = binding.viewPager

        viewPager.adapter = SectionsPagerAdapter(this)

        // Set the icon and text for each tab
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setIcon(getTabIcon(position))
            tab.text = getTabTitle(position)
        }.attach()

        mBinding = binding
        return binding?.root!!
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater)
        menuInflater.inflate(R.menu.menu_main, menu)

        val item = menu.findItem(R.id.action_search)
        if (null != item) {
            mBinding?.searchView?.setMenuItem(item)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        initViewModels()

        observeViewEvents()
        subscribeLiveData()
    }

    private fun initViewModels() {
        mRepoListViewModel = ViewModelProviders.of(activity!!, mViewModelFactory)
            .get(RepositoryViewModel::class.java)

        mTrendingDevViewModel = ViewModelProviders.of(activity!!, mViewModelFactory)
            .get(TrendingDevViewModel::class.java)
    }

    private fun subscribeLiveData() {
        mRepoListViewModel?.repoLiveData?.observe(activity!!, Observer {
            if (it?.status == Status.SUCCESS) {
                mTrendingRepoList = it.data as ArrayList<TrendingRepoEntity>
            }
        })

        mTrendingDevViewModel?.devLiveData?.observe(activity!!, Observer {
            if (it?.status == Status.SUCCESS) {
                mTrendingDevList = it.data as ArrayList<TrendingDevEntity>
            }
        })
    }

    private fun observeViewEvents() {
        mBinding?.searchView?.setOnQueryTextListener(object :
            MaterialSearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String?): Boolean {
                val currentFragment = mBinding?.viewPager?.currentItem

                if (currentFragment == 0) {
                    performRepoListSearch(newText)
                } else {
                    performDevListSearch(newText)
                }

                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                val currentFragment = mBinding?.viewPager?.currentItem

                if (!TextUtils.isEmpty(query?.trim())) {
                    if (currentFragment == 0) {
                        performRepoListSearch(query)
                    } else {
                        performDevListSearch(query)
                    }
                }

                ApplicationUtils.hideKeyboard(mBinding?.root!!)
                return true
            }
        })

        mBinding?.searchView?.setOnSearchViewListener(object :
            MaterialSearchView.SearchViewListener {
            override fun onSearchViewClosed() {
                val currentFragment = mBinding?.viewPager?.currentItem
                if (currentFragment == 0)
                    mRepoListViewModel?.updateRepoList(mTrendingRepoList)
                else
                    mTrendingDevViewModel?.updateDevList(mTrendingDevList)
            }

            override fun onSearchViewShown() {
            }
        })
    }

    private fun performRepoListSearch(newText: String?) {
        val filteredList =
            mTrendingRepoList.filter {
                it.name.trim().toLowerCase().contains(newText?.toLowerCase()!!)
            }
        mRepoListViewModel?.updateRepoList(filteredList)
    }

    private fun performDevListSearch(newText: String?) {
        val filteredList =
            mTrendingDevList.filter {
                it.name.trim().toLowerCase().contains(newText?.toLowerCase()!!)
            }
        mTrendingDevViewModel?.updateDevList(filteredList)
    }

    private fun getTabIcon(position: Int): Int {
        return when (position) {
            REPO_PAGE_INDEX -> R.drawable.repo_tab_selector
            DEV_PAGE_INDEX -> R.drawable.dev_tab_selector
            else -> throw IndexOutOfBoundsException()
        }
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            REPO_PAGE_INDEX -> getString(R.string.repo_title)
            DEV_PAGE_INDEX -> getString(R.string.dev_title)
            else -> null
        }
    }
}
