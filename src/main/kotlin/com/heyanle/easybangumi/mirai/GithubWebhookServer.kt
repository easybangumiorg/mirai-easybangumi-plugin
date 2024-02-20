package org.example.mirai.plugin.com.heyanle.easybangumi.mirai

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.*
import kotlinx.serialization.json.JsonObject
import org.apache.commons.codec.binary.Hex
import org.json.JSONObject
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object GithubWebhookServer {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val sha256Hmac = Mac.getInstance("HmacSHA256")
    private val secretKey = SecretKeySpec(Configs.githubWebHookSecret.toByteArray(), "HmacSHA256")

    init {
        sha256Hmac.init(secretKey)
    }

    private val engine: NettyApplicationEngine by lazy {
        embeddedServer(Netty, port = Configs.githubWebHookPort) {
            routing {
                post("/") {
                    val payload = call.receiveText()
                    val signature = call.request.header("X-Hub-Signature-256") ?: ""
                    dispatchWebhook(signature, payload)
                    call.respondText("Ok")
                }
            }
        }
    }

    private fun dispatchWebhook(signature: String, payload: String) {
        scope.launch {
            val sign = Hex.encodeHexString(sha256Hmac.doFinal(payload.toByteArray()))
            if (sign == signature) {
                handleWebhook(payload)
            }

        }
    }

    private fun handleWebhook(payload: String) {
        //JsonObject(payload)
        val o = JSONObject(payload)
        val action = o.getString("action") ?: ""
        if (action != "published") {
            return
        }
        val release = o.getString("release") ?: ""
    }


    fun start() {
        engine.start(wait = true)

    }

    fun stop() {
        engine.stop()
    }

}