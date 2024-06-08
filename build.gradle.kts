import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `java-gradle-plugin`
    `maven-publish`
    kotlin("jvm") version "2.0.0"
}

group = "nulled"
version = "1.0"

repositories{
    mavenCentral()
    mavenLocal()
    maven { url = uri("https://raw.githubusercontent.com/MeteorLite/hosting/main/repo/") }
    maven {
        url = uri("https://maven.pkg.github.com/zeruth/logger")
        credentials {
            username = System.getenv("USERNAME")
            password = System.getenv("TOKEN")
        }
    }
}

gradlePlugin {
    plugins {
        create("injector") {
            id = "meteor.injector"
            implementationClass = "nulled.InjectorPlugin"
        }
    }
}

dependencies{
    implementation(gradleApi())
    implementation(localGroovy())

    with(libs){
        compileOnly(lombok)
        annotationProcessor(lombok)
        implementation(gson)
        implementation(java.inject)
        implementation(asm)
        implementation(asm.util)
        implementation(guava)
        implementation(jopt.simple)
        implementation(annotations)
        implementation(fernflower)
    }
    implementation("nulled:logger:1.0")
    implementation("nulled:annotations:1.0")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

publishing {
    publications {
        create<MavenPublication>("java") {
            from(components["java"])
        }
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_11)
    }
}