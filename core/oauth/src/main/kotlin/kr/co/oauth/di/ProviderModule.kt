package kr.co.oauth.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class)
internal class ProviderModule {
    @Provides
    fun provideActivityContext(@ActivityContext context: Context): Context {
        return context
    }
}