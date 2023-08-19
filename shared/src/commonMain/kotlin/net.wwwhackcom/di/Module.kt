package net.wwwhackcom.di

import net.wwwhackcom.network.Api
import net.wwwhackcom.network.ApiImpl
import net.wwwhackcom.repository.AuthRepository
import net.wwwhackcom.repository.AuthRepositoryImpl
import net.wwwhackcom.experience.login.LoginViewModel
import org.koin.dsl.module

/**
 * @author nickwang
 * Created 6/07/23
 */

val apiModule = module {
    single { ApiImpl() as Api }
}

val repositoryModule = module {
    single { AuthRepositoryImpl(get()) as AuthRepository }
}

val viewModelModule = module {
    single { LoginViewModel(get()) }
}

val appModule = listOf(
    apiModule,
    repositoryModule,
    viewModelModule,
)