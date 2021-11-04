package org.helllynx.ssh.helper

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun SettingsDialog(
    onCloseClicked: () -> Unit,
) {
    Dialog(
        title = "Edit connection",
        onCloseRequest = onCloseClicked,
    ) {
        Column(horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Top) {
            TextField(
                value = item.password,
                modifier = Modifier.fillMaxWidth().sizeIn(minHeight = 24.dp),
                label = { Text("password") },
                onValueChange = onPasswordTextChanged,
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(
                onClick = onSaveClicked,
            ) {
                Text("Save")
            }
        }
    }
}
