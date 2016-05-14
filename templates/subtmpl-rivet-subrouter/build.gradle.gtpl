plugins {
  id 'java'
  id 'maven'
}

project.group = "${_group}"
project.version = "${_version}"

sourceCompatibility = '1.8'

dependencies {
  compile "io.vertx:vertx-core:\${project.vertxVersion}"
  compile "io.vertx:vertx-web:\${project.vertxVersion}"
  compile "javax.inject:javax.inject:1"
  compile "org.kohsuke.metainf-services:metainf-services:1.5"
}

