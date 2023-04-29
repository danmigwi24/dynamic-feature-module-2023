package com.daniel.dkm.baseapp.di

import android.app.Application
import android.content.Context
import com.daniel.dkm.baseapp.TopApp
import com.daniel.dkm.common.data.db.CommonDataBase
import com.daniel.dkm.common.data.network.CommonApiService
import com.daniel.dkm.common.data.network.NetworkInterceptor
import com.daniel.dkm.common.data.network.RemoteDataSource
import com.daniel.dkm.common.data.repository.SampleDataRepo
import com.daniel.dkm.common.data.repository.SampleRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideContext(app: TopApp): Context {
        return app
    }

    @Singleton
    @Provides
    fun provideSampleRepository(context: Context): SampleRepository {
        return SampleDataRepo(context)
    }

    @Singleton
    @Provides
    fun provideApp(context: Context): Application {
        return context.applicationContext as Application
    }

    @Singleton
    @Provides
    fun provideNetworkInterceptor(context: Context): NetworkInterceptor {
        return NetworkInterceptor(context)
    }

    @Singleton
    @Provides
    fun provideRemoteDataSource(networkInterceptor: NetworkInterceptor): RemoteDataSource {
        return RemoteDataSource(networkInterceptor)
    }

    //
    @Singleton
    @Provides
    fun provideApiClientService(remoteDataSource: RemoteDataSource): CommonApiService {
        return remoteDataSource.buildApi(CommonApiService::class.java)
    }

    //
    @Singleton
    @Provides
    fun provideFFAppDatabase(context: Context): CommonDataBase {
        return CommonDataBase(context)
    }
}
