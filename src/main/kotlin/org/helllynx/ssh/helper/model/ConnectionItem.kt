package org.helllynx.ssh.helper.model

import kotlinx.serialization.Serializable

@Serializable
data class ConnectionItem(
    val id: Long = 0L,
    val label: String = "",
    val host: String = "",
    val port: String = "22",
    val user: String = "root",
    val password: String = "",
    val details: String = "",
    var available: Boolean = false,
)

fun ConnectionItem.getSshCommand(copyPasteMode: Boolean = false): List<String> {
    val commandSsh = when (password.isEmpty()) {
        true  -> listOf("/bin/bash", "-ic", "konsole -e \"ssh -o ServerAliveInterval=15 -o ServerAliveCountMax=3 ${user}@${host} -p $port\"")
        false -> listOf("/bin/bash", "-ic", "konsole -e \"sshpass -p $password ssh -o ServerAliveInterval=15 -o ServerAliveCountMax=3 ${user}@${host} -p $port\"")
    }
    return if (copyPasteMode)
        listOf(commandSsh.drop(2).first().removePrefix("konsole -e \"").removeSuffix("\"")) // TODO refactor this crap
    else
        commandSsh
}
