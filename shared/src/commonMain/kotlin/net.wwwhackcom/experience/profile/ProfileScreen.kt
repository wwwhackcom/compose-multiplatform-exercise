package net.wwwhackcom.experience.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import net.wwwhackcom.User
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
            is ProfileUiState.Success -> ProfileScreen((state as ProfileUiState.Success).user) {
                navigator.pop()
            }

            is ProfileUiState.Error -> ErrorScreen((state as ProfileUiState.Error).error)
        }
    }
}

@Composable
private fun ProfileScreen(
    userProfile: User,
    onBackPressed: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            TopBar(title = "User Profile", onBackPressed = onBackPressed)
        }) {

        ProfileCard(
            modifier = Modifier.fillMaxSize().padding(top = 50.dp),
            userProfile = userProfile,
            scope = scope,
            snackbarHostState = snackbarHostState
        )
    }
}

@Composable
fun ProfileCard(
    modifier: Modifier = Modifier,
    userProfile: User,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState
) {
    Surface {
        Card(
            modifier = modifier
                .width(200.dp)
                .height(390.dp)
                .padding(12.dp),
            shape = RoundedCornerShape(corner = CornerSize(15.dp)),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        )
        {
            Column(
                modifier = Modifier.height(300.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                userProfile.avatar?.let {
                    Avatar(
                        imageUrl = it,
                        scope = scope,
                        snackbarHostState = snackbarHostState
                    )
                    Divider()
                }

                Info(userProfile)
            }
        }
    }

}

@Composable
private fun Avatar(
    modifier: Modifier = Modifier,
    imageUrl: String,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState
) {
    Surface(
        modifier = modifier
            .size(154.dp)
            .padding(5.dp),
        shape = CircleShape,
        border = BorderStroke(0.5.dp, Color.LightGray),
        tonalElevation = 4.dp,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
    ) {
        val painterResource: Resource<Painter> = asyncPainterResource(
            imageUrl,
            filterQuality = FilterQuality.High,
        )

        KamelImage(
            resource = painterResource,
            contentDescription = null,
            modifier = Modifier
                .aspectRatio(1F)
                .shadow(elevation = 8.dp, RoundedCornerShape(16.dp))
                .background(Color.White, RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop,
            onLoading = { CircularProgressIndicator(it) },
            onFailure = { exception: Throwable ->
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = exception.message.toString(),
                        actionLabel = "Hide",
                    )
                }
            },
        )
    }
}

@Composable
private fun Info(userProfile: User) {
    Column(
        modifier = Modifier
            .padding(5.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            color = Color.Blue,
            fontSize = 24.sp,
            style = MaterialTheme.typography.headlineMedium,
            text = "${userProfile.firstName} ${userProfile.lastName}"
        )

        Text(
            text = userProfile.username,
            modifier = Modifier.padding(3.dp),
            style = MaterialTheme.typography.titleMedium
        )

        userProfile.description?.let {
            Text(
                text = it,
                modifier = Modifier.padding(3.dp)
            )
        }

    }
}