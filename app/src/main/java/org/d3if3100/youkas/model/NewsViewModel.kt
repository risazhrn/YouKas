package org.d3if3100.youkas.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3100.youkas.db.entity.News
import org.d3if3100.youkas.network.NewsApi
import java.lang.Exception

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
        } catch (e: Exception) {
            Log.d("MainViewModel", "Failure ${e.message}")
            status.postValue(NewsApi.ApiStatus.FAILED)
        }
    }

    fun getData() : LiveData<News> = data

    fun getStatus(): LiveData<NewsApi.ApiStatus> = status
}