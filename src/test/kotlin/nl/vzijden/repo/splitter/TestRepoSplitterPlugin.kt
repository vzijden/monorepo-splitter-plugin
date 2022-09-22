package nl.vzijden.repo.splitter

import org.assertj.core.api.Assertions.assertThat
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.exists
import kotlin.io.path.isDirectory
import kotlin.io.path.readText

class TestRepoSplitterPlugin {
    private lateinit var outputDir: Path

    @BeforeEach
    fun createTempDir() {
        outputDir = Files.createTempDirectory("TestRepoSplitterPlugin")
    }

    @Test
    fun testSplitAndBuildModules() {
        // Given
        val gradleRunner = initGradleRunner()

        // When
        gradleRunner
            .withArguments("split", "--outputProjectDir", outputDir.toString())
            .build()

        // then
        validateNewGradleProject(outputDir.resolve("module4"))
        validateNewGradleProject(outputDir.resolve("module3"))
        validateNewGradleProject(outputDir.resolve("module2"))
        validateNewGradleProject(outputDir.resolve("module1"))
    }

    private fun validateNewGradleProject(projectPath: Path) {
        assertTrue(projectPath.resolve("gradle").isDirectory())
        assertTrue(projectPath.resolve("gradlew").exists())
        assertTrue(projectPath.resolve("src/main/java").exists())

        assertFalse(projectPath.resolve("build").exists())

        val buildResult = buildGradleProject(projectPath)
        assertThat(buildResult.tasks).noneMatch { it.outcome == TaskOutcome.FAILED }
        assertTrue(projectPath.resolve("build").exists())
    }

    private fun initGradleRunner(): GradleRunner {
        return GradleRunner.create()
            .withProjectDir(File(javaClass.getResource("/big-mono-repo")!!.file))
            .withPluginClasspath()
            .withDebug(true)
            .forwardOutput()
    }


    private fun buildGradleProject(buildDir: Path): BuildResult {
        try {
            return GradleRunner.create()
                .withProjectDir(buildDir.toFile())
                .withArguments("compileJava", "publishToMavenLocal")
                .build()
        } catch (e: Exception) {
            fail(
                """
                    Failed to build project $buildDir with gradleFile:
                    ${buildDir.resolve("build.gradle.kts").readText()}
                    exception: $e""".trimIndent()
            )
        }
    }
}