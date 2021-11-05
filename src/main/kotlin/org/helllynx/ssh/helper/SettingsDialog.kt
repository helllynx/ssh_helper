package org.helllynx.ssh.helper

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun SettingsDialog(
    onCloseClicked: () -> Unit = {},
) {
    Dialog(
        title = "Edit connection",
        onCloseRequest = onCloseClicked,
    ) {
        Column(horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Top) {
            DropdownDemo()
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
