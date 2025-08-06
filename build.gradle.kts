plugins {
    java
    id("org.springframework.boot") version "3.5.3"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.bookshop"
version = "0.0.1-SNAPSHOT"
description = "Provides functionality for dispatching orders."
extra.set("otelVersion", "1.33.3")
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {

    implementation("org.springframework.cloud:spring-cloud-starter-function-web")
    //implementation ("org.springframework.boot:spring-boot-starter")
    //implementation ("org.springframework.cloud:spring-cloud-function-context")

    implementation ("org.springframework.cloud:spring-cloud-starter-config")
    implementation ("org.springframework.cloud:spring-cloud-stream-binder-rabbit")
    implementation ("org.springframework.retry:spring-retry")
    implementation ("org.springframework.boot:spring-boot-starter-actuator")
    implementation ("org.springframework.boot:spring-boot-starter-webflux")

    runtimeOnly ("io.micrometer:micrometer-registry-prometheus")
    runtimeOnly ("io.opentelemetry.javaagent:opentelemetry-javaagent:${property("otelVersion")}")

    testImplementation("org.assertj:assertj-core")
    testImplementation ("org.springframework.boot:spring-boot-starter-webflux")
    testImplementation("org.springframework.cloud:spring-cloud-function-context")
    testImplementation ("org.springframework.boot:spring-boot-starter-test")
    testImplementation ("io.projectreactor:reactor-test")
    testImplementation("org.springframework.cloud:spring-cloud-stream-test-binder")

}


dependencyManagement {// 책에서는 없으나... 클라우드 디펜던시가 필요했음
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:3.3.1")
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2023.0.1")

    }
}


tasks.bootBuildImage {
    builder.set("paketobuildpacks/builder-jammy-java-tiny:0.0.46")
    //imagePlatform.set("linux/arm64")
    imageName.set(project.name)
    //imageName.set("ghcr.io/kingstree/${project.name}:latest")   // ★ 레지스트리·계정 포함
    environment.put("BP_JVM_VERSION", "17")

    docker {
        publishRegistry {
            username = project.findProperty("registryFUsername") as String?
            password = project.findProperty("registryToken") as String?
            url = project.findProperty("registryUrl") as String?
        }
    }
}
tasks.withType<Test> {
    useJUnitPlatform()
}
