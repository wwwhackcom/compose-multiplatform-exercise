package net.wwwhackcom.viewmodel

import net.wwwhackcom.Credential
import net.wwwhackcom.network.Response
import net.wwwhackcom.repository.AuthRepository

/**
 * @author nickwang
 * Created 6/07/23
 */


class ViewModel constructor(
    private val repository: AuthRepository
) {
    suspend fun login(credential: Credential): String {
        return when (val response = repository.login(credential)) {
            is Response.Success -> response.data.toString()
            is Response.Error -> response.error.message
        }
    }
}