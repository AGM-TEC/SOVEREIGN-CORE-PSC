plugins {
    kotlin("jvm") version "2.3.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.fardc.sigint"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    // Ajoute des libs si nécessaires (sans activer de fonctions réseau offensives)
    // implementation("org.slf4j:slf4j-simple:2.0.12")
}

kotlin {
    jvmToolchain(17)
}

tasks.test {
    useJUnitPlatform()
}

tasks.shadowJar {
    archiveBaseName.set("sigint-core")
    archiveClassifier.set("all")
    manifest {
        attributes["Main-Class"] = "com.fardc.sigint.core.MainKt"
    }
}