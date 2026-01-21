plugins {
    java
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    maven("https://jogamp.org/deployment/maven")
    mavenCentral()
}

dependencies {
    implementation("org.jogamp.jogl:jogl-all:2.6.0")
    implementation("org.jogamp.gluegen:gluegen-rt:2.6.0")

    runtimeOnly(files("libs/jogl-all-natives-windows-amd64.jar"))
    runtimeOnly(files("libs/gluegen-rt-natives-windows-amd64.jar"))
}

application {
    mainClass.set("game.Main")
}

// startScripts を無効化（短期回避）
tasks.named<CreateStartScripts>("startScripts") {
    enabled = false
}

tasks.named<JavaExec>("run") {
    jvmArgs = listOf(
        // ネイティブロードの警告を抑え、必要なアクセスを許可する（開発用）
        "--enable-native-access=ALL-UNNAMED",
        // 必要に応じて add-opens を追加（AWT などで警告が出る場合）
        "--add-opens=java.base/java.lang=ALL-UNNAMED",
        "--add-opens=java.desktop/sun.awt=ALL-UNNAMED",
    )
}