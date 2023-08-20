package net.wwwhackcom.di

import net.wwwhackcom.network.Api
import net.wwwhackcom.network.ApiImpl
import net.wwwhackcom.repository.AuthRepository
import net.wwwhackcom.repository.AuthRepositoryImpl
import net.wwwhackcom.experience.login.LoginViewModel
import net.wwwhackcom.experience.registration.RegistrationViewModel
import net.wwwhackcom.experience.userInfo.UserInfoViewModel
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
    factory { LoginViewModel(get()) }
    factory { RegistrationViewModel(get()) }
    factory { UserInfoViewModel(get()) }
}

val appModule = listOf(
    apiModule,
    repositoryModule,
    viewModelModule,
)