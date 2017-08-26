package nl.funda.yazazzello.fa

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import nl.funda.yazazzello.fa.datamodels.EstateAgency
import nl.funda.yazazzello.fa.injection.api.FundaResponse
import nl.funda.yazazzello.fa.utils.TestTools
import org.junit.Before
import org.junit.Test
import java.util.*
import kotlin.comparisons.compareBy
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue


/**
 * Created by yazazzello on 2/27/17.
 */
class ParsingTest {

    var responseStr: String? = null
    lateinit var gson: Gson
    @Before
    fun setUp() {
        responseStr = TestTools.readFile("api_response.json")
        gson = GsonBuilder().create()
    }

    @Test
    @Throws(Exception::class)
    fun shouldNotBeNulls() {
        assertNotNull(responseStr)
    }

    @Test
    @Throws(Exception::class)
    fun shouldParseAllFields() {
        val response = gson.fromJson<FundaResponse>(responseStr, FundaResponse::class.java)
        assertNotNull(response)
        assertNotNull(response.items)
        assertNotNull(response.page)
        assertNotNull(response.totalItems)
        assertNotNull(response.page.currentPage)
        assertNotNull(response.page.totalPages)
    }

    @Test
    @Throws(Exception::class)
    fun shouldHaveCorrectSize() {
        val response = gson.fromJson<FundaResponse>(responseStr, FundaResponse::class.java)
        assertEquals(25, response.items.size)
    }

    @Test
    @Throws(Exception::class)
    fun shouldParseCorrect() {
        val response = gson.fromJson<FundaResponse>(responseStr, FundaResponse::class.java)
        val (address, photoUrl, agencyId, agencyName, areaSquareMeters) = response.items[0]
        //check first element
        assertEquals("Herengracht 518", address)
        assertEquals("http://cloud.funda.nl/valentina_media/075/290/929_middel.jpg", photoUrl)
        assertEquals(24325, agencyId)
        assertEquals("PC22 makelaars taxateurs o.z.", agencyName)
        assertEquals(1155, areaSquareMeters)
    }

    @Test
    @Throws(Exception::class)
    fun shouldCount() {
        val hashMap: HashMap<EstateAgency, Int> = HashMap()
        val agency1 = EstateAgency(1L, "ag1")
        val agency2 = EstateAgency(2L, "ag2")

        hashMap.put(agency1, hashMap.getOrPut(agency1, { 0 }).inc())
        hashMap.put(agency1, hashMap.getOrPut(agency2, { 0 }).inc())
        hashMap.put(agency1, hashMap.getOrPut(agency1, { 0 }).inc())

        val mutableList = hashMap.entries.toMutableList().sortedWith(compareBy { it.value })
        val reversed = mutableList.reversed()
        assertTrue(reversed[0].value==2)
    }
}