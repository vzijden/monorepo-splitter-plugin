subprojects {
    apply {
        plugin("java")
    }

    group = "nl.vzijden"
    version = "1.0.0-SNAPSHOT"

    repositories {
        mavenCentral()
        mavenLocal()
    }
}

plugins {
    id("java")
    id("nl.vzijden.repo.splitter")
}
