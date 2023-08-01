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

//    fun doDatabaseThings(driver: SqlDriver) {
//        val database = Database(driver)
//        val playerQueries: PlayerQueries = database.playerQueries
//
//        println(playerQueries.selectAll().executeAsList())
//        // [HockeyPlayer(15, "Ryan Getzlaf")]
//
//        playerQueries.insert(player_number = 10, full_name = "Corey Perry")
//        println(playerQueries.selectAll().executeAsList())
//        // [HockeyPlayer(15, "Ryan Getzlaf"), HockeyPlayer(10, "Corey Perry")]
//
//        val player = HockeyPlayer(10, "Ronald McDonald")
//        playerQueries.insertFullPlayerObject(player)
//    }

}
