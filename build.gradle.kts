import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `java-gradle-plugin`
    `maven-publish`
    kotlin("jvm") version "2.0.0"
}

group = "nulled"
version = "1.5"

repositories{
    mavenCentral()
    mavenLocal()
    maven { url = uri("https://raw.githubusercontent.com/MeteorLite/hosting/main/repo/") }
    maven { url = uri("https://raw.githubusercontent.com/zeruth/repo/main/") }
}

gradlePlugin {
    plugins {
        create("injector") {
            id = "nulled.injector"
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
    implementation("nulled:logger:1.2")
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

publishing {
    repositories {
        maven {
            val propjectDIr = project.layout.projectDirectory
            url = uri("file://$propjectDIr/.nulled-repo")
        }
    }
    publications {
        register<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}