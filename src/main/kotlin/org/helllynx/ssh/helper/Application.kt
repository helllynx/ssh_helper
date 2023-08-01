package org.helllynx.ssh.helper

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.helllynx.ssh.helper.composable.CommandCopyPastDialog
import org.helllynx.ssh.helper.composable.DetailsDialog
import org.helllynx.ssh.helper.composable.EditDialog
import org.helllynx.ssh.helper.model.ConnectionItem

@Composable
@Preview
fun App() {
    MaterialTheme {
        RootContent(
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun RootContent(modifier: Modifier = Modifier) {
    val model = remember { RootStore() }
    val state = model.state

    MainContent(
        modifier = modifier,
        items = state.items,
        onItemClicked = model::onItemClicked,
        onItemLongClicked = model::onItemLongClicked,
        onItemDeleteClicked = model::onItemDeleteClicked,
        onItemSshfsClicked = model::onItemSshfsClicked,
        onItemDetailsClicked = model::onItemDetailsClicked,
        onAddItemClicked = model::onAddItemClicked,
    )

    state.editingItem?.also { item ->
        EditDialog(
            item = item,
            onCloseClicked = model::onEditorCloseClicked,
            onLabelTextChanged = model::onLabelTextChanged,
            onHostTextChanged = model::onHostTextChanged,
            onPortTextChanged = model::onPortTextChanged,
            onUserTextChanged = model::onUserTextChanged,
            onPasswordTextChanged = model::onPasswordTextChanged,
            onSaveClicked = model::onSaveClicked,
        )
    }


    state.exceptionCommand?.also { command ->
        CommandCopyPastDialog(
            command = command.command,
            password = command.password,
            onCloseClicked = model::onExceptionCommandCloseClicked
        )
    }

    state.detailsItem?.also { item ->
        DetailsDialog(
            item = item,
            onCloseClicked = model::onItemDetailsCloseClicked,
            onItemDetailsChanged = model::onItemDetailsChanged
        )
    }

//    LaunchedEffect(Unit) {
//        while(true) {
//            delay(1000)
//            state.items.forEach {
//                it.available = pingServer(it.host)
//            }
//        }
//    }
}

private val RootStore.RootState.editingItem: ConnectionItem?
    get() = editingItemId?.let(items::firstById)

private val RootStore.RootState.detailsItem: ConnectionItem?
    get() = detailsItemId?.let(items::firstById)


private fun List<ConnectionItem>.firstById(id: Long): ConnectionItem =
    first { it.id==id }
