package dev.mcd.untitledcaloriesapp.data.common.time

import dev.mcd.untitledcaloriesapp.domain.common.time.TimeProvider

class SystemTimeProvider : TimeProvider {
    override val now: Long
        get() = System.currentTimeMillis()
}
