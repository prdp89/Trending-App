package com.android.assignment.trendinggit.ui.repository

import androidx.lifecycle.*
import com.android.assignment.trendinggit.datasource.repository.HomeRepository
import com.android.assignment.trendinggit.datasource.roomdb.entity.TrendingDevEntity
import com.android.assignment.trendinggit.datasource.roomdb.entity.TrendingRepoEntity
import com.android.assignment.trendinggit.utils.Resource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class RepositoryViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var mHomeRepository: HomeRepository

    private val mRepoData = MutableLiveData<Boolean>()

    val mFilteredRepoData = MutableLiveData<List<TrendingRepoEntity>>()

    val repoLiveData: LiveData<Resource<List<TrendingRepoEntity>>> = mRepoData.switchMap {
        mHomeRepository.getAllRepo(it)
    }

    fun refreshRepoLiveData() = liveData(Dispatchers.IO) {
        emitSource(mHomeRepository.clearAllRepo())
    }

    fun triggerRepoLiveData(isRefresh: Boolean) {
        mRepoData.value = isRefresh
    }

    fun updateRepoList(mTrendingRepoList: List<TrendingRepoEntity>) {
        mFilteredRepoData.value = mTrendingRepoList
    }

    //TODO: Pending PagedLIst Implementation
    //val mFilteredRepoData = MutableLiveData<PagedList<TrendingRepoEntity>>()

    /* val repoLiveData: LiveData<Resource<PagedList<TrendingRepoEntity>>> = mRepoData.switchMap {
         mHomeRepository.getAllRepo(it)
     }*/

}