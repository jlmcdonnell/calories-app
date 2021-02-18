package dev.mcd.untitledcaloriesapp.domain.common.time

interface DateTimeProvider {
    /**
     * Milliseconds since Unix epoch
     */
    val now: Long

    val date: DateString
}
