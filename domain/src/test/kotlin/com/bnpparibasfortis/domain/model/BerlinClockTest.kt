package com.bnpparibasfortis.domain.model

import com.bnpparibasfortis.domain.model.Light.OFF
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.*

class BerlinClockTest {

    @Test
    fun `Test that default Berlin clock is made of OFF lights`() {
        expectThat(BerlinClock.default()).isEqualTo(
            BerlinClock(
                OFF, List(4) { OFF }, List(4) { OFF }, List(11) { OFF },
                List(4) { OFF })
        )
    }
}