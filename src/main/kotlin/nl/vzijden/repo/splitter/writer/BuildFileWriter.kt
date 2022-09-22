package nl.vzijden.repo.splitter.writer

import org.gradle.api.Project

internal fun writeBuildFile(project: Project): String = """
plugins {
    id("java")
    `maven-publish`
}

group = "${project.group}"
version = "${project.name}"

repositories {
    mavenCentral()
    mavenLocal()
} 
dependencies {
    ${writeConfigurations(project.configurations.toList())}
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "${project.group}"
            artifactId = "${project.name}"
            version = "${project.version}"

            from(components["java"])
        }
    }
}
"""
