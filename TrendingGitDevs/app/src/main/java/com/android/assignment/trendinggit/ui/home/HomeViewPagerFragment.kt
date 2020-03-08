package com.android.assignment.trendinggit.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.android.assignment.trendinggit.R
import com.android.assignment.trendinggit.databinding.FragmentHomeViewPagerBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_home_view_pager.*

class HomeViewPagerFragment : DaggerFragment() {

    companion object {
        fun newInstance() = HomeViewPagerFragment()
    }

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

        return binding?.root!!
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
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
