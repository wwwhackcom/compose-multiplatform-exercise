package net.wwwhackcom

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @author nickwang
 * Created 5/07/23
 */

@Serializable
data class User(
    @SerialName("userId")
    val userId: String,
    @SerialName("username")
    val username: String,
    @SerialName("firstName")
    val firstName: String? = null,
    @SerialName("lastName")
    val lastName: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("avatar")
    val avatar: String? = null,
)

@Serializable
data class Credential(
    @SerialName("username")
    val username: String,
    @SerialName("password")
    val password: String,
)