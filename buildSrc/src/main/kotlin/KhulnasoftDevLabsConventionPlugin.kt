package com.khulnasoft.devlabs

import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.springframework.boot.gradle.plugin.SpringBootPlugin

class KhulnasoftDevLabsConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            // Apply common plugins
            pluginManager.apply("java")
            pluginManager.apply("org.jetbrains.kotlin.jvm")
            pluginManager.apply(SpringBootPlugin::class.java)
            pluginManager.apply("io.spring.dependency-management")
            pluginManager.apply("idea")
            pluginManager.apply("com.diffplug.spotless")

            // Configure Java compatibility
            configureJava(this)

            // Configure Spotless for code formatting
            configureSpotless()

            // Configure repositories
            configureRepositories()
        }
    }

    private fun Project.configureJava(project: Project) {
        java {
            sourceCompatibility = JavaVersion.VERSION_21
            targetCompatibility = JavaVersion.VERSION_21
        }
        tasks.withType<JavaCompile> {
            options.encoding = "UTF-8"
        }
    }

    private fun Project.configureSpotless() {
        configure<SpotlessExtension> {
            java {
                googleJavaFormat("1.11.0")
                removeUnusedImports()
                importOrder("java", "javax", "")
                trimTrailingWhitespace()
                endWithNewline()
            }
            kotlin {
                target("**/*.kt")
                ktfmt("0.37")
                trimTrailingWhitespace()
                endWithNewline()
            }
            kotlinGradle {
                target("**/*.gradle.kts")
                ktfmt("0.37")
                trimTrailingWhitespace()
                endWithNewline()
            }
            format("misc") {
                target(
                    "**/*.adoc",
                    "**/*.fragments",
                    "**/*.md",
                    "**/.gitignore",
                    "**/.yaml-lint"
                )
                trimTrailingWhitespace()
                endWithNewline()
            }
        }
    }

    private fun Project.configureRepositories() {
        repositories {
            mavenCentral()
            gradlePluginPortal()
        }
    }
}

// Extension function to apply the convention plugin
fun Project.khulnasoftDevLabs() {
    pluginManager.apply("com.khulnasoft.devlabs.convention")
}
