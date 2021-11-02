package org.helllynx.ssh.helper

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

@Composable
@Preview
fun App() {
    MaterialTheme {
        RootContent(
            modifier = Modifier.fillMaxSize()
        )
    }
    
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "SSH manager",
        state = rememberWindowState(
            position = WindowPosition(alignment = Alignment.Center),
        ),
    ) {
        App()
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
        onAddItemClicked = model::onAddItemClicked,
        onSettingsClicked = model::onSettingsClicked,
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
}

private val RootStore.RootState.editingItem: ConnectionItem?
    get() = editingItemId?.let(items::firstById)

private fun List<ConnectionItem>.firstById(id: Long): ConnectionItem =
    first { it.id==id }
