plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.0.0"
    application

}

application {
    mainClass = "org.proyecto.Main"
}

group = "org.proyecto"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.opencsv:opencsv:3.7")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.16.0")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.jar {
    manifest.attributes["Main-Class"] = "org.proyecto.Main"
}

tasks.test {
    useJUnitPlatform()
}