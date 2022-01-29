package org.helllynx.ssh.helper.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.helllynx.ssh.helper.Dialog
import org.helllynx.ssh.helper.model.ConnectionItem

@Composable
internal fun CommandCopyPastDialog(
    command: String,
    password: String,
    onCloseClicked: () -> Unit,
    ) {
    Dialog(
        title = "Command dialog",
        onCloseRequest = onCloseClicked,
        ) {
        Column(horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Top) {
            TextField(
                value = command,
                modifier = Modifier.fillMaxWidth().sizeIn(minHeight = 24.dp),
                label = { Text("command") },
                onValueChange = {},
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = password,
                modifier = Modifier.fillMaxWidth().sizeIn(minHeight = 24.dp),
                label = { Text("password") },
                onValueChange = {},
            )
        }
    }
}
