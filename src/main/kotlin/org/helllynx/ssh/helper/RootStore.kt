package org.helllynx.ssh.helper

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

internal class RootStore {
    val store: Store = JsonLocalStore()

    var state: RootState by mutableStateOf(initialState())
        private set

    fun onItemClicked(id: Long) {
        println(getItem(id))
    }

    fun onItemLongClicked(id: Long) {
        setState { copy(editingItemId = id) }
    }

    fun onItemDeleteClicked(id: Long) {
        setState { copy(items = items.filterNot { it.id == id }) }
    }

    fun onAddItemClicked() {
        setState {
            val newItem =
                ConnectionItem(
                    id = items.maxOfOrNull(ConnectionItem::id)?.plus(1L) ?: 1L,
                    label = inputText,
                )

            copy(items = items + newItem, inputText = "")
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

    fun onSaveClicked() {
        store.saveConnections(state.items)
    }

    fun onEditorDoneChanged(isDone: Boolean) {
        setState {
            updateItem(id = requireNotNull(editingItemId)) { it.copy() }
        }
    }

    private fun RootStore.getItem(id: Long): ConnectionItem = state.items.first { it.id == id }

    private fun RootState.updateItem(id: Long, transformer: (ConnectionItem) -> ConnectionItem): RootState =
        copy(items = items.updateItem(id = id, transformer = transformer))

    private fun List<ConnectionItem>.updateItem(id: Long, transformer: (ConnectionItem) -> ConnectionItem): List<ConnectionItem> =
        map { item -> if (item.id == id) transformer(item) else item }

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
        val editingItemId: Long? = null,
    )
}
