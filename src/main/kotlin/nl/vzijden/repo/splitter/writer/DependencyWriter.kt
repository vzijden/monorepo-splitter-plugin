package nl.vzijden.repo.splitter.writer

import org.gradle.api.artifacts.Configuration


fun writeConfigurations(configurations: List<Configuration>): String {
    return configurations.flatMap { configuration ->
        configuration.dependencies.map { dependency ->
            """${configuration.name}("${dependency.group}:${dependency.name}:${dependency.version}")"""
        }
    }.joinToString(System.lineSeparator() + "\t\t")
}