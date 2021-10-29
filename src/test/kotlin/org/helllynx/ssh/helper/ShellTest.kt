package org.helllynx.ssh.helper

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test


class ShellTest {

    @Test
    fun runShell() {
        val connection = ConnectionItem(
            user = "user",
            host = "127.0.0.1",
            port = "22",
        )
        val command = "ssh ${connection.user}@${connection.host} -p ${connection.port}"
        runBlocking {
            command.runCommand()
        }
    }
}