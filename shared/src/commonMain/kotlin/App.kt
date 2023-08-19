import androidx.compose.runtime.Composable
import net.wwwhackcom.experience.login.LoginScreen

@Composable
internal fun App() {
    LoginScreen()
}

expect fun getPlatformName(): String