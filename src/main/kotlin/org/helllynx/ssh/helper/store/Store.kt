package org.helllynx.ssh.helper.store

import org.helllynx.ssh.helper.model.ConnectionItem

interface Store {
    fun saveConnection(connection: ConnectionItem)

    fun saveConnections(connections: List<ConnectionItem>)

    fun getConnectionById(id: Long): ConnectionItem

    fun getAllConnections(): List<ConnectionItem>
}
