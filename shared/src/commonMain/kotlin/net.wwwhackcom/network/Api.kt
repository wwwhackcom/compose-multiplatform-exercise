package net.wwwhackcom.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import net.wwwhackcom.Credential
import net.wwwhackcom.User

/**
 * @author nickwang
 * Created 5/07/23
 */

interface Api {
    suspend fun login(credential: Credential): Dto<User>
}


class ApiImpl : Api {

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = baseUrl
            }
            header(X_API_CONSUMER, API_KEY)
            contentType(ContentType.Application.Json)
        }
    }

    override suspend fun login(credential: Credential): Dto<User> {
        return httpClient.post("/auth/login") {
            setBody(credential)
        }.body()
    }

    companion object {

        private const val baseUrl = "api.wwwhackcom.net"
        private const val X_API_CONSUMER = "api-key"
        private const val API_KEY = "3e8288cf-e626-4557-81a8-517e10dec6f1"

    }

}