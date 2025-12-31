cat << 'EOF' > build.gradle.kts
plugins {
    // Passage à une version plus robuste pour Termux
    kotlin("jvm") version "1.9.10" 
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.fardc.sigint"
version = "2.3.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.javalin:javalin:5.6.3")
    implementation("org.slf4j:slf4j-simple:2.0.7")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    implementation(kotlin("stdlib"))
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17" // Force la cible Java 17, standard stable
        freeCompilerArgs = listOf("-Xjsr305=strict")
    }
}

tasks.shadowJar {
    archiveBaseName.set("sigint-core-all")
    archiveClassifier.set("")
    archiveVersion.set("")
}
EOF
