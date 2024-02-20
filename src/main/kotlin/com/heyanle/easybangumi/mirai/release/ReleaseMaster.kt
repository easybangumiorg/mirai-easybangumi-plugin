package org.example.mirai.plugin.com.heyanle.easybangumi.mirai.release

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.asCoroutineDispatcher
import org.json.JSONObject
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicReference

object ReleaseMaster {

    private val SINGLE = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
    val scope = CoroutineScope(SupervisorJob() + SINGLE)

    private val currentVersion = AtomicReference<String>("")


    fun startWithVersionName(versionName: String) {
        if (currentVersion.compareAndSet("", versionName)) {

        }
    }

    fun startWithWebhookPayload(webhookPayload: String) {
        val o = JSONObject(webhookPayload)
        val release = o.getJSONObject("release")
        val isDraft = o.getBoolean("draft")
        val isPrerelease = o.getBoolean("prerelease")
        val versionName = o.getString("tag_name") ?: ""
        val action = o.getString("action")
        if (action == "edited") {
            return
        }
        if (!isDraft && !isPrerelease && versionName.isNotEmpty()) {

            if (currentVersion.compareAndSet("", versionName)) {

            }

        }

    }




}