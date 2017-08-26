package nl.funda.yazazzello.fa.mvp

import nl.funda.yazazzello.fa.datamodels.EstateAgency
import nl.funda.yazazzello.fa.injection.api.FundaResponse
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import timber.log.Timber
import uk.sandbox.yaz.base.data.DataManager
import uk.sandbox.yaz.base.features.BasePresenter
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.comparisons.compareBy

/**
 * Created by yazazzello on 2/27/17.
 */

class MainPresenter @Inject constructor(var dataManager: DataManager) : BasePresenter<MainView>() {
    enum class MODE { SIMPLE, GARDEN }

    private val simpleEstates = "/amsterdam/"

    private val gardenEstates = "/amsterdam/tuin/"

    private val simpleEstateList: MutableList<MutableMap.MutableEntry<EstateAgency, Int>> = ArrayList()

    private val gardenEstateList: MutableList<MutableMap.MutableEntry<EstateAgency, Int>> = ArrayList()

    private var currentList: MutableList<MutableMap.MutableEntry<EstateAgency, Int>> = gardenEstateList

    private val simpleEstateMap: HashMap<EstateAgency, Int> = HashMap()

    private val gardenEstateMap: HashMap<EstateAgency, Int> = HashMap()

    private val ITEMS_TO_DISPLAY = 10

    fun loadEstateList(forceUpdate: Boolean = false, mode: MODE = MODE.SIMPLE) {
        checkViewAttached()

        var searchQuery = ""
        when (mode) {
            MODE.SIMPLE -> {
                currentList = simpleEstateList
                searchQuery = simpleEstates
            }
            MODE.GARDEN -> {
                currentList = gardenEstateList
                searchQuery = gardenEstates
            }
        }

        if (currentList.isNotEmpty() && !forceUpdate) {
            mvpView?.showData(currentList)
            return
        }
        simpleEstateMap.clear()
        gardenEstateMap.clear()

        mvpView?.flipProgress(true)
        mvpView?.blockNavigation(true)
        addSubscription(dataManager.getEstateResponse(searchQuery)
                .flatMap {
                    Observable.range(1, it.page.totalPages).delay(200L, TimeUnit.MILLISECONDS, Schedulers.immediate())
                            .flatMap { dataManager.getEstateResponse(searchQuery, it) }
                }
                .subscribeOn(Schedulers.io())
                .onBackpressureBuffer()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<FundaResponse>() {
                    override fun onError(e: Throwable?) {
                        mvpView?.showError("Error")
                        Timber.e(e)
                    }

                    override fun onNext(response: FundaResponse?) {
                        if (response != null) {
                            response.items.forEach {
                                it.agencyName?.apply {
                                    val agency = EstateAgency(it.agencyId, it.agencyName!!)
                                    simpleEstateMap.put(agency, simpleEstateMap.getOrPut(agency, { 0 }).inc())
                                }
                            }
                            val mutableList = simpleEstateMap.entries.toMutableList().sortedWith(compareBy { it.value })
                            val resultList = mutableList.reversed().subList(0, ITEMS_TO_DISPLAY)
                            currentList.clear()
                            currentList.addAll(resultList)
                            mvpView?.showData(resultList)
                            val progress = (response.page.currentPage.toFloat() / response.page.totalPages.toFloat()) * 100
                            mvpView?.updateProgress(progress.toInt())
                        } else {
                            mvpView?.showError("response is empty")
                        }
                    }

                    override fun onCompleted() {
                        mvpView?.flipProgress(false)
                        mvpView?.blockNavigation(false)
                    }

                }))
    }
}