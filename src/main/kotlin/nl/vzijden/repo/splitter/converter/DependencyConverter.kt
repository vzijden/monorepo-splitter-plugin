package nl.vzijden.repo.splitter.converter

import org.gradle.api.artifacts.ConfigurationContainer
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.internal.artifacts.dependencies.DefaultExternalModuleDependency

fun ConfigurationContainer.convertProjectDependencies() {
    forEach { configuration ->
        val projectDependencies = configuration.dependencies.filter { it is ProjectDependency }
        configuration.dependencies.removeAll(projectDependencies)
        val convertedDependencies = projectDependencies.map(::convertToExternalDependency)
        configuration.dependencies.addAll(convertedDependencies)
    }
}

private fun convertToExternalDependency(dependency: Dependency): Dependency {
    return DefaultExternalModuleDependency(dependency.group!!, dependency.name, "1.0.0-SNAPSHOT")
}
