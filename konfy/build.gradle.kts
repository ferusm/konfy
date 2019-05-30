import tanvd.kosogor.proxy.publishJar

group = rootProject.group
version = rootProject.version

dependencies {
    compile(kotlin("stdlib"))
    compile(kotlin("reflect"))
}

publishJar {
    publication {
        artifactId = "tanvd.konfy"
    }

    bintray {
        username = "tanvd"
        repository = "tanvd.konfy"
        info {
            description = "Statically typed configurations for Kotlin"
            githubRepo = "https://github.com/TanVD/konfy"
            vcsUrl = "https://github.com/TanVD/konfy"
            labels.addAll(listOf("kotlin", "configuration", "kotlin-dsl", "konfy"))
        }
    }
}
