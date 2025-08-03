package com.testtapyou.feature.mainscreen

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testtapyou.data.repository.PointsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    val repository: PointsRepository
) : ViewModel() {

    private val _event = MutableSharedFlow<UiEvent>()
    val event = _event.asSharedFlow()

    fun buttonGoPressed(input: String) {
        viewModelScope.launch {
            if (input.isNotEmpty() && input.isDigitsOnly()) {
                val result = repository.fetchPoints(input.toInt())
                if (result.isSuccess) {
                    val points = result.getOrThrow()
                    repository.clear()
                    repository.save(points)
                    _event.emit(UiEvent.NavigateDetails)
                } else {
                    val message = result.exceptionOrNull()?.message ?: "error"
                    _event.emit(UiEvent.ShowToast(message))
                }
            } else {
                _event.emit(UiEvent.ShowToast("error"))
            }
        }
    }
}

sealed class UiEvent {
    data class ShowToast(val message: String) : UiEvent()
    object NavigateDetails : UiEvent()
}