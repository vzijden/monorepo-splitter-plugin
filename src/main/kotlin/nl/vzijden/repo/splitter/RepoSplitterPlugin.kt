package nl.vzijden.repo.splitter

import org.gradle.api.Plugin
import org.gradle.api.Project


class RepoSplitterPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.tasks.register("split", SplitModulesTask::class.java)
    }
}



