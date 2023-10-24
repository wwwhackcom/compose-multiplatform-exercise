package net.wwwhackcom.experience.profile

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import net.wwwhackcom.User
import net.wwwhackcom.network.Response
import net.wwwhackcom.repository.AuthRepository

/**
 * @author nickwang
 * Created 18/08/23
 */

class ProfileViewModel constructor(
    private val repository: AuthRepository,
): StateScreenModel<ProfileUiState>(ProfileUiState.Loading) {
    fun getUserProfile(id: String) {
        screenModelScope.launch(CoroutineExceptionHandler { _, exception ->
            ProfileUiState.Error(exception.toString())
        }) {
            when (val response = repository.userInfo(id)) {
                is Response.Success -> mutableState.value = ProfileUiState.Success(response.data)
                is Response.Error -> mutableState.value = ProfileUiState.Error(response.error.message)
            }
        }
    }
}

sealed class ProfileUiState {
    object Loading: ProfileUiState()
    data class Success(val user: User): ProfileUiState()
    data class Error(val error: String): ProfileUiState()
}