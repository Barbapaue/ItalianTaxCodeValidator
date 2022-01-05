plugins {
    kotlin("jvm") version "1.6.10"
    `java-library`
    `maven-publish`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testImplementation(kotlin("test"))
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useKotlinTest()
            useJUnitJupiter()
        }
    }
}

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "it.paolopasianot.italiantaxcodevalidator"
            artifactId = "library"
            version = "1.0.2"

            from(components["java"])
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
            pom {
                name.set("Italian Tax Code Validator")
                description.set("This small library was developed to validate Italian tax codes.")
                url.set("https://github.com/Barbapaue/ItalianTaxCodeValidator")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("paolopasianot")
                        name.set("Paolo Pasianot")
                        email.set("paolo.pasianot@icloud.com")
                    }
                }
            }
        }
    }
}
