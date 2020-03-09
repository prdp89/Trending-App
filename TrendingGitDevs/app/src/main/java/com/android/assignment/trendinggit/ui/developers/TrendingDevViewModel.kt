package com.android.assignment.trendinggit.ui.developers

import androidx.lifecycle.*
import com.android.assignment.trendinggit.datasource.repository.HomeRepository
import com.android.assignment.trendinggit.datasource.roomdb.entity.TrendingDevEntity
import com.android.assignment.trendinggit.utils.Resource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class TrendingDevViewModel @Inject constructor(private val mHomeRepository: HomeRepository) :
    ViewModel() {

    private val mDevData = MutableLiveData<Boolean>()

    val devLiveData: LiveData<Resource<List<TrendingDevEntity>>> = mDevData.switchMap {
        mHomeRepository.getAllDev(it)
    }

    fun refreshDevLiveData() = liveData(Dispatchers.IO) {
        emitSource(mHomeRepository.clearAllDev())
    }

    fun triggerDevLiveData(isRefresh: Boolean) {
        mDevData.value = isRefresh
    }
}