package net.wwwhackcom.experience.registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import net.wwwhackcom.Credential
import net.wwwhackcom.experience.base.ErrorScreen
import net.wwwhackcom.experience.base.LoadingScreen
import net.wwwhackcom.experience.base.TopBar
import net.wwwhackcom.experience.registration.RegistrationUiState.Error
import net.wwwhackcom.experience.registration.RegistrationUiState.Init
import net.wwwhackcom.experience.registration.RegistrationUiState.Loading
import net.wwwhackcom.experience.registration.RegistrationUiState.Success
import net.wwwhackcom.experience.userInfo.UserInfoRoute
import net.wwwhackcom.ext.getScreenModel
import net.wwwhackcom.ext.validateEmail
import net.wwwhackcom.ext.validatePassword

/**
 * @author nickwang
 * Created 18/08/23
 */
internal class RegistrationRoute : Screen {

    @Composable
    override fun Content() {

        val viewModel = getScreenModel<RegistrationViewModel>()
        val state by viewModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        when (state) {
            is Init -> RegistrationScreen(viewModel) {
                navigator.pop()
            }

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
private fun RegistrationScreen(
    viewModel: RegistrationViewModel,
    onBackPressed: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val validateEmail = email.validateEmail()
    val validatePassword = password.validatePassword()

    Scaffold(
        topBar = {
            TopBar(title = "Register New User", onBackPressed = onBackPressed)
        }) {
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center
        ) {
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
                onClick = {
                    viewModel.register(Credential(email, password))
                }
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    textAlign = TextAlign.Center,
                    text = "Sign up"
                )
            }
        }
    }
}