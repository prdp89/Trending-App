package com.android.assignment.trendinggit.ui.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.android.assignment.trendinggit.datasource.repository.HomeRepository
import com.android.assignment.trendinggit.datasource.roomdb.entity.TrendingDevEntity
import com.android.assignment.trendinggit.datasource.roomdb.entity.TrendingRepoEntity
import com.android.assignment.trendinggit.utils.Resource
import javax.inject.Inject

class RepositoryViewModel @Inject constructor(private val mHomeRepository: HomeRepository) :
    ViewModel() {

    private val mRepoData = MutableLiveData<Boolean>()
    private val mDevData = MutableLiveData<Boolean>()

    val repoLiveData: LiveData<Resource<List<TrendingRepoEntity>>> = mRepoData.switchMap {
        mHomeRepository.getAllRepo(it)
    }

    val devLiveData: LiveData<Resource<List<TrendingDevEntity>>> = mDevData.switchMap {
        mHomeRepository.getAllDev(it)
    }

    fun triggerDevLiveData(isRefresh: Boolean) {
        mDevData.value = isRefresh
    }

    fun triggerRepoLiveData(isRefresh: Boolean) {
        mRepoData.value = isRefresh
    }
}