plugins {
  id 'java'
  id 'application'
  id 'maven-publish'
  id 'com.github.johnrengelman.shadow' version '1.2.3'
}

project.group = "\${project.defaultGroup}"
project.version = '1.0-SNAPSHOT'
sourceCompatibility = "\${project.sourceCompatibility}"
targetCompatibility = "\${project.targetCompatibility}"

mainClassName = "${_appPackage}.${_mainClass}"

dependencies {
  compile "io.vertx:vertx-core:\${project.vertxVersion}"
  compile "io.vertx:vertx-shell:\${project.vertxVersion}"
  compile "io.vertx:vertx-web:\${project.vertxVersion}"
  compile "io.vertx:vertx-auth-common:\${project.vertxVersion}"
  compile "javax.inject:javax.inject:1"
  compile "com.google.inject:guice:4.0"
}

shadowJar {
  archivesBaseName = "${_mainAppName}"
  classifier = 'app'
  mergeServiceFiles {
    include 'META-INF/services/io.vertx.core.spi.VerticleFactory'
  }
}


publishing {
  publications {
    maven(MavenPublication) {
      groupId "$_group"
      artifactId "${_mainAppName}"
      version "\${project.version}"
      from components.java
    }
  }
}