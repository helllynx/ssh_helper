package org.helllynx.ssh.helper.utils

import java.net.InetAddress


fun pingServer(host: String, timeout: Int = 10000) = try {
    InetAddress.getByName(host).isReachable(timeout)
} catch (e: Exception) {
    false
}

