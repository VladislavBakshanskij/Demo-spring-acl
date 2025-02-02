plugins {
	java
	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
	id("org.liquibase.gradle") version "2.2.2"
	id("nu.studer.jooq") version "9.0"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(23)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-jooq")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-docker-compose")
	runtimeOnly("org.postgresql:postgresql")
	implementation("org.liquibase:liquibase-core")
	liquibaseRuntime("org.liquibase:liquibase-core")
	liquibaseRuntime("org.postgresql:postgresql")
	jooqGenerator("org.postgresql:postgresql")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

liquibase {
	activities {
		register("main") {
			arguments = mapOf(
				Pair("changelogFile", "db/changelog/db.changelog-master.xml"),
				Pair("url", "jdbc:postgresql://localhost:5432/demo"),
				Pair("username", "postgres"),
				Pair("password", "password"),
				Pair("searchPath", "./src/main/resources")
			)
		}
	}
}

jooq {
	configurations {
		register("main") {
			generationTool {

			}
		}
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
