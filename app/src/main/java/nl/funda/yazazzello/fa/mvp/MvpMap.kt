package nl.funda.yazazzello.fa.mvp

import nl.funda.yazazzello.fa.datamodels.EstateAgency

/**
 * Created by yazazzello on 2/27/17.
 */
interface BaseMvpView //marker

interface MainView : BaseMvpView {

    fun flipProgress(isRefreshing: Boolean)

    fun showError(msg: String)

    fun showData(resultList: List<MutableMap.MutableEntry<EstateAgency, Int>>)

    fun updateProgress(progress: Int)

    fun blockNavigation(shouldBlock: Boolean)
}