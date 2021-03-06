import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("net.subroh0508.otonashi-vocabularies-generator") version Packages.Vocabularies.generatorVersion
}

group = "net.subroh0508.otonashi.vocabularies"
version = Packages.Vocabularies.imasparqlVersion

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
}

dependencies {
    compile(project(":triples"))
    compile(Dep.Kotlin.stdlibJdk8)
    testCompile(Dep.junit)
}

val compileKotlin by tasks.getting(KotlinCompile::class) {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

generateVocabulary {
    endpoint = "https://sparql.crssnky.xyz/imasrdf/URIs/imas-schema.ttl"
    outputPath = "src/main/kotlin/net/subroh0508/otonashi/vocabularies/imasparql"
    prefix(
        "imas" to "<https://sparql.crssnky.xyz/imasrdf/URIs/imas-schema.ttl#>",
        "imasrdf" to "<https://sparql.crssnky.xyz/imasrdf/RDFs/detail/>"
    )
}

val bintrayTask: (libraryName: String, versionName: String) -> Unit by ext

bintrayTask("Otonashi-Imasparql", Packages.Vocabularies.imasparqlVersion)
