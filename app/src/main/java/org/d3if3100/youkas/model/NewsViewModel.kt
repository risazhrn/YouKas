package org.d3if3100.youkas.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3100.youkas.db.entity.News
import org.d3if3100.youkas.network.NewsApi
import org.d3if3100.youkas.network.UpdateWorker
import java.lang.Exception
import java.util.concurrent.TimeUnit

class NewsViewModel: ViewModel() {
    private val data = MutableLiveData<News>()
    private val status = MutableLiveData<NewsApi.ApiStatus>()

    init {
        retrieveData()
    }

    private fun retrieveData() = viewModelScope.launch(Dispatchers.IO) {
        status.postValue(NewsApi.ApiStatus.LOADING)
        try {
            val result = NewsApi.service.getNews()
            data.postValue(result)
            status.postValue(NewsApi.ApiStatus.SUCCESS)
            Log.d("MainViewModel", "$result")
        } catch (e: Exception) {
            Log.d("MainViewModel", "Failure ${e.message}")
            status.postValue(NewsApi.ApiStatus.FAILED)
        }
    }

    fun getData() : LiveData<News> = data

    fun getStatus(): LiveData<NewsApi.ApiStatus> = status

    fun scheduleUpdater(app: Application) {
        val request = OneTimeWorkRequestBuilder<UpdateWorker>()
            .setInitialDelay(1, TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(app).enqueueUniqueWork(
            UpdateWorker.WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            request
        )
    }
}