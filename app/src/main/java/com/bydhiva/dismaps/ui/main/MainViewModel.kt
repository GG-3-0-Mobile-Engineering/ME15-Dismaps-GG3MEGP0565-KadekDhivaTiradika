package com.bydhiva.dismaps.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bydhiva.dismaps.base.Status
import com.bydhiva.dismaps.common.getExceptionMessageId
import com.bydhiva.dismaps.domain.model.Disaster
import com.bydhiva.dismaps.domain.model.DisasterType
import com.bydhiva.dismaps.domain.model.ReportsFilter
import com.bydhiva.dismaps.domain.usecase.disaster.GetReportsUseCase
import com.bydhiva.dismaps.utils.debounce
import com.google.android.material.search.SearchView.TransitionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getReportsUseCase: GetReportsUseCase
): ViewModel() {
    private val _isListLoading = MutableLiveData<Boolean>()
    val isListLoading: LiveData<Boolean> get() = _isListLoading

    private val _listDisaster = MutableLiveData<List<Disaster>>()
    val listDisaster: LiveData<List<Disaster>> get() = _listDisaster

    private val _searchViewState = MutableLiveData<TransitionState>()
    val searchViewState: LiveData<TransitionState> get() = _searchViewState

    private val _reportsFilter = MutableStateFlow(ReportsFilter())
    val reportsFilter: LiveData<ReportsFilter> get() = _reportsFilter.asLiveData().debounce(500, viewModelScope)

    private val _searchText = MutableStateFlow("")
    val searchText: LiveData<String> get() = _searchText.asLiveData().debounce(500, viewModelScope)

    private val _mainUIEvent = MutableLiveData<MainUIEvent>()
    val mainUIEvent: LiveData<MainUIEvent> get() = _mainUIEvent

    private val _selectedDisaster = MutableLiveData<Disaster?>()
    val selectedDisaster get() = _selectedDisaster

    init {
        getReports()
    }

    fun getReports() = viewModelScope.launch {
        getReportsUseCase(
            searchQuery = _searchText.value,
            disasterType = _reportsFilter.value.disasterType,
            startEndDate = _reportsFilter.value.startEndDate
        ).collectLatest { result ->
            when(result) {
                is Status.Loading -> _isListLoading.postValue(true)
                is Status.Success -> {
                    _isListLoading.postValue(false)
                    result.data?.let {
                        _listDisaster.postValue(it)
                        if (it.isEmpty())
                            _mainUIEvent.postValue(MainUIEvent.EmptyEvent)
                        else
                            _mainUIEvent.postValue(MainUIEvent.SuccessEvent)
                    }
                }
                is Status.Error -> {
                    _isListLoading.postValue(false)
                    result.error?.let {
                        _mainUIEvent.postValue(MainUIEvent.ErrorEvent(it.getExceptionMessageId()))
                    }
                }
            }
        }
    }

    fun setSearchViewState(state: TransitionState) = _searchViewState.postValue(state)

    fun setSelectedDisaster(disaster: Disaster?) = _selectedDisaster.postValue(disaster)

    fun setSearchText(text: String) = viewModelScope.launch {
        _searchText.emit(text)
    }

    fun setDisasterTypeFilter(disasterType: DisasterType?) = viewModelScope.launch {
        _reportsFilter.emit(
            _reportsFilter.value.copy(disasterType = disasterType)
        )
    }

    fun setDateFilter(startEndDate: Pair<Date, Date>?) = viewModelScope.launch {
        _reportsFilter.emit(
            _reportsFilter.value.copy(
                startEndDate = startEndDate
            )
        )
    }

    sealed class MainUIEvent {
        data class ErrorEvent(val messageStringId: Int): MainUIEvent()
        object EmptyEvent: MainUIEvent()
        object SuccessEvent: MainUIEvent()
    }
}