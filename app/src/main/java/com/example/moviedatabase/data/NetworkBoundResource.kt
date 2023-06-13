package com.example.moviedatabase.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.moviedatabase.data.remote.network.ApiResponse
import com.example.moviedatabase.external.utils.AppExecutors
import com.example.moviedatabase.external.utils.Resource
import com.example.moviedatabase.external.utils.Status

abstract class NetworkBoundResource<ResultType, RequestType>(private val mExecutors: AppExecutors) {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)

        @Suppress("LeakingThis")
        val dbSource = loadFromDB()

        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetchData(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    result.value = Resource.success(newData)
                }

            }
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()

        result.addSource(dbSource) { newData ->
            result.value = Resource.loading(newData)
        }

        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            when (response.status) {
                Status.SUCCESS ->
                    mExecutors.diskIO().execute {
                        saveCallResult(response.body)
                        mExecutors.mainThread().execute {
                            result.addSource(loadFromDB()) { newData ->
                                result.value = Resource.success(newData)
                            }
                        }
                    }
                Status.EMPTY -> mExecutors.mainThread().execute {
                    result.addSource(loadFromDB()) { newData ->
                        result.value = Resource.success(newData)
                    }
                }
                Status.ERROR -> {
                    onFetchFailed()
                    result.addSource(dbSource) { newData ->
                        result.value = Resource.error(response.message, newData)
                    }
                }
                else -> {}
            }
        }
    }

    fun onFetchFailed() {}

    protected abstract fun saveCallResult(data: RequestType)

    abstract fun createCall(): LiveData<ApiResponse<RequestType>>

    abstract fun shouldFetchData(data: ResultType?): Boolean

    abstract fun loadFromDB(): LiveData<ResultType>
    fun asLiveData(): LiveData<Resource<ResultType>> = result
}