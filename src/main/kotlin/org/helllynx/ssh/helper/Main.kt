package org.helllynx.ssh.helper

import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState


fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "SSH manager",
        state = rememberWindowState(
            position = WindowPosition(alignment = Alignment.Center),
            size = DpSize(800.dp, 900.dp),
        ),
    ) {
        App()
    }
}
