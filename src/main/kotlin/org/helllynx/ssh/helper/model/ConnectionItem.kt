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
)
