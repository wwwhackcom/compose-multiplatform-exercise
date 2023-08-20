package net.wwwhackcom.experience.userInfo

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import net.wwwhackcom.User
import net.wwwhackcom.network.Response
import net.wwwhackcom.repository.AuthRepository

/**
 * @author nickwang
 * Created 18/08/23
 */

class UserInfoViewModel constructor(
    private val repository: AuthRepository,
): StateScreenModel<UserInfoUiState>(UserInfoUiState.Loading) {
    fun userInfo(id: String) {
        coroutineScope.launch(CoroutineExceptionHandler { _, exception ->
            UserInfoUiState.Error(exception.toString())
        }) {
            when (val response = repository.userInfo(id)) {
                is Response.Success -> mutableState.value = UserInfoUiState.Success(response.data)
                is Response.Error -> mutableState.value = UserInfoUiState.Error(response.error.message)
            }
        }
    }
}

sealed class UserInfoUiState {
    object Loading: UserInfoUiState()
    data class Success(val user: User): UserInfoUiState()
    data class Error(val error: String): UserInfoUiState()
}