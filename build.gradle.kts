plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.1"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.hcpark"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect") // 없으면 repository 생성이 안됨; reflection 쓰는 듯
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // Setup h2 database
    // spring boot 필요 - https://stackoverflow.com/questions/73548351/how-to-access-the-h2-console-for-spring-boot-without-spring-web
    runtimeOnly("com.h2database:h2:2.2.220")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa") // data.sql 실행해서 h2에 테이블 생성하기 위해 필요
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
