dependencies {
    implementation(projects.shared)
    implementation("org.bstats:bstats-velocity:3.1.0")
    compileOnly("com.github.limework.redisbungee:RedisBungee-Velocity:0.11.0")
    compileOnly("com.velocitypowered:velocity-api:3.1.1")
    annotationProcessor("com.velocitypowered:velocity-api:3.1.1")
    compileOnly("com.github.LeonMangler:PremiumVanishAPI:2.9.0-4")
    compileOnly("net.william278:velocityscoreboardapi:1.0.1")
}

tasks.compileJava {
    options.release.set(17)
}

plugins {
    id("xyz.jpenilla.run-velocity") version "2.2.0"
}

tasks {
    shadowJar {
        dependencies {
            include(dependency("${project.group}:shared"))
        }
    }
}

velocityPlugin {
    apiVersion = "3.1.1"
    // 其他 Velocity 插件配置...
}