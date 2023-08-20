package net.wwwhackcom.experience.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import net.wwwhackcom.Credential
import net.wwwhackcom.experience.base.ErrorScreen
import net.wwwhackcom.experience.base.LoadingScreen
import net.wwwhackcom.experience.login.LoginUiState.Error
import net.wwwhackcom.experience.login.LoginUiState.Init
import net.wwwhackcom.experience.login.LoginUiState.Loading
import net.wwwhackcom.experience.login.LoginUiState.Success
import net.wwwhackcom.experience.registration.RegistrationRoute
import net.wwwhackcom.experience.userInfo.UserInfoRoute
import net.wwwhackcom.ext.getScreenModel
import net.wwwhackcom.ext.validateEmail
import net.wwwhackcom.ext.validatePassword

/**
 * @author nickwang
 * Created 6/07/23
 */

internal class LoginRoute : Screen {

    @Composable
    override fun Content() {

        val viewModel = getScreenModel<LoginViewModel>()
        val state by viewModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        when (state) {
            is Init -> LoginScreen(
                onLoginClick = {
                    viewModel.login(it)
                },
                onRegisterClick = {
                    navigator.push(RegistrationRoute())
                })

            is Loading -> LoadingScreen()
            is Success -> {
                viewModel.reset() //TODO: AppState
                navigator.push(UserInfoRoute((state as Success).user.userId))
            }

            is Error -> ErrorScreen((state as Error).error) {
                viewModel.reset()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginScreen(
    onLoginClick: (Credential) -> Unit,
    onRegisterClick: () -> Unit,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val validateEmail = email.validateEmail()
    val validatePassword = password.validatePassword()

    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome to Compose Multiplatform World",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(vertical = 32.dp)
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            isError = validateEmail != null,
            supportingText = {
                if (validateEmail != null) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = validateEmail,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            isError = validatePassword != null,
            supportingText = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = validatePassword ?: "",
                    color = MaterialTheme.colorScheme.error
                )
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onLoginClick(Credential(email, password)) }
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                textAlign = TextAlign.Center,
                text = "Log in"
            )
        }

        ClickableText(
            text = AnnotatedString("Sign up"),
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            onClick = { onRegisterClick() },
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily.Default,
                textDecoration = TextDecoration.Underline,
                textAlign = TextAlign.Center
            )
        )
    }
}