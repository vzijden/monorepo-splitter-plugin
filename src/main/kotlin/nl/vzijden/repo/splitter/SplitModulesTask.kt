package nl.vzijden.repo.splitter

import nl.vzijden.repo.splitter.converter.convertProject
import nl.vzijden.repo.splitter.writer.writeBuildFile
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option
import java.io.File

open class SplitModulesTask : DefaultTask() {
    @set: Option(
            option = "outputProjectDir", description = "The dir where the sub projects are extracted to",
    )
    @get: Input
    var outputProjectDir: String = ""


    @TaskAction
    fun split() {
        // Get all Gradle sub modules.
        project.childProjects.forEach { (name, subModule) ->
            val newProjectDirFile = createNewProjectDir(name)

            copyModuleSourceFiles(newProjectDirFile, subModule)

            copyGradleWrapperFromRootProject(newProjectDirFile)

            createNewGradleBuildFile(newProjectDirFile, subModule)

        }
        logger.lifecycle("Extracted child projects to $outputProjectDir")
    }

    private fun copyModuleSourceFiles(modulePath: File, subProject: Project) {
        val javaPluginExtension = subProject.extensions.getByType(JavaPluginExtension::class.java)

        val mainSourceSet = javaPluginExtension.sourceSets.named("main").get()
        mainSourceSet.allSource
                .asFileTree
                .visit { file ->
                    file.copyTo(modulePath.resolve("src/main/java/" + file.relativePath.pathString))
                }


        val testSourceSet = javaPluginExtension.sourceSets.named("test").get()
        testSourceSet.allSource
                .asFileTree
                .visit { file ->
                    file.copyTo(modulePath.resolve("src/test/java/" + file.relativePath.pathString))
                }

    }

    private fun createNewProjectDir(name: String): File {
        // Create the directory for the new project in the output dir.
        return File(outputProjectDir, name)
    }

    private fun createNewGradleBuildFile(newProjectPath: File, subModule: Project) {
        convertProject(subModule)
        val gradleFileString = writeBuildFile(subModule)

        val gradleFilePath = newProjectPath.resolve("build.gradle.kts")
        gradleFilePath.writeText(gradleFileString)

        // IntelliJ IDEA requires a Gradle settings file to recognize the project as a Gradle project.
        newProjectPath.resolve("settings.gradle.kts").createNewFile()
    }

    private fun copyGradleWrapperFromRootProject(modulePath: File) {
        val gradlewTargetFile = modulePath.resolve("gradlew")
        project.file("gradlew").copyTo(gradlewTargetFile)
        gradlewTargetFile.setExecutable(true)

        project.file("gradle").copyRecursively(modulePath.resolve("gradle"))
    }


}