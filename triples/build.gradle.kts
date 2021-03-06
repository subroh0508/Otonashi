import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

group = "net.subroh0508.otonashi"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
}

dependencies {
    compile(Dep.Kotlin.stdlibJdk8)
    testCompile(Dep.junit)
}

val compileKotlin by tasks.getting(KotlinCompile::class) {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

val bintrayTask: (libraryName: String, versionName: String) -> Unit by ext

bintrayTask("Otonashi-Triples", Packages.versionName)
