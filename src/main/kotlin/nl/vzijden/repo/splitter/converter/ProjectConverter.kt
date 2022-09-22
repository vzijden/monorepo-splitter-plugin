package nl.vzijden.repo.splitter.converter

import org.gradle.api.Project

fun convertProject(project: Project) {
    project.run {
        configurations.convertProjectDependencies()
    }
}

