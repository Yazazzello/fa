package nl.funda.yazazzello.fa.injection.modules

import android.graphics.Bitmap
import com.squareup.picasso.LruCache
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import nl.funda.yazazzello.fa.App
import javax.inject.Singleton

@Module
class ApplicationModule(private val app: App) {

    @Provides
    internal fun provideApp(): App {
        return app
    }

    @Provides
    @Singleton
    internal fun providePicasso(app: App): Picasso {
        val memoryCache = LruCache(PICASSO_DISK_CACHE_SIZE)
        return Picasso.Builder(app)
                .defaultBitmapConfig(Bitmap.Config.RGB_565)
                .memoryCache(memoryCache).build()
    }

    companion object {
        private val PICASSO_DISK_CACHE_SIZE = 1024 * 1024 * 20//10 MB
    }

}
