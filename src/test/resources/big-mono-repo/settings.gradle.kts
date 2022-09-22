rootProject.name = "big-mono-repo"

include("module1")
include("module2")
include("module3")
include("module4")

pluginManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()

    }

    plugins {
        id("nl.vzijden.repo.splitter") version "1.0.0-SNAPSHOT"
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library("junit-jupiter-api", "org.junit.jupiter:junit-jupiter-api:5.8.1")
            library("junit-jupiter-engine", "org.junit.jupiter:junit-jupiter-api:5.8.1")
        }
    }
}