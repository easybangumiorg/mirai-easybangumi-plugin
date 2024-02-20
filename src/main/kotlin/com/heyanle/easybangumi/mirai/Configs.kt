package org.example.mirai.plugin.com.heyanle.easybangumi.mirai

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.value

object Configs : AutoSavePluginConfig("settings") {

    val botId: Long by value(0L)

    val adminId: Long by value(0L)

    var enabled: Boolean by value(false)

    var groupId: String by value("[]")

    val githubWebHookPort: Int by value(9999)

    val githubWebHookSecret: String by value("")

}