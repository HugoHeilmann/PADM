package com.example.padm.net

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.util.concurrent.TimeUnit

private const val SERVER_BASE_URL = "http://127.0.0.1:8080"

private val httpClient by lazy {
    OkHttpClient.Builder()
        .connectTimeout(6, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()
}

data class JoinGameResult(
    val ok: Boolean,
    val playerNumber: Int? = null,
    val message: String
)

/**
 * Send POST /join-game with { gameId, playerId }
 */
suspend fun joinGame(gameId: String, playerId: String): JoinGameResult = withContext(Dispatchers.IO) {
    val url = "$SERVER_BASE_URL/join-game/$gameId/$playerId"

    val req = Request.Builder()
        .url(url)
        .post("".toRequestBody(null))
        .build()

    try {
        httpClient.newCall(req).execute().use { response ->
            val raw = response.body?.string().orEmpty()

            if (!response.isSuccessful) {
                val parsed = runCatching { JSONObject(raw) }.getOrNull()
                val msg = parsed?.optString("message")
                    ?.takeIf { it.isNotBlank() }
                    ?: parsed?.optString("error")
                    ?: "Erreur serveur (${response.code})"

                return@withContext JoinGameResult(false, null, msg)
            }

            val json = runCatching { JSONObject(raw) }.getOrNull()
            val playerNumber = json?.optInt("playerNumber", -1)?.takeIf { it >= 0 }
            val message = json?.optString("message").orEmpty().ifEmpty { "Joined." }

            JoinGameResult(true, playerNumber, message)
        }
    } catch (e: Exception) {
        JoinGameResult(false, null, "Network error : ${e.message}")
    }
}