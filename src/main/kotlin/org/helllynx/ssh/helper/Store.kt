package org.helllynx.ssh.helper

interface Store {
    fun saveConnection(connection: ConnectionItem)

    fun saveConnections(connections: List<ConnectionItem>)

    fun getConnectionById(id: Long): ConnectionItem

    fun getAllConnections(): List<ConnectionItem>
}
