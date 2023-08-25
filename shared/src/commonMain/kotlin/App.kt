import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import net.wwwhackcom.experience.login.LoginRoute

@Composable
internal fun App() {
    MaterialTheme {
        Navigator(LoginRoute())
    }
}

expect fun getPlatformName(): String