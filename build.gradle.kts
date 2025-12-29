// build.gradle.kts
plugins {
    kotlin("jvm") version "1.9.24"    // version stable côté Gradle 8.2
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.fardc.sigint"
version = "1.0"

repositories { mavenCentral() }

dependencies {
    implementation(kotlin("stdlib"))
    // Ajoute ici tes libs si besoin (aucune nécessaire pour java.net/Thread: c’est dans le JDK)
}

kotlin { jvmToolchain(17) }

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xno-param-assertions", "-Xno-call-assertions")
    }
}

tasks.shadowJar {
    archiveBaseName.set("sigint-core")
    archiveClassifier.set("all")
    manifest {
        attributes["Main-Class"] = "com.fardc.sigint.core.MainKt"
    }
}