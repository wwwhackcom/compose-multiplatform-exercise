package net.wwwhackcom.experience.registration

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import net.wwwhackcom.Credential
import net.wwwhackcom.User
import net.wwwhackcom.experience.login.LoginUiState
import net.wwwhackcom.network.Response
import net.wwwhackcom.repository.AuthRepository

/**
 * @author nickwang
 * Created 18/08/23
 */

class RegistrationViewModel constructor(
    private val repository: AuthRepository
): StateScreenModel<RegistrationUiState>(RegistrationUiState.Init) {

    fun register(credential: Credential) {
        screenModelScope.launch(CoroutineExceptionHandler { _, exception ->
            LoginUiState.Error(exception.toString())
        }) {
            mutableState.value = RegistrationUiState.Loading
            when (val response = repository.register(credential)) {
                is Response.Success -> mutableState.value =
                    RegistrationUiState.Success(response.data)
                is Response.Error -> mutableState.value =
                    RegistrationUiState.Error(response.error.message)
            }
        }
    }

    fun reset() {
        mutableState.value = RegistrationUiState.Init
    }
}

sealed class RegistrationUiState {
    object Init: RegistrationUiState()
    object Loading: RegistrationUiState()
    data class Success(val user: User): RegistrationUiState()
    data class Error(val error: String): RegistrationUiState()
}