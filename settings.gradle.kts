pluginManagement {
    repositories {
        google()
//        mavenCentral()
        maven {
            isAllowInsecureProtocol = true
            setUrl("https://maven.aliyun.com/nexus/content/groups/public/")
        }
        maven {
            isAllowInsecureProtocol = true
            setUrl("https://maven.aliyun.com/nexus/content/repositories/jcenter")
        }
        maven {
            isAllowInsecureProtocol = true
            setUrl("http://maven.aliyun.com/nexus/content/repositories/google")
        }
        maven {
            isAllowInsecureProtocol = true
            setUrl("http://maven.aliyun.com/nexus/content/repositories/gradle-plugin")
        }

        gradlePluginPortal()
//        maven { setUrl("https://jitpack.io") }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
//        mavenCentral()
//        jcenter()
        maven {
            isAllowInsecureProtocol = true
            setUrl("https://maven.aliyun.com/nexus/content/groups/public/")
        }
        maven {
            isAllowInsecureProtocol = true
            setUrl("https://maven.aliyun.com/nexus/content/repositories/jcenter")
        }
        maven {
            isAllowInsecureProtocol = true
            setUrl("http://maven.aliyun.com/nexus/content/repositories/google")
        }
        maven {
            isAllowInsecureProtocol = true
            setUrl("http://maven.aliyun.com/nexus/content/repositories/gradle-plugin")
        }

        maven { setUrl("https://jitpack.io") }

    }
}


rootProject.name = "SunnyBeach"
include(":app")
 