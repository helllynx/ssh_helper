package org.helllynx.ssh.helper

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.helllynx.ssh.helper.model.ConnectionItem
import org.helllynx.ssh.helper.utils.MARGIN_SCROLLBAR

@Composable
internal fun MainContent(
    modifier: Modifier = Modifier,
    items: List<ConnectionItem>,
    onItemClicked: (id: Long) -> Unit,
    onItemLongClicked: (id: Long) -> Unit,
    onItemDeleteClicked: (id: Long) -> Unit,
    onItemSshfsClicked: (id: Long) -> Unit,
    onItemDetailsClicked: (id: Long) -> Unit,
    onAddItemClicked: () -> Unit,
) {
    Column(modifier) {
        Header(
            onAddClicked = onAddItemClicked,
            onSettingsClicked = { },
        )

        Box(Modifier.weight(1F)) {
            ListContent(
                items = items,
                onItemClicked = onItemClicked,
                onItemLongClicked = onItemLongClicked,
                onItemDeleteClicked = onItemDeleteClicked,
                onItemSshfsClicked = onItemSshfsClicked,
                onItemDetailsClicked = onItemDetailsClicked,
            )
        }


    }
}

@Composable
private fun ListContent(
    items: List<ConnectionItem>,
    onItemClicked: (id: Long) -> Unit,
    onItemLongClicked: (id: Long) -> Unit,
    onItemDeleteClicked: (id: Long) -> Unit,
    onItemSshfsClicked: (id: Long) -> Unit,
    onItemDetailsClicked: (id: Long) -> Unit,
) {
    Box {
        val listState = rememberLazyListState()

        LazyColumn(state = listState) {
            items(items) { item ->
                Item(
                    item = item,
                    onClicked = { onItemClicked(item.id) },
                    onLongClicked = { onItemLongClicked(item.id) },
                    onDeleteClicked = { onItemDeleteClicked(item.id) },
                    onMountSshfsClicked = { onItemSshfsClicked(item.id) },
                    onDetailsClicked = { onItemDetailsClicked(item.id) },
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

@Composable
fun PingStatus(color: Color = Color.Red) {
    Canvas(
        modifier = Modifier
            .size(size = 32.dp)
//            .border(color = Color.Magenta, width = 2.dp)
    ) {
        drawCircle(
//            brush = Brush.horizontalGradient(colors = listOf(Color.Green, Color.Yellow)),
            color = color,
            radius = 8.dp.toPx()
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Item(
    item: ConnectionItem,
    onClicked: () -> Unit,
    onLongClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
    onMountSshfsClicked: () -> Unit,
    onDetailsClicked: () -> Unit,
) {
    Row(modifier = Modifier.combinedClickable(onClick = onClicked, onLongClick = onLongClicked)) {
        Spacer(modifier = Modifier.width(8.dp))

        when(item.available) {
            true -> PingStatus(Color.Green)
            false -> PingStatus(Color.Red)
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = AnnotatedString(item.label),
            modifier = Modifier.weight(1F).align(Alignment.CenterVertically),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = AnnotatedString(item.host),
            modifier = Modifier.weight(1F).align(Alignment.CenterVertically),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(onClick = onMountSshfsClicked) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = null
            )
        }

        IconButton(onClick = onDetailsClicked) {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = null
            )
        }

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
private fun Header(
    onAddClicked: () -> Unit,
    onSettingsClicked: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = Modifier.padding(8.dp).background(Color.LightGray, shape = RoundedCornerShape(4.dp))
    ) {
        IconButton(onClick = onAddClicked) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add connection"
            )
        }
        IconButton(onClick = onSettingsClicked) {
            Icon(Icons.Default.Settings, contentDescription = "Settings")
        }
    }
}


