import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.0"
    kotlin("plugin.serialization") version "1.9.0"
    id("org.jetbrains.compose") version "1.5.0-beta01"
    id("app.cash.sqldelight") version "2.0.0"
}

group = "org.ssh.helper"
version = "1.0.2"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
    implementation("org.junit.jupiter:junit-jupiter:5.9.2")

    implementation("app.cash.sqldelight:sqlite-driver:2.0.0")
}

sqldelight {
    databases {
        create("SshHelper") {
            packageName.set("org.helllynx")
        }
    }
}

tasks.test {
    // Use the built-in JUnit support of Gradle.
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
    kotlinOptions.freeCompilerArgs += "-Xuse-experimental=kotlin.experimental -opt-in=kotlin.RequiresOptIn"
}

compose.desktop {
    application {
        mainClass = "org.helllynx.ssh.helper.MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "SshHelper"
            packageVersion = version.toString()
        }
    }
}
