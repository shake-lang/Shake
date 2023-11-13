import com.shakelang.shake.util.changelog.resolveVersion
import conventions.dependencies
import conventions.projectGroup

group = projectGroup("util.jvmlib")
version = resolveVersion()
description = "A library for jvm stuff in java"

plugins {
    id("conventions.all")
    id("conventions.publishing")
}

val javaCompilationOutputDir = "src/commonTest/resources/classes/java"

val javaCompilations = (6..21).map { it.toString() }

// Create java compilations source sets
sourceSets {
    javaCompilations.forEach {
        val javaCompilation = create("java$it") {
            java.srcDir("src/resourceSources/java")
            resources.srcDir("src/resourceSources/resources")
        }
    }
}

// Create java compilations tasks
javaCompilations.forEach {
    tasks.register("compileJava$it", JavaCompile::class.java) {
        group = "test-resource-build"
        source = sourceSets["java$it"].java
        classpath = sourceSets["java$it"].compileClasspath
        destinationDirectory.set(file("$javaCompilationOutputDir/java$it"))
    }
}

tasks.register("compileAllJava") {
    group = "test-resource-build"
    javaCompilations.forEach {
        dependsOn("compileJava$it")
    }
}

kotlin {
    dependencies {
        implementation(project(":util:parseutils"))
        implementation(project(":util:shason"))
        implementation(project(":util:common-io"))
        implementation(project(":util:primitives"))
        testImplementation(kotlin("test"))
    }
}
