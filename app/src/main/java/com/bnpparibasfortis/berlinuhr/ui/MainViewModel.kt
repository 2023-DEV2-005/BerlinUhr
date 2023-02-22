package com.bnpparibasfortis.berlinuhr.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bnpparibasfortis.berlinuhr.ui.display.DisplayUiState
import com.bnpparibasfortis.usecase.TimeBerlinClock
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit.SECONDS
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val usecase: TimeBerlinClock) : ViewModel() {

    private val _uiState = MutableStateFlow(DisplayUiState())
    val uiState: StateFlow<DisplayUiState> = _uiState.asStateFlow()

    init {
        startTimer()
    }

    private fun startTimer() {
        viewModelScope.launch(Dispatchers.IO) {
            usecase().collect {
                _uiState.emit(
                    DisplayUiState(
                        LocalTime.now().truncatedTo(SECONDS).format(DateTimeFormatter.ISO_TIME),
                        it
                    )
                )
            }
        }
    }
}