package nl.funda.yazazzello.fa.injection.api

import com.google.gson.annotations.SerializedName

/**
 * Created by yazazzello on 2/27/17.
 */
data class FundaResponse(
        @SerializedName("TotaalAantalObjecten") var totalItems: Int,
        @SerializedName("Paging") var page: Paging,
        @SerializedName("Objects") var items: List<EstateItem>
)

data class Paging(
        @SerializedName("AantalPaginas") var totalPages: Int,
        @SerializedName("HuidigePagina") var currentPage: Int
)

data class EstateItem(
        @SerializedName("Adres") var address: String,
        @SerializedName("FotoMedium") var photoUrl: String,
        @SerializedName("MakelaarId") var agencyId: Long,
        @SerializedName("MakelaarNaam") var agencyName: String?,
        @SerializedName("Woonoppervlakte") var areaSquareMeters: Int
)