package org.helllynx.ssh.helper.store

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.helllynx.ssh.helper.model.ConnectionItem

class SqlLiteDatabase: Store {
    private val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:database.db")

    override fun saveConnection(connection: ConnectionItem) {
        TODO("Not yet implemented")
    }

    override fun saveConnections(connections: List<ConnectionItem>) {
        TODO("Not yet implemented")
    }

    override fun getConnectionById(id: Long): ConnectionItem {
        TODO("Not yet implemented")
    }

    override fun getAllConnections(): List<ConnectionItem> {
        TODO("Not yet implemented")
    }

}
