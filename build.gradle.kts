plugins {
    kotlin("jvm") version "1.9.24"            // Compatible Gradle 8.2, évite BuildFlowService
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.fardc.sigint"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.javalin:javalin:5.6.1")
    implementation("org.slf4j:slf4j-simple:2.0.7")
}

kotlin {
    jvmToolchain(17)                          // Verrouillage Java 17
}

tasks.shadowJar {
    archiveBaseName.set("sigint-core")
    archiveClassifier.set("all")
    mergeServiceFiles()                       // Fusionne les fichiers de services si présents
    manifest {
        attributes["Main-Class"] = "com.fardc.sigint.core.MainKt"]
    }
}