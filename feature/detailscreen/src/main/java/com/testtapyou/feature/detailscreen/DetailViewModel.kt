package com.testtapyou.feature.detailscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testtapyou.domain.model.Point
import com.testtapyou.domain.GetPointsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getPointsUseCase: GetPointsUseCase
) : ViewModel() {

    private val _event = MutableSharedFlow<UiEvent>(replay = 1)
    val event = _event.asSharedFlow()

    init {
        viewModelScope.launch {
            val points = getPointsUseCase.execute()
            _event.emit(UiEvent.ShowPoints(points))
        }
    }

}

sealed class UiEvent {
    data class ShowPoints(val points: List<Point>) : UiEvent()
}