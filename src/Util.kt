import io.github.cdimascio.dotenv.dotenv
import java.net.HttpURLConnection
import java.net.HttpURLConnection.HTTP_OK
import java.net.URL
import java.nio.file.Path
import kotlin.io.path.exists
import kotlin.io.path.readText
import kotlin.io.path.writeText

val env by lazy {
    dotenv {
        ignoreIfMissing = true
    }
}

fun readInput(day: Int): String {
    val file = Path.of("inputs", day.toString().padStart(2, '0') + ".txt")
    val token = env.get("TOKEN")
    if (file.exists() || token == null) {
        return file.readText()
    }

    val conn = URL("https://adventofcode.com/2023/day/$day/input").openConnection() as HttpURLConnection
    conn.setRequestProperty("Cookie", "session=$token")
    conn.setRequestProperty("User-Agent", "HTTPUrlConnection")
    check(conn.responseCode == HTTP_OK) { conn.responseMessage }

    val input = conn.inputStream.reader().readText().dropLast(1)
    file.writeText(input)
    return input
}