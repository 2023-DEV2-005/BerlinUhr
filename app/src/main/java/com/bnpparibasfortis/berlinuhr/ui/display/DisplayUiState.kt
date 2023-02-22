package com.bnpparibasfortis.berlinuhr.ui.display

import com.bnpparibasfortis.domain.model.BerlinClock

data class DisplayUiState(
    val digitalFormatTime: String? = null,
    val berlinFormatTime: BerlinClock = BerlinClock.default()
)