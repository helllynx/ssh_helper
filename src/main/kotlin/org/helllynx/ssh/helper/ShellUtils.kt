package org.helllynx.ssh.helper


import java.io.File


fun String.runCommand(
    workingDir: File = File("."),
//    timeoutAmount: Long = 60,
//    timeoutUnit: TimeUnit = TimeUnit.SECONDS
): String? = runCatching {
    ProcessBuilder("\\s".toRegex().split(this))
        .directory(workingDir)
        .redirectOutput(ProcessBuilder.Redirect.PIPE)
        .redirectError(ProcessBuilder.Redirect.PIPE)
        .start()
//        .also { it.waitFor(timeoutAmount, timeoutUnit) }
        .inputStream.bufferedReader().readText()
}.onFailure { it.printStackTrace() }.getOrNull()
