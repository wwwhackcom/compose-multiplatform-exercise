package net.wwwhackcom.network

import kotlinx.coroutines.test.runTest
import net.wwwhackcom.Credential
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ApiImplTest {

    private lateinit var api: Api

    @BeforeTest
    fun setUp() {
        api = ApiImpl()
    }

    @Test
    fun testLogin() {
        runTest {
            val response = api.login(Companion.CredentialIncorrect)
            println("response = ${response.result.message}/${(response.payload).toString()}")
            assertEquals(102, response.result.retCode)
        }
    }

    companion object {
        private val CredentialIncorrect = Credential("username", "password")
    }
}