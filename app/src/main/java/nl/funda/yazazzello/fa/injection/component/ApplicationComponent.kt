package nl.funda.yazazzello.fa.injection.component


import com.squareup.picasso.Picasso
import dagger.Component
import nl.funda.yazazzello.fa.App
import nl.funda.yazazzello.fa.injection.api.ApiService
import nl.funda.yazazzello.fa.injection.modules.ApplicationModule
import nl.funda.yazazzello.fa.injection.modules.NetworkModule
import nl.funda.yazazzello.fa.ui.MainActivity

import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class, NetworkModule::class))
interface ApplicationComponent {

    fun app(): App

    fun apiService(): ApiService

    fun picasso(): Picasso

    fun inject(activity: MainActivity)

}
