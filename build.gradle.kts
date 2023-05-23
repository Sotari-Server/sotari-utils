import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import java.net.URI

plugins {
    id("java")
    id("com.github.johnrengelman.shadow").version("8.1.1")
    id("application")
}

group = "fr.knsrhuseyin.sotari.minecraft_launcher"
version = "1.0"

repositories {
    mavenCentral()
    maven {
        url = URI("https://litarvan.github.io/maven")
    }
}

dependencies {
    implementation("fr.litarvan:openauth:1.1.4")
    implementation("fr.flowarg:openlauncherlib:3.2.6")
    implementation("com.google.code.gson:gson:2.10.1")
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("SotariMinecraftLauncher")
        mergeServiceFiles()
        application { mainClass.set("fr.knsrhuseyin.sotari.minecraft_launcher.Main") }
        manifest {
            attributes(mapOf("Main-Class" to "fr.knsrhuseyin.sotari.minecraft_launcher.Main"))
        }
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}

