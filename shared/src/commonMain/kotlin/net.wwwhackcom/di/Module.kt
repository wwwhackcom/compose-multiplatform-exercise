package net.wwwhackcom.di

import net.wwwhackcom.network.Api
import net.wwwhackcom.network.ApiImpl
import net.wwwhackcom.repository.SpaceRepository
import net.wwwhackcom.repository.SpaceRepositoryImpl
import net.wwwhackcom.viewmodel.ViewModel

/**
 * @author nickwang
 * Created 6/07/23
 */

object AppModule {

    private fun provideApi(): Api {
        return ApiImpl()
    }

    private fun provideRepository(api: Api): SpaceRepository {
        return SpaceRepositoryImpl(api)
    }

    val viewModule = ViewModel(
        repository = provideRepository(
            api = provideApi()
        )
    )
}