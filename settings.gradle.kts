rootProject.name = "shake"
// include("cli")
// include("jvm")
include(":util:colorlib")
include(":util:common-io")
include(":util:environment")
include(":util:jvmlib")
include(":util:logger")
include(":util:parseutils")
include(":util:primitives")
include(":util:shason")
include(":util:testlib")

include(":shake:compiler:shakelib")
include(":shake:compiler:lexer")
include(":shake:compiler:parser")

// include(":shake:compiler:interpreter")
include(":shake:compiler:jsgenerator")
include(":shake:compiler:processor")
include(":shake:shasambly:shastools")
include(":shake:shasambly:shasambly")
include(":shake:shasambly:shasp")
include(":shake:shasambly:java-dist")
// include(":cli")
// include(":browser")
// include(":jvm-executable")

plugins {
    id("com.gradle.enterprise") version("3.15")
}

gradleEnterprise {
    if (System.getenv("CI") != null) {
        buildScan {
            publishAlways()
            termsOfServiceUrl = "https://gradle.com/terms-of-service"
            termsOfServiceAgree = "yes"
        }
    }
}
