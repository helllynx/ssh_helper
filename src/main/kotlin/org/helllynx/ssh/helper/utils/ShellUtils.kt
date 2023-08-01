package org.helllynx.ssh.helper.utils

import org.helllynx.ssh.helper.model.ConnectionItem


fun List<String>.runCommand(
): String? = runCatching {
//    ProcessBuilder("\\s".toRegex().split(this))
    ProcessBuilder(this)
//        .directory(workingDir)
        .redirectOutput(ProcessBuilder.Redirect.PIPE)
        .redirectError(ProcessBuilder.Redirect.PIPE)
        .start()
//        .also { it.waitFor(timeoutAmount, timeoutUnit) }
        .inputStream.bufferedReader().readText()
}.onFailure { it.printStackTrace() }.getOrNull()

fun String.runCommand(): String? {
    return "\\s".toRegex().split(this).runCommand()
}

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
