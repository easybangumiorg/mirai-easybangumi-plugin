package com.heyanle.easybangumi.mirai

import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.BotInvitedJoinGroupRequestEvent

class PluginMain: KotlinPlugin(
    JvmPluginDescription(
        id = "com.heyanle.easybangumi.mirai",
        name = "纯纯看番q群插件",
        version = "0.1.0"
    ) {
        author("heyanle")
        info(
            """
            1. 监听 github webhook 同步新版本到群里
            2. 自动同意加群申请
        """.trimIndent()
        )
        // author 和 info 可以删除.
    }
) {

    override fun onEnable() {
        super.onEnable()
        val eventChannel = GlobalEventChannel.parentScope(this)

        //自动同意加群申请
        eventChannel.subscribeAlways<BotInvitedJoinGroupRequestEvent> {
            accept()
        }
    }

    override fun onDisable() {
        super.onDisable()
    }

}