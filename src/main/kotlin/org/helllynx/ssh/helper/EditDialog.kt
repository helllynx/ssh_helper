package org.helllynx.ssh.helper

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.helllynx.ssh.manager.common.org.helllynx.ssh.helper.Dialog

@Composable
internal fun EditDialog(
    item: ConnectionItem,
    onCloseClicked: () -> Unit,
    onLabelTextChanged: (String) -> Unit,
    onHostTextChanged: (String) -> Unit,
    onPortTextChanged: (String) -> Unit,
    onUserTextChanged: (String) -> Unit,
    onPasswordTextChanged: (String) -> Unit,
    onSaveClicked: () -> Unit,
) {
    Dialog(
        title = "Edit connection",
        onCloseRequest = onCloseClicked,
    ) {
        Column(horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Top) {
            TextField(
                value = item.label,
                modifier = Modifier.fillMaxWidth().sizeIn(minHeight = 24.dp),
                label = { Text("label") },
                onValueChange = onLabelTextChanged,
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = item.host,
                modifier = Modifier.fillMaxWidth().sizeIn(minHeight = 24.dp),
                label = { Text("host") },
                onValueChange = onHostTextChanged,
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = item.port,
                modifier = Modifier.fillMaxWidth().sizeIn(minHeight = 24.dp),
                label = { Text("port") },
                onValueChange = onPortTextChanged,
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = item.user,
                modifier = Modifier.fillMaxWidth().sizeIn(minHeight = 24.dp),
                label = { Text("user") },
                onValueChange = onUserTextChanged,
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = item.password,
                modifier = Modifier.fillMaxWidth().sizeIn(minHeight = 24.dp),
                label = { Text("password") },
                onValueChange = onPasswordTextChanged,
            )
            OutlinedButton(
                onClick = onSaveClicked,
            ) {
                Text("Save")
            }
        }
    }
}
