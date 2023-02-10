package com.bnpparibasfortis.domain.model

import com.bnpparibasfortis.domain.model.Light.OFF

data class BerlinClock(
    val seconds: Light,
    val hourTopRow: List<Light>,
    val hourBottomRow: List<Light>,
    val minuteTopRow: List<Light>,
    val minuteBottomRow: List<Light>
) {
    //todo: put size in constants
    companion object {
        fun default() = BerlinClock(OFF, List(4) { OFF }, List(4) { OFF },
            List(4) { OFF }, List(11) { OFF })
    }
}
