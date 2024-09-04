import com.shakelang.util.changelog.public
import com.shakelang.util.changelog.resolveVersion
import conventions.projectGroup

plugins {
    id("conventions.dokka")
    `kotlin-dsl`
}

repositories {
    maven {
        url = uri("https://clojars.org/repo")
    }
}

group = projectGroup("util.embed")
version = resolveVersion()
description = "Gradle plugin for embedding resources into kotlin source"
public = true

val projectName = name

kotlin {
    dependencies {
        implementation(project(":util:embed:api"))
        implementation(gradleApi())
        implementation("com.squareup:kotlinpoet:1.16.0")

        // compileOnly kotlin gradle plugins
        compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:2.0.20")
    }
}

sourceSets {
    main {
        java.srcDirs("src/main/kotlin", "src/main/java")
    }
    test {
        java.srcDirs("src/test/kotlin", "src/test/java")
    }
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(kotlin.sourceSets.main.get().kotlin)
}

publishing {
    publications {
        // kotlin plugin
        withType<MavenPublication> {
            groupId = project.group.toString()
            artifactId = projectName
            version = project.version.toString()
        }
    }
}

kotlin {
    sourceSets["main"].kotlin.srcDirs("src/main/kotlin")
}
