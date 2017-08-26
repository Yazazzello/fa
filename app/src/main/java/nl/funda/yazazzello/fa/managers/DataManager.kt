package uk.sandbox.yaz.base.data

import nl.funda.yazazzello.fa.injection.api.ApiService
import nl.funda.yazazzello.fa.injection.api.FundaResponse
import rx.Observable
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class DataManager @Inject constructor(private val apiService: ApiService) {
    fun getEstateResponse(param: String, page: Int = 1): Observable<FundaResponse> {
        return apiService.searchEstates(page, param)
    }
}
