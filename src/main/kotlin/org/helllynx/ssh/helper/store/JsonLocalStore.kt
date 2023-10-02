package org.helllynx.ssh.helper.store


import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.helllynx.ssh.helper.model.ConnectionItem
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

class JsonLocalStore : Store {

    private val pathToHome = System.getProperty("user.home")
    private val pathToAppFolder = "$pathToHome/Sync/ssh_helper"
    private val pathToJsonFile = "$pathToAppFolder/store.json"

    init {
        Files.createDirectories(Paths.get(pathToAppFolder))
    }

    override fun saveConnection(connection: ConnectionItem) {
        val oldData = readFileOrCreateIfNotExists(pathToJsonFile)

        if (oldData.isBlank()) {
            writeFileCreateIfNotExists(pathToJsonFile, Json.encodeToString(listOf(connection)))
            return
        }

        val parsedData = Json.decodeFromString<MutableList<ConnectionItem>>(oldData)
        parsedData.add(connection)
        writeFileCreateIfNotExists(pathToJsonFile, Json.encodeToString(parsedData))
    }

    override fun saveConnections(connections: List<ConnectionItem>) {
        writeFileCreateIfNotExists(pathToJsonFile, Json.encodeToString(connections))
    }

    override fun getConnectionById(id: Long): ConnectionItem {
        val oldData = readFileOrCreateIfNotExists(pathToJsonFile)
        if (oldData.isEmpty()) throw IOException("No such element in connections")
        val parsedData = Json.decodeFromString<MutableList<ConnectionItem>>(oldData)
        return parsedData.first { it.id==id }
    }

    override fun getAllConnections(): List<ConnectionItem> {
        val oldData = readFileOrCreateIfNotExists(pathToJsonFile)
        if (oldData.isEmpty()) return emptyList()
        return Json.decodeFromString<MutableList<ConnectionItem>>(oldData)
    }
}

fun readFileOrCreateIfNotExists(pathToFile: String): String {
    val file = File(pathToFile)
    if (!file.exists())
        file.createNewFile()
    return file.readText()
}

fun writeFileCreateIfNotExists(pathToFile: String, text: String) {
    val file = File(pathToFile)
    if (!file.exists())
        file.createNewFile()
    return file.writeText(text)
}
