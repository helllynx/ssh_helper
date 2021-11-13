package org.helllynx.ssh.helper.composable

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.helllynx.ssh.helper.Dialog
import org.helllynx.ssh.helper.DropdownDemo

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
