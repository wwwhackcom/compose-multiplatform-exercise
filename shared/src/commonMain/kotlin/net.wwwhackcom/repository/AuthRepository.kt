package net.wwwhackcom.repository

import net.wwwhackcom.Credential
import net.wwwhackcom.User
import net.wwwhackcom.network.Api
import net.wwwhackcom.network.Response
import net.wwwhackcom.network.ResultError

/**
 * @author nickwang
 * Created 5/07/23
 */

interface AuthRepository {
    @Throws(Exception::class)
    suspend fun login(credential: Credential): Response<User, ResultError>

    @Throws(Exception::class)
    suspend fun register(credential: Credential): Response<User, ResultError>

    @Throws(Exception::class)
    suspend fun userInfo(id: String): Response<User, ResultError>
}

class AuthRepositoryImpl constructor(
    private val api: Api
) : AuthRepository {

    @Throws(Exception::class)
    override suspend fun login(credential: Credential): Response<User, ResultError> {
        val response = api.login(credential)
        return if (response.payload != null) Response.Success(response.payload) else Response.Error(
            response.result
        )
    }

    @Throws(Exception::class)
    override suspend fun register(credential: Credential): Response<User, ResultError> {
        val response = api.register(credential)
        return if (response.payload != null) Response.Success(response.payload) else Response.Error(
            response.result
        )
    }

    @Throws(Exception::class)
    override suspend fun userInfo(id: String): Response<User, ResultError> {
        val response = api.userInfo(id)
        return if (response.payload != null) Response.Success(response.payload) else Response.Error(
            response.result
        )
    }
}