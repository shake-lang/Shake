// import com.shakelang.shake.sarifmerge.SarifMerge
import com.shakelang.util.changelog.Changelog
import com.shakelang.util.changelog.tasks.VersionTask
import com.shakelang.util.sarifmerge.SarifMerge
import io.codearte.gradle.nexus.NexusStagingExtension
import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.dokka.DokkaConfiguration.Visibility
import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.dokka.gradle.DokkaTaskPartial
import org.jetbrains.dokka.versioning.VersioningConfiguration
import org.jetbrains.dokka.versioning.VersioningPlugin
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType
import java.net.URL

group = "com.shakelang.shake"
version = "0.1.0"
description = "Shake"

buildscript {
    dependencies {
        classpath("org.jetbrains.dokka:versioning-plugin:1.9.10")
    }
}

dependencies {
//    kover(project(":util:changelog"))
    kover(project(":util:colorlib"))
    kover(project(":util:common-io"))
    kover(project(":util:environment"))
    kover(project(":util:jvmlib"))
    kover(project(":util:logger"))
    kover(project(":util:markdown"))
    kover(project(":util:parseutils"))
    kover(project(":util:pointers"))
    kover(project(":util:primitives"))
    kover(project(":util:shason"))
    kover(project(":util:testlib"))

    kover(project(":shake:compiler:lexer"))
    kover(project(":shake:compiler:parser"))
    kover(project(":shake:compiler:shakelib"))

    kover(project(":shake:bytecode:conventions"))
    kover(project(":shake:bytecode:utils"))
    kover(project(":shake:bytecode:interpreter"))
    kover(project(":shake:bytecode:generator"))

    kover(project(":shake:compiler:jsgenerator"))
    kover(project(":shake:compiler:processor"))
    kover(project(":shake:shasambly:shasambly"))
    kover(project(":shake:shasambly:shasp"))
    kover(project(":shake:shasambly:shastools"))
//    kover(project(":shake:shasambly:java-dist"))
}

repositories {
    mavenLocal()
    mavenCentral()
    gradlePluginPortal()
}

plugins {
    id("org.jetbrains.dokka")
    id("org.jetbrains.kotlinx.kover")
    id("org.jlleitschuh.gradle.ktlint") version "11.6.1"
    id("io.gitlab.arturbosch.detekt") version "1.23.4"
    id("io.github.gradle-nexus.publish-plugin") version "1.3.0"
}

apply<Changelog>()
apply<SarifMerge>()
apply(plugin = "io.codearte.nexus-staging")

nexusPublishing {
    repositories {
        sonatype {

//            stagingProfileId.set("com.shakelang")
            val _username =
                System.getenv("GRADLE_SONATYPE_USERNAME") ?: project.properties["sonatype.username"] as String?
            val _password =
                System.getenv("GRADLE_SONATYPE_PASSWORD") ?: project.properties["sonatype.password"] as String?

            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
            username.set(_username)
            password.set(_password)
        }
    }
}

val dokkaPlugin by configurations
tasks.register<org.jetbrains.dokka.gradle.DokkaMultiModuleTask>("dokkaRoot") {
    dependencies {
        dokkaPlugin("org.jetbrains.dokka:all-modules-page-plugin:1.9.10")
    }
    outputDirectory.set(file("$buildDir/docs"))
    addChildTasks(childProjects.values, "dokkaHtmlPartial")
}

tasks.withType<VersionTask>().configureEach {
    this.tagFormat {
        "release${it.project.path.replace(":", "/")}/v${it.version}"
    }
}

val currentVersion = "1.0"
val previousVersionsDirectory = project.rootProject.projectDir.resolve("previousDocVersions").invariantSeparatorsPath

val styleSheet = file("./assets/dokka-style.css").absoluteFile

tasks.dokkaHtmlMultiModule {
    pluginConfiguration<VersioningPlugin, VersioningConfiguration> {
        version = currentVersion
        olderVersionsDir = file(previousVersionsDirectory)
    }
}

// apply(plugin = "com.shakelang.util.changelog.Changelog")
detekt {
    toolVersion = "1.23.3"
    config.setFrom(rootProject.file("config/detekt/detekt.yml"))
    buildUponDefaultConfig = true
    ignoreFailures = true
}

// Kotlin DSL
tasks.withType<Detekt>().configureEach {
    reports {
        xml.required.set(true)
        html.required.set(true)
        txt.required.set(true)
        sarif.required.set(true)
        md.required.set(true)
    }
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint") // Version should be inherited from parent
    apply(plugin = "io.gitlab.arturbosch.detekt") // Version should be inherited from parent
    repositories {
        mavenLocal()
        mavenCentral()
    }

    ktlint {
        ignoreFailures.set(true)
        reporters {
            reporter(ReporterType.PLAIN)
            reporter(ReporterType.HTML)
            reporter(ReporterType.SARIF)
        }
    }

    detekt {
        toolVersion = "1.23.3"
        config.setFrom(rootProject.file("config/detekt/detekt.yml"))
        buildUponDefaultConfig = true
        ignoreFailures = true
    }

    // Kotlin DSL
    tasks.withType<Detekt>().configureEach {
        reports {
            xml.required.set(true)
            html.required.set(true)
            txt.required.set(true)
            sarif.required.set(true)
            md.required.set(true)
        }
    }

    afterEvaluate {

        tasks.withType<DokkaTask>().configureEach {
            pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
                customStyleSheets = listOf(styleSheet)
            }
        }

        tasks.withType<DokkaTaskPartial>().configureEach {

            moduleName.set(project.group.toString() + "." + project.name.toString())
            moduleVersion.set(project.version.toString())

            pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
//                customAssets = listOf(file("./assets/docs-logo.svg"))
                customStyleSheets = listOf(file("./assets/dokka-style.css").absoluteFile)
            }

            dokkaSourceSets.configureEach {
                documentedVisibilities.set(
                    setOf(
                        Visibility.PUBLIC,
                        Visibility.PROTECTED
                    )
                )

                // Read docs for more details: https://kotlinlang.org/docs/dokka-gradle.html#source-link-configuration
                sourceLink {
                    val exampleDir =
                        "https://github.com/Kotlin/dokka/tree/master/examples/gradle/dokka-multimodule-example"

                    localDirectory.set(rootProject.projectDir)
                    remoteUrl.set(URL("$exampleDir"))
                    remoteLineSuffix.set("#L")
                }
            }
        }
    }
}

