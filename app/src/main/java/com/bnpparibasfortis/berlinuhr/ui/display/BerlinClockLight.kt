package com.bnpparibasfortis.berlinuhr.ui.display

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import com.bnpparibasfortis.berlinuhr.ui.display.ClockLightDefaults.clockLightColors
import com.bnpparibasfortis.berlinuhr.ui.display.ClockLightDefaults.clockLightElevation
import com.bnpparibasfortis.berlinuhr.ui.theme.*
import com.bnpparibasfortis.domain.model.Light


@Composable
fun BerlinClockLight(
    modifier: Modifier,
    tag: String,
    light: Light,
    width: Dp = burh_theme_clock_light_width_large,
    height: Dp = burh_theme_clock_light_height,
    shape: Shape = ClockLightDefaults.shape,
    sameAspectRatio: Boolean = false
) {
    val enabled = light != Light.OFF
    val color = clockLightColors(light).containerColor(enabled).value
    val elevation = clockLightElevation().elevation(enabled).value

    Surface(
        modifier = modifier
            .testTag(tag)
            .padding(md_theme_margin_padding_small)
            .size(width, height)
            .run {
                if (sameAspectRatio) {
                    aspectRatio(1.0f)
                }
                else this
            }
        ,
        shape = shape,
        color = color,
        contentColor = color,
        tonalElevation = elevation,
        shadowElevation = elevation
    ) {}
}

internal object ClockLightDefaults {

    val shape: Shape @Composable get() = shapes.medium

    @Composable
    fun clockLightColors(
        light: Light,
        disabledContainerColor: Color =
            colorScheme.onSurface
                .copy(alpha = buhr_theme_clock_light_disabled_opacity)
                .compositeOver(
                    colorScheme.surfaceColorAtElevation(buhr_theme_clock_light_elevation_disabled)
                )
    ) = ClockLightColors(
        containerColor = clockLightToColor(light),
        disabledContainerColor = disabledContainerColor,
    )

    @Composable
    fun clockLightElevation(
        elevation: Dp = buhr_theme_clock_light_elevation_enabled,
        disabledElevation: Dp = buhr_theme_clock_light_elevation_disabled
    ) = ClockLightElevation(
        defaultElevation = elevation,
        disabledElevation = disabledElevation
    )

    private fun clockLightToColor(light: Light) = when (light) {
        Light.YELLOW -> clock_light_yellow
        Light.RED -> clock_light_red
        Light.OFF -> clock_light_off
    }
}

@Immutable
internal class ClockLightColors internal constructor(
    private val containerColor: Color,
    private val disabledContainerColor: Color,
) {
    @Composable
    internal fun containerColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(if (enabled) containerColor else disabledContainerColor)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is ClockLightColors) return false

        if (containerColor != other.containerColor) return false
        if (disabledContainerColor != other.disabledContainerColor) return false

        return true
    }

    override fun hashCode() = 31 * containerColor.hashCode() + disabledContainerColor.hashCode()
}

// todo: animate state change
@Immutable
class ClockLightElevation internal constructor(
    private val defaultElevation: Dp,
    private val disabledElevation: Dp
) {

    @Composable
    internal fun elevation(enabled: Boolean): State<Dp> {
        return remember { mutableStateOf(if (enabled) defaultElevation else disabledElevation) }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is ClockLightElevation) return false

        if (defaultElevation != other.defaultElevation) return false
        if (disabledElevation != other.disabledElevation) return false

        return true
    }

    override fun hashCode() = 31 * defaultElevation.hashCode() + disabledElevation.hashCode()
}