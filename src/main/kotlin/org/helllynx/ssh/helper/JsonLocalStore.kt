package org.helllynx.ssh.helper


import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.IOException

class JsonLocalStore() : Store {

    private val pathToJsonFile = "store.json"
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