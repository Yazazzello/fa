package nl.funda.yazazzello.fa.injection.api

import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface ApiService {

    @GET(value = TOKEN + "pagesize=25&type=koop")
    fun searchEstates(@Query("page") page: Int, @Query("zo") searchKw: String): Observable<FundaResponse>

    companion object {
        val ENDPOINT = "http://partnerapi.funda.nl/feeds/Aanbod.svc/json/"
        const val TOKEN = "005e7c1d6f6c4f9bacac16760286e3cd/?"
    }
}
