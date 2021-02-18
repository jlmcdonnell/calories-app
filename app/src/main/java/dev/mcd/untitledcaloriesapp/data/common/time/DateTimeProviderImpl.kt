package dev.mcd.untitledcaloriesapp.data.common.time

import dev.mcd.untitledcaloriesapp.domain.common.time.DateTimeProvider
import java.time.LocalDate

class DateTimeProviderImpl : DateTimeProvider {
    override val now: Long
        get() = System.currentTimeMillis()

    override val date: String
        get() = LocalDate.now().toString()
}
