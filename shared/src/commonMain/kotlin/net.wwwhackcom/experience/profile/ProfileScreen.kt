package net.wwwhackcom.experience.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import net.wwwhackcom.experience.base.ErrorScreen
import net.wwwhackcom.experience.base.LoadingScreen
import net.wwwhackcom.experience.base.TopBar
import net.wwwhackcom.ext.getScreenModel

internal class ProfileRoute(private val id: String) : Screen {

    @Composable
    override fun Content() {

        val viewModel = getScreenModel<ProfileViewModel>()
        val state by viewModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(Unit) {
            viewModel.getUserProfile(id)
        }
        when (state) {
            is ProfileUiState.Loading -> LoadingScreen()
            is ProfileUiState.Success -> ProfileScreen((state as ProfileUiState.Success).user.toString()) {
                navigator.pop()
            }

            is ProfileUiState.Error -> ErrorScreen((state as ProfileUiState.Error).error)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileScreen(
    userInfo: String,
    onBackPressed: () -> Unit
) {
    Scaffold(
        topBar = {
            TopBar(title = "User Info", onBackPressed = onBackPressed)
        }) {
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                textAlign = TextAlign.Center,
                text = userInfo
            )
        }
    }
}