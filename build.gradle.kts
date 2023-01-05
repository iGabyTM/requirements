plugins {
    id("java-library")
    id("maven-publish")
    id("org.cadixdev.licenser") version("0.6.1")
}

repositories {
    mavenCentral()
}

subprojects {

    apply {
        plugin("java-library")
        plugin("maven-publish")
        plugin("org.cadixdev.licenser")
    }

    group = "me.gabytm.minecraft.util"
    version = "1.0.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    dependencies {
        compileOnlyApi("org.jetbrains:annotations:23.0.0")
        testCompileOnly("org.jetbrains:annotations:23.0.0")
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        withSourcesJar()
        withJavadocJar()
    }

    license {
        setHeader(rootProject.file("LICENSE"))
        include("**/*.java")
        style.put("java", org.cadixdev.gradle.licenser.header.HeaderStyle.JAVADOC)
    }

    tasks {

        javadoc {
            setDestinationDir(file("../docs/${project.version}/${project.name.removePrefix("requirements-")}"))
            options.encoding = "UTF-8"

            if (options is StandardJavadocDocletOptions) {
                val opt = options as StandardJavadocDocletOptions

                opt.links(
                    "https://docs.oracle.com/javase/8/docs/api/",
                    "https://javadoc.io/doc/org.jetbrains/annotations/latest/"
                )

                if (JavaVersion.current().isJava9Compatible) {
                    opt.addBooleanOption("html5", true)
                    opt.addStringOption("-release", "9")
                }
            }
        }

        publishing {
            publications {
                create<MavenPublication>("maven") {
                    from(project.components["java"])

                    versionMapping {
                        usage("java-api") {
                            fromResolutionOf("runtimeClasspath")
                        }
                        usage("java-runtime") {
                            fromResolutionResult()
                        }
                    }

                    pom {
                        name.set("requirements")
                        url.set("https://github.com/iGabyTM/requirements")

                        licenses {
                            license {
                                name.set("MIT License")
                                url.set("https://www.opensource.org/licenses/mit-license.php")
                            }
                        }

                        developers {
                            developer {
                                id.set("GabyTM")
                                name.set("Gabriel Dumitru")
                                email.set("contactgabytm@gmail.com")
                                url.set("https://github.com/iGabyTM")
                                timezone.set("Europe/Bucharest")
                            }
                        }

                        scm {
                            connection.set("scm:git:git://github.com/iGabyTM/requirements.git")
                            developerConnection.set("scm:git:ssh://github.com:iGabyTM/requirements.git")
                            url.set("https://github.com/iGabyTM/requirements")
                        }
                    }
                }
            }

            repositories {
                maven {
                    credentials {
                        username = System.getenv("MATT_REPO_USERNAME")
                        password = System.getenv("MATT_REPO_PASSWORD")
                    }

                    url = uri("https://repo.triumphteam.dev/" + (if (project.version.toString().endsWith("SNAPSHOT")) "snapshots/" else "releases/"))
                }
            }
        }

        withType<PublishToMavenRepository> {
            dependsOn("test")
        }

        withType<PublishToMavenLocal> {
            dependsOn("test")
        }

    }

}