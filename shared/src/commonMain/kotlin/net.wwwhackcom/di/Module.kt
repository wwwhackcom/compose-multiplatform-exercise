package net.wwwhackcom.di

import net.wwwhackcom.network.Api
import net.wwwhackcom.network.ApiImpl
import net.wwwhackcom.repository.AuthRepository
import net.wwwhackcom.repository.AuthRepositoryImpl
import net.wwwhackcom.viewmodel.ViewModel

/**
 * @author nickwang
 * Created 6/07/23
 */

object AppModule {

    private fun provideApi(): Api {
        return ApiImpl()
    }

    private fun provideRepository(api: Api): AuthRepository {
        return AuthRepositoryImpl(api)
    }

    val viewModule = ViewModel(
        repository = provideRepository(
            api = provideApi()
        )
    )
}