import com.shakelang.shake.util.changelog.resolveVersion
import conventions.dependencies
import conventions.projectGroup
import conventions.useKotest

group = projectGroup("bytecode")
version = resolveVersion()
description = "Generator for Shake Bytecode"
java.sourceCompatibility = JavaVersion.VERSION_1_8

plugins {
    id("conventions.all")
    id("conventions.publishing")
}

kotlin {
    dependencies {
        implementation(project(":util:pointers"))
        implementation(project(":util:primitives"))
        implementation(project(":util:common-io"))
        implementation(project(":shake:bytecode:conventions"))
        implementation(project(":shake:bytecode:utils"))
        implementation(project(":shake:compiler:processor"))
        testImplementation(project(":util:testlib"))
    }
}

useKotest()
