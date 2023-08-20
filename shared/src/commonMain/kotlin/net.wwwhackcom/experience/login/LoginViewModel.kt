package net.wwwhackcom.experience.login

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import net.wwwhackcom.Credential
import net.wwwhackcom.User
import net.wwwhackcom.network.Response
import net.wwwhackcom.repository.AuthRepository

/**
 * @author nickwang
 * Created 6/07/23
 */


class LoginViewModel constructor(
    private val repository: AuthRepository
): StateScreenModel<LoginUiState>(LoginUiState.Init) {

    fun login(credential: Credential) {
        coroutineScope.launch(CoroutineExceptionHandler { _, exception ->
            LoginUiState.Error(exception.toString())
        }) {
            mutableState.value = LoginUiState.Loading
            when (val response = repository.login(credential)) {
                is Response.Success -> mutableState.value = LoginUiState.Success(response.data)
                is Response.Error -> mutableState.value = LoginUiState.Error(response.error.message)
            }
        }
    }

    fun reset() {
        mutableState.value = LoginUiState.Init
    }
}

sealed class LoginUiState {
    object Init: LoginUiState()
    object Loading: LoginUiState()
    data class Success(val user: User): LoginUiState()
    data class Error(val error: String): LoginUiState()
}