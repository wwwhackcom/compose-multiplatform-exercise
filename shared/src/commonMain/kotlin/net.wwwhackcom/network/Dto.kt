package net.wwwhackcom.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @author nickwang
 * Created 11/07/23
 */

@Serializable
data class Dto<T>(
    @SerialName("data")
    val payload: T? = null,

    @SerialName("ret")
    val result: ResultError,
)

@Serializable
data class ResultError(
    val retCode: Int,
    val message: String,
)

sealed class Response<out Data, out Error> {

    data class Success<out Data>(val data: Data) : Response<Data, Nothing>()

    data class Error<out Error>(val error: Error) : Response<Nothing, Error>()

}