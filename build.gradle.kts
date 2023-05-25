plugins {
    id("java")
    kotlin("jvm") version "1.8.21"
}

group = "io.github.sinhajun"
version = "0.0.1"

repositories {
    mavenCentral()
    maven {
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT")
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.processResources {
    filesMatching("*.yml") {
        expand(
            "name" to rootProject.name,
            "version" to version
        )
    }
}

/*
1. Edit Configurations
2. Gradle 빌드툴 생성
3. run 부분에 jar copyPlugin 입력
*/
tasks.register("copyPlugin", Copy::class) {
    doFirst { println("copying built plugin ...") }

    from("build/libs/" + "Stats-0.0.1.jar") // 예 : paper-sample-0.0.1.jar
    into("CUsers/s7302/IdeaProjects/MinecraftServer/PluginServer/plugins") // 예: ~/PluginServer/plugins

    doLast { println("copied built plugin!") }
}

kotlin {
    jvmToolchain(11)
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}