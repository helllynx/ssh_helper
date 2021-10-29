package org.helllynx.ssh.helper

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.helllynx.ssh.manager.common.org.helllynx.ssh.helper.MARGIN_SCROLLBAR
import org.helllynx.ssh.manager.common.org.helllynx.ssh.helper.VerticalScrollbar
import org.helllynx.ssh.manager.common.org.helllynx.ssh.helper.rememberScrollbarAdapter

@Composable
internal fun MainContent(
    modifier: Modifier = Modifier,
    items: List<ConnectionItem>,
    inputText: String,
    onItemClicked: (id: Long) -> Unit,
    onItemLongClicked: (id: Long) -> Unit,
    onItemDeleteClicked: (id: Long) -> Unit,
    onAddItemClicked: () -> Unit,
    onInputTextChanged: (String) -> Unit,
) {
    Column(modifier) {
        TopAppBar(title = { Text(text = "SSH Helper") })

        Box(Modifier.weight(1F)) {
            ListContent(
                items = items,
                onItemClicked = onItemClicked,
                onItemLongClicked = onItemLongClicked,
                onItemDeleteClicked = onItemDeleteClicked
            )
        }

        Input(
            text = inputText,
            onAddClicked = onAddItemClicked,
            onTextChanged = onInputTextChanged
        )
    }
}

@Composable
private fun ListContent(
    items: List<ConnectionItem>,
    onItemClicked: (id: Long) -> Unit,
    onItemLongClicked: (id: Long) -> Unit,
    onItemDeleteClicked: (id: Long) -> Unit,
) {
    Box {
        val listState = rememberLazyListState()

        LazyColumn(state = listState) {
            items(items) { item ->
                Item(
                    item = item,
                    onClicked = { onItemClicked(item.id) },
                    onLongClicked = { onItemLongClicked(item.id) },
                    onDeleteClicked = { onItemDeleteClicked(item.id) }
                )

                Divider()
            }
        }

        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
            adapter = rememberScrollbarAdapter(scrollState = listState)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Item(
    item: ConnectionItem,
    onClicked: () -> Unit,
    onLongClicked: () -> Unit,
//    onDoneChanged: (Boolean) -> Unit,
    onDeleteClicked: () -> Unit
) {
    Row(modifier = Modifier.combinedClickable(onClick = onClicked, onLongClick = onLongClicked)) {
        Spacer(modifier = Modifier.width(8.dp))

//        Checkbox(
//            checked = item.isDone,
//            modifier = Modifier.align(Alignment.CenterVertically),
//            onCheckedChange = onDoneChanged,
//        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = AnnotatedString(item.label),
            modifier = Modifier.weight(1F).align(Alignment.CenterVertically),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(onClick = onDeleteClicked) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.width(MARGIN_SCROLLBAR))
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun Input(
    text: String,
    onTextChanged: (String) -> Unit,
    onAddClicked: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
//        OutlinedTextField(
//            value = text,
//            modifier = Modifier
//                .weight(weight = 1F)
//                .onKeyUp(key = Key.Enter, action = onAddClicked),
//            onValueChange = onTextChanged,
//            label = { Text(text = "Add a todo") }
//        )

//        Spacer(modifier = Modifier.width(8.dp))

        IconButton(onClick = onAddClicked) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null
            )
        }
    }
}
