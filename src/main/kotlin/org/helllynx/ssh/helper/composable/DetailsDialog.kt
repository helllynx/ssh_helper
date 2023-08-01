package org.helllynx.ssh.helper.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.helllynx.ssh.helper.utils.Dialog
import org.helllynx.ssh.helper.model.ConnectionItem

@Composable
internal fun DetailsDialog(
    item: ConnectionItem,
    onCloseClicked: () -> Unit,
    onItemDetailsChanged: (String) -> Unit,
    ) {
    Dialog(
        title = "Info",
        onCloseRequest = onCloseClicked,
        ) {
        Column(horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Top) {
            TextField(
                value = item.details,
                modifier = Modifier.fillMaxWidth().sizeIn(minHeight = 150.dp),
                label = { Text("info") },
                onValueChange = onItemDetailsChanged,
                maxLines = 5
            )
            TextField(
                value = item.details,
                modifier = Modifier.fillMaxWidth().sizeIn(minHeight = 100.dp),
                label = { Text("ssh") },
                onValueChange = onItemDetailsChanged,
                maxLines = 2
            )
        }
    }
}
