package uk.sandbox.yaz.base.features


import nl.funda.yazazzello.fa.mvp.BaseMvpView
import rx.Subscription
import rx.subscriptions.CompositeSubscription

abstract class BasePresenter<T : BaseMvpView> {

    private var compositeSubscription: CompositeSubscription = CompositeSubscription()

    var mvpView: T? = null
        private set

    fun attachView(mvpView: T) {
        this.mvpView = mvpView
    }

    fun detachView() {
        compositeSubscription.clear()
        this.mvpView = null
    }

    val isViewAttached: Boolean
        get() = mvpView != null

    fun addSubscription(subscription: Subscription) {
        compositeSubscription.add(subscription)
    }

    fun checkViewAttached() {
        if (!isViewAttached) throw MvpViewNotAttachedException()
    }

    class MvpViewNotAttachedException : RuntimeException("Please call Presenter.attachView(MvpView) before" + " requesting data to the Presenter")

}
