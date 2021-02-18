package dev.mcd.untitledcaloriesapp.data.common.base.mapper

abstract class Mapper<in Data, out Domain> {
    abstract fun map(data: Data): Domain
}
