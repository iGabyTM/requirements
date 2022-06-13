rootProject.name = "requirements"

listOf(
    "core",
    "bukkit"
).forEach(::includeProject)

/**
 * @author Matt
 */
fun includeProject(name: String) {
    include(name) {
        this.name = "${rootProject.name}-$name"
    }
}

/**
 * @author Matt
 */
fun includeProject(name: String, folder: String) {
    include(name) {
        this.name = "${rootProject.name}-$name"
        this.projectDir = file("$folder/$name")
    }
}

/**
 * @author Matt
 */
fun include(name: String, block: ProjectDescriptor.() -> Unit) {
    include(name)
    project(":$name").apply(block)
}