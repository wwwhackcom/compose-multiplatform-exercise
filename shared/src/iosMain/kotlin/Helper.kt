import net.wwwhackcom.di.appModule
import org.koin.core.context.startKoin

/**
 * @author nickwang
 * Created 21/07/23
 */

fun initKoin() {
    startKoin {
        modules(appModule)
    }
}