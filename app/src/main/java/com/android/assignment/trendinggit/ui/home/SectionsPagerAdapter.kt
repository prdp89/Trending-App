package com.android.assignment.trendinggit.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.android.assignment.trendinggit.ui.developers.DeveloperFragment
import com.android.assignment.trendinggit.ui.repository.RepositoryFragment

const val REPO_PAGE_INDEX = 0
const val DEV_PAGE_INDEX = 1

class SectionsPagerAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        REPO_PAGE_INDEX to { RepositoryFragment() },
        DEV_PAGE_INDEX to { DeveloperFragment() }
    )

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}