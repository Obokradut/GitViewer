// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.42")
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.7.0")
        classpath ("com.android.tools.build:gradle:7.2.1")
    }
}
plugins {
    id ("com.android.application") version "7.2.0" apply false
    id ("com.android.library") version "7.2.0" apply false
    id ("org.jetbrains.kotlin.android") version "1.7.0" apply false
    id ("org.jetbrains.kotlin.plugin.serialization") version "1.6.21" apply false
}

tasks.register(name = "clean", type = Delete::class) {
    delete(rootProject.buildDir)
}
