plugins {
    id("com.github.johnrengelman.shadow") version("7.0.0")
}

repositories {
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.16.4-R0.1-SNAPSHOT")
    testCompileOnly("org.spigotmc:spigot-api:1.16.4-R0.1-SNAPSHOT")
    api(project(":requirements-core"))
}