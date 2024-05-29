package kr.co.wdtt.core.local.di

import android.content.Context
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kr.co.wdtt.nbdream.BuildConfig
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalServiceModule {
    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext context: Context) =
        PreferenceDataStoreFactory.create (
            corruptionHandler = ReplaceFileCorruptionHandler {
                it.printStackTrace()
                emptyPreferences()
            },
            produceFile = { context.preferencesDataStoreFile(BuildConfig.DATASTORE_NAME) }
        )
}