package com.soldatov.data.repository

import com.soldatov.data.api.PlaceHolderApi
import com.soldatov.data.models.TaxiInfo
import com.soldatov.domain.models.DomainTaxiInfo
import com.soldatov.domain.repository.TaxiRepository

private const val LAT_LON = "123"

class TaxiRepositoryImpl(private val placeHolderApi: PlaceHolderApi) : TaxiRepository {

    override suspend fun getTaxiInfo(): List<DomainTaxiInfo> {
        val taxiInfo = placeHolderApi.getTaxiInfo(LAT_LON, LAT_LON, LAT_LON, LAT_LON)
        return mapToDomain(taxiInfo)
    }

    private fun mapToDomain(taxiInfo: TaxiInfo): List<DomainTaxiInfo> {
        val result = ArrayList<DomainTaxiInfo>()
        taxiInfo.taxiList.forEach {
            val domainTaxiInfo =
                DomainTaxiInfo(it.id, it.coordinate.latitude, it.coordinate.longitude)
            result.add(domainTaxiInfo)
        }
        return result
    }
}