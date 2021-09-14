package com.soldatov.domain.repository

import com.soldatov.domain.models.DomainTaxiInfo

interface TaxiRepository {
    suspend fun getTaxiInfo(): List<DomainTaxiInfo>
}