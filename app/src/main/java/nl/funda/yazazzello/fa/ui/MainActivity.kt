package nl.funda.yazazzello.fa.ui

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import nl.funda.yazazzello.fa.App
import nl.funda.yazazzello.fa.R
import nl.funda.yazazzello.fa.adapters.EstateAdapter
import nl.funda.yazazzello.fa.datamodels.EstateAgency
import nl.funda.yazazzello.fa.mvp.MainPresenter
import nl.funda.yazazzello.fa.mvp.MainView
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainView {

    @Inject
    lateinit var presenter: MainPresenter
    @Inject
    lateinit var picasso: Picasso

    lateinit var adapter: EstateAdapter

    var currentMode: MainPresenter.MODE = MainPresenter.MODE.SIMPLE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App[this].getComponent().inject(this)
        adapter = EstateAdapter()

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        presenter.attachView(this)
        presenter.loadEstateList()
        swipeToRefresh.setOnRefreshListener {
            presenter.loadEstateList(forceUpdate = true, mode = currentMode)
        }
    }

    override fun updateProgress(progress: Int) {
        progressBar.progress = progress
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_simple -> {
                currentMode = MainPresenter.MODE.SIMPLE
                presenter.loadEstateList(mode = currentMode)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_garden -> {
                currentMode = MainPresenter.MODE.GARDEN
                presenter.loadEstateList(mode = currentMode)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun flipProgress(isRefreshing: Boolean) {
        swipeToRefresh.isRefreshing = isRefreshing
        progressBar.visibility = if (isRefreshing) View.VISIBLE else View.GONE
    }

    override fun showError(msg: String) {
        Snackbar.make(container, msg, Snackbar.LENGTH_SHORT).show()
    }

    override fun showData(resultList: List<MutableMap.MutableEntry<EstateAgency, Int>>) {
        adapter.list.clear()
        adapter.list.addAll(resultList)
        adapter.notifyDataSetChanged()
    }

    override fun blockNavigation(shouldBlock: Boolean) {
        navigation.menu.findItem(R.id.navigation_simple).isEnabled = !shouldBlock
        navigation.menu.findItem(R.id.navigation_garden).isEnabled = !shouldBlock
    }
}
