package com.android.assignment.trendinggit.datasource.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.android.assignment.trendinggit.datasource.network.ApiInterface
import com.android.assignment.trendinggit.datasource.roomdb.Database
import com.android.assignment.trendinggit.datasource.roomdb.entity.TrendingDevEntity
import com.android.assignment.trendinggit.datasource.roomdb.entity.TrendingRepoEntity
import com.android.assignment.trendinggit.utils.ApplicationUtils
import com.android.assignment.trendinggit.utils.Resource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val mApiInterface: ApiInterface,
    private val mContext: Context,
    private val mAppDatabase: Database
) {

    fun getAllRepo(isRefresh: Boolean) =
        liveData<Resource<List<TrendingRepoEntity>>>(Dispatchers.IO) {

            val mutableLiveData = MutableLiveData<Resource<List<TrendingRepoEntity>>>()

            emit(Resource.loading(""))

            val repoData = mAppDatabase.trendingRepoDao().loadTrendingRepos()
            if (repoData.isNotEmpty() && !isRefresh) {
                mutableLiveData.postValue(
                    Resource.success(
                        "", repoData
                    )
                )
                emitSource(mutableLiveData)
                return@liveData
            }

            if (ApplicationUtils.isNetworkAvailable(mContext) && repoData.isEmpty()) {
                val response = mApiInterface.getAllRepos()

                var repoId = -1
                response.forEach { repoItem ->
                    repoItem.id = ++repoId

                    mAppDatabase.trendingRepoDao().insertTrendingRepo(repoItem)

                    if (null != repoItem.builtBy && repoItem.builtBy?.isNotEmpty()!!) {
                        repoItem.builtBy?.forEach { builtByItem ->
                            builtByItem.mTrendingRepoId = repoId
                        }
                        mAppDatabase.trendingRepoDevDao()
                            .insertTrendingRepoDevList(repoItem.builtBy!!)
                    }
                }

                mutableLiveData.postValue(Resource.success("", response))

                emitSource(mutableLiveData)
            }
        }

    fun getAllDev(isRefresh: Boolean) =
        liveData<Resource<List<TrendingDevEntity>>>(Dispatchers.IO) {

            val mutableLiveData = MutableLiveData<Resource<List<TrendingDevEntity>>>()

            emit(Resource.loading(""))

            val devData = mAppDatabase.trendingDevDao().loadTrendingDevs()
            if (devData.isNotEmpty() && !isRefresh) {
                mutableLiveData.postValue(
                    Resource.success(
                        "", devData
                    )
                )
                emitSource(mutableLiveData)
                return@liveData
            }

            if (ApplicationUtils.isNetworkAvailable(mContext) && devData.isEmpty()) {
                val response = mApiInterface.getAllDevs()
                mAppDatabase.trendingDevDao().insertTrendingDevList(response)

                mutableLiveData.postValue(Resource.success("", response))

                emitSource(mutableLiveData)
            }
        }

    fun clearAllRepo() = liveData(Dispatchers.IO)
    {
        mAppDatabase.trendingRepoDao().deleteOldRepos()
        emit(mAppDatabase.trendingRepoDevDao().deleteOldRepoDev())
    }

    fun clearAllDev() = liveData(Dispatchers.IO)
    {
        emit(mAppDatabase.trendingDevDao().deleteOldDevs())
    }

    fun getRepoWithDevData(repoId: Int) = liveData(Dispatchers.IO) {
        emit(mAppDatabase.trendingRepoDao().loadTrendingRepoWithDevData(repoId))
    }

    //TODO: We can Implement PagedLIst to Fetch Data From DB
    /* private val PAGE_SIZE = 10
     fun getAllRepo(isRefresh: Boolean) =
         liveData<Resource<PagedList<TrendingRepoEntity>>>(Dispatchers.IO) {

             val mutableLiveData = MutableLiveData<Resource<PagedList<TrendingRepoEntity>>>()

             emit(Resource.loading(""))

             val repoData = mAppDatabase.trendingRepoDao().loadTrendingReposPaged()

             val pagedData = getPagedListBuilder(repoData)

             if (null!= pagedData.value &&pagedData.value?.size!! > 0 && !isRefresh) {
                 mutableLiveData.postValue(Resource.success("", pagedData.value))
                 emitSource(mutableLiveData)
                 return@liveData
             }

             if (ApplicationUtils.isNetworkAvailable(mContext) && pagedData.value?.size == 0) {
                 val response = mApiInterface.getAllRepos()

                 var repoId = -1
                 response.forEach { repoItem ->
                     repoItem.id = ++repoId

                     mAppDatabase.trendingRepoDao().insertTrendingRepo(repoItem)

                     if (null != repoItem.builtBy && repoItem.builtBy?.isNotEmpty()!!) {
                         repoItem.builtBy?.forEach { builtByItem ->
                             builtByItem.mTrendingRepoId = repoId
                         }
                         mAppDatabase.trendingRepoDevDao()
                             .insertTrendingRepoDevList(repoItem.builtBy!!)
                     }
                 }

                 val repoNetworkData = mAppDatabase.trendingRepoDao().loadTrendingReposPaged()
                 val pagedNetworkData = getPagedListBuilder(repoNetworkData)

                 mutableLiveData.postValue(Resource.success("", pagedNetworkData.value))
                 emitSource(mutableLiveData)
             }
         }*/

    /*
      private fun <T> getPagedListBuilder(repoData: DataSource.Factory<Int, T>): LiveData<PagedList<T>> {
        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .build()
        return LivePagedListBuilder(repoData, config).build()
    }
     */
}