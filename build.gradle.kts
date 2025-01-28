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
    implementation("org.jetbrains.kotlin:kotlin-reflect") // 없으면 repository 생성이 안됨; reflection 쓰는 듯
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // Setup h2 database
    // h2 띄우려면 spring boot 필요 - https://stackoverflow.com/questions/73548351/how-to-access-the-h2-console-for-spring-boot-without-spring-web
    runtimeOnly("com.h2database:h2:2.2.220")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa") // data.sql 실행해서 h2에 테이블 생성하기 위해 필요
//    implementation("org.springframework.boot:spring-boot-starter") // spring-boot-starter-web에 포함됨 (Tomcat 내장서버 없음)
    implementation("org.springframework.boot:spring-boot-starter-web") // 브라우저로 h2 web console 접속하고 싶으면, Tomcat 내장서버 띄워야 접속 가능
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // Ch5 - JavaMail and abstraction MailSender
    implementation("com.sun.mail:jakarta.mail:2.0.1")
    implementation("org.springframework:spring-context-support")

    // Kotlin에서 Mockito any() 사용시 any() can be null error - https://stackoverflow.com/questions/59230041/argumentmatchers-any-must-not-be-null
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.4.0")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
