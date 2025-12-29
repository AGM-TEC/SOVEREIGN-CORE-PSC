plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.21" // Version stable de repli
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.fardc.sigint"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("io.javalin:javalin:5.6.1")
    implementation("org.slf4j:slf4j-simple:2.0.7")
}

// Verrouillage de la toolchain contre l'empoisonnement Java 25
kotlin {
    jvmToolchain(17)
}

tasks.shadowJar {
    archiveBaseName.set("sigint-core")
    archiveClassifier.set("all")
    manifest {
        attributes["Main-Class"] = "com.fardc.sigint.core.MainKt"
    }
}
