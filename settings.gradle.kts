// La gestión de plugins se centraliza aquí.
pluginManagement {
    // Definimos DÓNDE buscar los plugins que necesita el proyecto.
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal() // Asegura que KSP y otros plugins se encuentren.
    }
}

// Aquí se definen los repositorios para las LIBRERÍAS (dependencias), no los plugins.
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

// Módulos incluidos en el proyecto.
rootProject.name = "BrickManager"
include(":app")
include(":domain")
include(":data")
