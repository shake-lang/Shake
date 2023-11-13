import com.shakelang.shake.util.changelog.resolveVersion
import conventions.dependencies
import conventions.projectGroup

plugins {
    id("conventions.all")
    id("conventions.publishing")
}

group = projectGroup("shasambly")
version = resolveVersion()
description = "Shake's own bytecode format interpreter"

kotlin {
    dependencies {
        implementation(project(":util:parseutils"))
        implementation(project(":util:common-io"))
        implementation(project(":util:primitives"))
        testImplementation(kotlin("test"))
    }
}

val projectName = name
tasks.named<Jar>("jvmJar") {
    archiveBaseName.set("shake-$projectName")
}
tasks.named<Jar>("jsJar") {
    archiveBaseName.set("shake-$projectName")
}
