# JOGL と Swing のサンプル

## 概要
JOGL と Swing を組み合わせたサンプルプロジェクト。Gradle でビルドし、ネイティブは jogamp-all-platforms から取得します。

## 前提
- Windows x64, JDK 64-bit
- Gradle Wrapper を使用

## 手順
1. https://jogamp.org/wiki/index.php/Downloading_and_installing_JOGL から `jogamp-all-platforms.7z` をダウンロードして解凍する  
2. `jar/` フォルダから `jogl-all-natives-windows-amd64.jar` と `gluegen-rt-natives-windows-amd64.jar` を取り出し、プロジェクト直下の `libs/` に置く  
3. `./gradlew --stop` → `./gradlew run`

## トラブルシュート
- `UnsatisfiedLinkError` → ネイティブが不足、またはアーキテクチャ不一致
- 実行時の警告 → `build.gradle.kts` の `run.jvmArgs` に `--enable-native-access=ALL-UNNAMED` を追加
