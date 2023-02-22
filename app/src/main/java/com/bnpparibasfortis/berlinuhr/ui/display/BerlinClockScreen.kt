package com.bnpparibasfortis.berlinuhr.ui.display

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bnpparibasfortis.berlinuhr.ui.MainViewModel
import com.bnpparibasfortis.berlinuhr.ui.theme.burh_theme_clock_light_width_large
import com.bnpparibasfortis.berlinuhr.ui.theme.burh_theme_clock_light_width_small
import com.bnpparibasfortis.berlinuhr.ui.theme.md_theme_margin_padding_medium
import com.bnpparibasfortis.domain.model.BerlinClock
import com.bnpparibasfortis.domain.model.Light

@Composable
fun ComposeBerlinClock(modifier: Modifier = Modifier, viewModel: MainViewModel = viewModel()) {
    val displayUiState by viewModel.uiState.collectAsState()

    Column(
        modifier
            .wrapContentSize()
            .fillMaxSize()
            .padding(horizontal = md_theme_margin_padding_medium),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Center
    ) {
        ComposeDigitalTime(modifier, displayUiState.digitalFormatTime)
        ComposeSecondRow(modifier, displayUiState.berlinFormatTime.seconds)
        ComposeHourRows(modifier.weight(1f), displayUiState.berlinFormatTime)
        ComposeMinuteRows(modifier.weight(1f), displayUiState.berlinFormatTime)
    }
}

@Composable
fun ComposeDigitalTime(modifier: Modifier, time: String?) {
    Text(
        modifier = modifier
            .testTag("digitalTime")
            .wrapContentSize(),
        text = time.orEmpty(),
        style = MaterialTheme.typography.displayLarge
    )
}

@Composable
fun ComposeSecondRow(modifier: Modifier, light: Light) {
    BerlinClockLight(
        modifier = modifier.padding(md_theme_margin_padding_medium),
        tag = "secondsLamp",
        light = light,
        shape = CircleShape,
        sameAspectRatio = true
    )
}

@Composable
fun ComposeHourRows(modifier: Modifier, clock: BerlinClock) {
    ComposeLightsRow(modifier, clock.hourTopRow, "hourTopRow")
    ComposeLightsRow(modifier, clock.hourBottomRow, "hourBottomRow")
}

@Composable
fun ComposeMinuteRows(modifier: Modifier, clock: BerlinClock) {
    ComposeLightsRow(
        modifier,
        clock.minuteTopRow,
        "minuteTopRow",
        burh_theme_clock_light_width_small
    )
    ComposeLightsRow(modifier, clock.minuteBottomRow, "minuteBottomRow")
}

@Composable
fun ComposeLightsRow(
    modifier: Modifier,
    lights: List<Light>,
    tag: String,
    width: Dp = burh_theme_clock_light_width_large
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Center,
        verticalAlignment = CenterVertically
    ) {
        repeat(lights.size) { i ->
            BerlinClockLight(modifier, "$tag$i", lights[i], width)
        }
    }

}

