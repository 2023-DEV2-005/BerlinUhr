package com.bnpparibasfortis.domain

import com.bnpparibasfortis.domain.model.BerlinClock
import com.bnpparibasfortis.domain.model.Light
import com.bnpparibasfortis.domain.model.Light.*
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BerlinClockConverter @Inject constructor() {

    fun from(time: String) =
        DateTimeFormatter.ISO_TIME.parse(time, LocalTime::from).toBerlinClock()

    private fun LocalTime.toBerlinClock() = BerlinClock(
        seconds = convertSecond(second),
        hourTopRow = convertHour(hourToBase4Times5()),
        hourBottomRow = convertHour(hourToBase4()),
        minuteTopRow = convertToMinuteTopRow(this),
        minuteBottomRow = convertToMinuteBottomRow(this)
    )

    private fun convertSecond(seconds: Int) = if (seconds.isEven()) YELLOW else OFF

    private fun convertHour(lights: Int) = buildLightList(lights, NUMBER_OF_LIGHT_PER_HOUR_ROW) {
        RED
    }

    private fun convertToMinuteTopRow(time: LocalTime) =
        convertMinute(time.minuteToBase5Times11(), NUMBER_OF_LIGHT_MINUTE_TOP_ROW) { index ->
            if ((index + 1).multipleOfThree())
                RED
            else
                YELLOW
        }

    private fun convertToMinuteBottomRow(time: LocalTime) =
        convertMinute(time.minuteToBase4(), NUMBER_OF_LIGHT_MINUTE_BOTTOM_ROW) {
            YELLOW
        }

    private fun convertMinute(lights: Int, capacity: Int, item: (index: Int) -> Light) =
        buildLightList(lights, capacity, item)

    private fun buildLightList(items: Int, capacity: Int, item: (index: Int) -> Light) =
        buildList(capacity) {
            addAll(List(items, item))
            addAll(List(capacity - items) { OFF })
        }.toList()

    private fun Int.isEven() = this % 2 == 0
    private fun LocalTime.hourToBase4Times5() = hour / 5
    private fun LocalTime.hourToBase4() = hour % 5
    private fun LocalTime.minuteToBase5Times11() = minute / 5
    private fun LocalTime.minuteToBase4() = minute % 5
    private fun Int.multipleOfThree() = this % 3 == 0

    companion object {
        const val NUMBER_OF_LIGHT_PER_HOUR_ROW = 4
        const val NUMBER_OF_LIGHT_MINUTE_TOP_ROW = 11
        const val NUMBER_OF_LIGHT_MINUTE_BOTTOM_ROW = 4
    }
}
