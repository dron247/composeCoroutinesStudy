package org.dementiev.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.dementiev.data.NewsRepository
import org.dementiev.data.doOnError
import org.dementiev.data.doOnSuccess
import timber.log.Timber

class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {
    val state: StateFlow<NewsState>
        get() = stateInternal.asStateFlow()

    private val stateInternal = MutableStateFlow<NewsState>(NewsState.Loading)

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            stateInternal.emit(NewsState.Loading)
            delay(1000)
            newsRepository.getNews().collect { result ->
                result
                    .doOnError { e ->
                        stateInternal.emit(NewsState.Error(e))
                        Timber.e(e)
                    }
                    .doOnSuccess { data ->
                        stateInternal.emit(NewsState.Content(data))
                    }
            }
        }
    }
}