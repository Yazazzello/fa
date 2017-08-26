package nl.funda.yazazzello.fa

import android.app.Application
import android.content.Context
import com.squareup.leakcanary.LeakCanary
import nl.funda.yazazzello.fa.injection.component.ApplicationComponent
import nl.funda.yazazzello.fa.injection.component.DaggerApplicationComponent
import nl.funda.yazazzello.fa.injection.modules.ApplicationModule
import rx.plugins.RxJavaHooks
import timber.log.Timber

/**
 * Created by yazazzello on 2/27/17.
 */
class App : Application() {

    private lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                // This process is dedicated to LeakCanary for heap analysis.
                // You should not init your app in this process.
                return
            }
            Timber.plant(Timber.DebugTree())
            LeakCanary.install(this)
            RxJavaHooks.setOnError { Timber.e(it, "rx hook error") }
        }
        initComponent()
    }


    fun getComponent(): ApplicationComponent {
        return applicationComponent
    }

    private fun initComponent() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    fun setComponent(component: ApplicationComponent) {
        this.applicationComponent = component
    }

    companion object {

        operator fun get(context: Context): App {
            return context.applicationContext as App
        }
    }
}