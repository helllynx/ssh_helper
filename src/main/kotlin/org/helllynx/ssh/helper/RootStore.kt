package org.helllynx.ssh.helper

import androidx.compose.runtime.*
import kotlinx.coroutines.*
import org.helllynx.ssh.helper.model.ConnectionItem
import org.helllynx.ssh.helper.model.getSshCommand
import org.helllynx.ssh.helper.store.JsonLocalStore
import org.helllynx.ssh.helper.store.Store
import org.helllynx.ssh.helper.utils.pingServer
import org.helllynx.ssh.helper.utils.runCommand
import java.nio.file.Files
import java.nio.file.Paths

internal class RootStore {
    val store: Store = JsonLocalStore()

    var state: RootState by mutableStateOf(initialState())
        private set

    var job: Job = GlobalScope.launch {
        while (true) {
            try {
                delay(1000)
                state.items.parallelStream().map {item ->
                    item to pingServer(item.host)
                }.forEach { itemToPing ->
                    setState {
                        updateItem(id = requireNotNull(itemToPing.first.id)) { it.copy(available = itemToPing.second) }
                    }
                }
                delay(30000)
            } catch (e: Exception) {
                e.printStackTrace()
                delay(10000)
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun onItemClicked(id: Long) {
        val connection = state.items.first {
            it.id==id
        }

        GlobalScope.launch { // TODO replace GlobalScope with something more safe
            connection.getSshCommand().runCommand().apply {
                println(this)
            }
        }
    }

    fun onItemLongClicked(id: Long) {
        setState { copy(editingItemId = id) }
    }

    fun onItemDeleteClicked(id: Long) {
        setState { copy(items = items.filterNot { it.id==id }) }
        store.saveConnections(state.items)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun onItemSshfsClicked(id: Long) {
        val connection = state.items.first {
            it.id==id
        }

        val pathToSshfsMountDir = "${System.getProperty("user.home")}/SSH_HELPER/${connection.label.filter { !it.isWhitespace() }}"
        val commandSshfs =
            "sshfs -o reconnect,ServerAliveInterval=15,ServerAliveCountMax=3 ${connection.user}@${connection.host}:/ $pathToSshfsMountDir -p ${connection.port}".split(" ")

        Files.createDirectories(Paths.get(pathToSshfsMountDir))

        if (connection.password.isNotEmpty()) {
            setState { copy(exceptionCommand = ExceptionSSHFSCommand(commandSshfs.joinToString(" "), connection.password)) }
            return
        }

        GlobalScope.launch {
            commandSshfs.runCommand()
        }
    }

    fun onItemDetailsClicked(id: Long) {
//        setState { copy(detailsItemId = id) }
        val connection = state.items.first {
            it.id==id
        }

        setState {
            updateItem(id = requireNotNull(detailsItemId)) {
                it.copy(details = connection.getSshCommand().joinToString { " " })
            }
        }
    }

    fun onItemDetailsCloseClicked() {
        setState { copy(detailsItemId = null) }
    }

    fun onItemDetailsChanged(text: String) {
        setState {
            updateItem(id = requireNotNull(detailsItemId)) {
                it.copy(details = text)
            }
        }
    }

    fun onExceptionCommandCloseClicked() {
        setState { copy(exceptionCommand = null) }
    }

    fun onAddItemClicked() {
        setState {
            val newItem =
                ConnectionItem(
                    id = items.maxOfOrNull(ConnectionItem::id)?.plus(1L) ?: 1L,
                    label = inputText,
                )

            copy(items = items + newItem, inputText = "New Connection")
        }
    }

    fun onInputTextChanged(text: String) {
        setState { copy(inputText = text) }
    }

    fun onEditorCloseClicked() {
        setState { copy(editingItemId = null) }
    }

    fun onLabelTextChanged(text: String) {
        setState {
            updateItem(id = requireNotNull(editingItemId)) { it.copy(label = text) }
        }
    }

    fun onHostTextChanged(text: String) {
        setState {
            updateItem(id = requireNotNull(editingItemId)) { it.copy(host = text) }
        }
    }

    fun onPortTextChanged(text: String) {
        setState {
            updateItem(id = requireNotNull(editingItemId)) { it.copy(port = text) }
        }
    }

    fun onUserTextChanged(text: String) {
        setState {
            updateItem(id = requireNotNull(editingItemId)) { it.copy(user = text) }
        }
    }

    fun onPasswordTextChanged(text: String) {
        setState {
            updateItem(id = requireNotNull(editingItemId)) { it.copy(password = text) }
        }
    }

    fun onSaveClicked() {
        store.saveConnections(state.items)
    }


    fun onEditorDoneChanged(isDone: Boolean) {
        setState {
            updateItem(id = requireNotNull(editingItemId)) { it.copy() }
        }
    }

    private fun RootStore.getItem(id: Long): ConnectionItem = state.items.first { it.id==id }

    private fun RootState.updateItem(id: Long, transformer: (ConnectionItem) -> ConnectionItem): RootState =
        copy(items = items.updateItem(id = id, transformer = transformer))

    private fun List<ConnectionItem>.updateItem(
        id: Long,
        transformer: (ConnectionItem) -> ConnectionItem
    ): List<ConnectionItem> =
        map { item -> if (item.id==id) transformer(item) else item }

    private fun initialState(): RootState =
        RootState(
            items = store.getAllConnections()
        )

    private inline fun setState(update: RootState.() -> RootState) {
        state = state.update()
    }

    data class RootState(
        val items: List<ConnectionItem> = emptyList(),
        val inputText: String = "",
        var exceptionCommand: ExceptionSSHFSCommand? = null,
        val editingItemId: Long? = null,
        val detailsItemId: Long? = null,
    )

    data class ExceptionSSHFSCommand(val command: String, val password: String)
}
