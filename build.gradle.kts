plugins {
    kotlin("multiplatform") version "1.7.20"
    jacoco
}

jacoco { toolVersion = "0.8.5" }

group = "com.xingpeds"

version = "1.0-SNAPSHOT"

repositories { mavenCentral() }

kotlin {
    jvm {
        compilations.all { kotlinOptions.jvmTarget = "1.8" }
        withJava()
        testRuns["test"].executionTask.configure { useJUnitPlatform() }
    }
    //    js(BOTH) {
    //
    //        //
    //        browser {
    //            //
    //            commonWebpackConfig { cssSupport.enabled = true }
    //        }
    //    }

    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget =
        when {
            hostOs == "Mac OS X" -> macosX64("native")
            hostOs == "Linux" -> linuxX64("native")
            isMingwX64 -> mingwX64("native")
            else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
        }

    sourceSets {
        val commonMain by getting {
            dependencies { api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4") }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:+")
                implementation("io.kotest:kotest-property:5.5.4")
            }
        }
        val jvmMain by getting
        val jvmTest by getting {
            dependencies { implementation("io.kotest:kotest-runner-junit5:5.5.4") }
        }
        //        val jsMain by getting
        //        val jsTest by getting
        val nativeMain by getting
        val nativeTest by getting
    }
}
