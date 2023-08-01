package org.helllynx.ssh.helper.utils


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
