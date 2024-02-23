import java.net.HttpURLConnection
import java.net.URL
import kotlinx.coroutines.*

fun checkWebsite(url: String): Boolean {
    return try {
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.requestMethod = "HEAD"
        connection.connectTimeout = 5000
        connection.readTimeout = 5000
        connection.responseCode == HttpURLConnection.HTTP_OK
    } catch (e: Exception) {
        false
    }
}

val websites = listOf(
    "https://www.google.com",
    "https://www.facebook.com",
    "https://www.github.com",
    "https://www.twitter.com",
    "https://www.instagram.com",
    "https://vkusnoitochka.ru",
    "https://www.samsung.com",
    "https://www.nike.com",
    "https://www.porsche.ru",
    "https://www.apple.com/"
)

fun main() = runBlocking {
    val tasks = websites.map { url ->
        async {
            val result = checkWebsite(url)
            Pair(url, result)
        }
    }
    tasks.forEach { task ->
        val (url, result) = task.await()
        val status = if (result) "доступен" else "недоступен"
        println("Сайт $url $status")
    }
}