ktlint {
    ignoreFailures.set(true)
    reporters {
        reporter(ReporterType.PLAIN)
        reporter(ReporterType.HTML)
        reporter(ReporterType.SARIF)
    }
}

tasks.create("lint") {
    group = "verification"
    description = "Runs ktlintCheck on all subprojects and copies the reports to build directory"
    dependsOn("ktlintCheck")
    subprojects.forEach {
        dependsOn("${it.path}:ktlintCheck")
    }

    doLast {
        println("Ktlint reports copied to ${rootProject.buildDir}/reports/ktlint/ktlintKotlinScriptCheck/")
    }
}

repositories {
    mavenLocal()
    mavenCentral()
}

fun findDokkaHtmlProjects(): List<Project> =
    subprojects.filter {
        !it.tasks.none { task -> "dokkaHtml" == task.name }
    }

fun findDokkaGfmProjects(): List<Project> =
    subprojects.filter {
        !it.tasks.none { task -> "dokkaGfm" == task.name }
    }

tasks.dokkaHtml.configure {

    outputDirectory.set(buildDir.resolve("docs/html"))

    dependsOn("copyDokkaHtml")

    doFirst {
        val dokkaProjects = findDokkaHtmlProjects()

        dokkaProjects.forEach {
            println("Copying html documentation of module ${it.name}...")
            copy {
                from(fileTree("${it.name}/build/docs/html"))
                into(layout.buildDirectory.dir("docs/html/modules/${it.name}"))
            }
        }

        dokkaSourceSets {
            dokkaProjects.forEach {
                dokkaSourceSets.create(it.name) {
                    sourceRoots.setFrom("${it.name}/src/commonMain")
                }
            }
        }
    }
}

tasks.dokkaGfm.configure {

    outputDirectory.set(buildDir.resolve("docs/markdown"))

    dependsOn("copyDokkaGfm")

    doFirst {
        val dokkaProjects = findDokkaGfmProjects()

        dokkaProjects.forEach {
            println("Copying markdown documentation of module ${it.name}...")
            copy {
                from(fileTree("${it.name}/build/docs/html"))
                into(layout.buildDirectory.dir("docs/html/modules/${it.name}"))
            }
        }

        dokkaSourceSets {
            dokkaProjects.forEach {
                dokkaSourceSets.create(it.name) {
                    sourceRoots.setFrom("${it.name}/src/commonMain")
                }
            }
        }
    }
}

tasks.register("dokka") {
    group = "documentation"
    dependsOn("dokkaHtml", "dokkaGfm")
}

tasks.register<TestReport>("genReport") {
    group = "verification"
    val testTasks = allprojects.flatMap { it.tasks.withType(Test::class) }
    dependsOn(testTasks)
    destinationDirectory.set(file("${layout.buildDirectory}/reports/tests"))
    testResults.from(testTasks)
}

tasks.register("copyDokkaGfm") {
    group = "documentation"
    findDokkaGfmProjects().forEach {
        dependsOn("${it.path}:dokkaGfm")
    }
}

tasks.register("copyDokkaHtml") {
    group = "documentation"
    findDokkaHtmlProjects().forEach {
        dependsOn("${it.path}:dokkaHtml")
    }
}

tasks.named("closeAndReleaseRepository") { group = "publishing" }
tasks.named("closeRepository") { group = "publishing" }
tasks.named("releaseRepository") { group = "publishing" }

extensions.getByType<NexusStagingExtension>().apply {
    serverUrl =
        "https://s01.oss.sonatype.org/service/local/" // required only for projects registered in Sonatype after 2021-02-24
    packageGroup = "com.shakelang" // optional if packageGroup == project.getGroup()

    username = System.getenv("GRADLE_SONATYPE_USERNAME") ?: project.properties["sonatype.username"] as String?
    password = System.getenv("GRADLE_SONATYPE_PASSWORD") ?: project.properties["sonatype.password"] as String?

    stagingProfileId = "com.shakelang"
}

val testAggregate = tasks.register<TestReport>("testAggregate") {
    group = "verification"
//
//    subprojects.forEach {
//        if (it.tasks.none { task -> "allTests" == task.name }) return@forEach
//        dependsOn("${it.path}:allTests")
//    }
    destinationDirectory.set(file("$buildDir/reports/tests/aggregate"))
    testResults.from(subprojects.flatMap { it.tasks.withType<Test>() })
}

afterEvaluate {
    tasks.withType<DokkaTaskPartial>().configureEach {
        pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
//                customAssets = listOf(file("./assets/docs-logo.svg"))
            customStyleSheets = listOf(styleSheet)
        }
    }
}
