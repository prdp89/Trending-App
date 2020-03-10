package com.android.assignment.trendinggit.ui.repository.repodetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.android.assignment.trendinggit.datasource.repository.HomeRepository
import com.android.assignment.trendinggit.datasource.roomdb.entity.TrendingRepoWithDevData
import javax.inject.Inject

class RepoDetailViewModel @Inject constructor(private val mHomeRepository: HomeRepository) :
    ViewModel() {

    private val mRepoWithBuiltData = MutableLiveData<Int>()

    val repoDetailLiveData: LiveData<TrendingRepoWithDevData> = mRepoWithBuiltData.switchMap {
        mHomeRepository.getRepoWithDevData(it)
    }

    fun triggerRepoLiveData(repoId: Int) {
        mRepoWithBuiltData.value = repoId
    }
}
