import groovy.lang.Closure

plugins {
    kotlin("jvm")
    id("fabric-loom")
    `maven-publish`
    java
}

group = property("maven_group")!!
version = property("mod_version")!!

repositories {
    // Add repositories to retrieve artifacts from in here.
    // You should only use this when depending on other mods because
    // Loom adds the essential maven repositories to download Minecraft and libraries from automatically.
    // See https://docs.gradle.org/current/userguide/declaring_repositories.html
    // for more information about repositories.
    mavenCentral()
    maven{name = "JitPack"; setUrl("https://jitpack.io"); metadataSources { artifact() }}
    maven{name = "TerraformersMC"; setUrl("https://maven.terraformersmc.com/")}
    maven{name = "Ladysnake Libs"; setUrl("https://ladysnake.jfrog.io/artifactory/mods")}
}

dependencies {
    minecraft("com.mojang:minecraft:${property("minecraft_version")}")
    mappings("net.fabricmc:yarn:${property("yarn_mappings")}:v2")
    modImplementation("net.fabricmc:fabric-loader:${property("loader_version")}")

    modImplementation("net.fabricmc:fabric-language-kotlin:${property("fabric_kotlin_version")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${property("fabric_api_version")}")

    modImplementation("com.github.DaFuqs:Revelationary:${property("revelationary_version")}")

    //modImplementation("dev.emi:trinkets:${property("trinkets_version")}")
}

tasks {

    processResources {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        inputs.property("version", project.version)
        filesMatching("fabric.mod.json") {
            expand(mutableMapOf("version" to project.version))
        }
    }

    jar {
        from("LICENSE")
    }

    /*publishing {
        publications {
            create<MavenPublication>("mavenJava") {
                artifact(remapJar) {
                    builtBy(remapJar)
                }
                artifact(kotlinSourcesJar) {
                    builtBy(remapSourcesJar)
                }
            }
        }

        // select the repositories you want to publish to
        repositories {
            // uncomment to publish to the local maven
            // mavenLocal()
        }
    }*/

    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }

}

java {
    // Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
    // if it is present.
    // If you remove this line, sources will not be generated.
    withSourcesJar()
}


loom{

    runs {

        create("Data Generation"){
            client()
            vmArg("-Dfabric-api.datagen")
            vmArg("-Dfabric-api.datagen.output-dir=${file("src/main/generated")}")
            vmArg("-Dfabric-api.datagen.strict_validation")

            ideConfigGenerated(true)
            runDir = "build/datagen"


        }
    }
}

sourceSets{
    main{
        resources{
            srcDirs("src/main/generated", "src/main/resources")
            /*exclude{
                println(it.path)
                it.a
                file("src/main/resources/${it.path}")
                file("src/main/generated/${it.path}")
                !it.isDirectory

                false
            }*/
        }
    }
}

// configure the maven publication
