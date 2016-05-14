import org.apache.commons.io.FileUtils
import uk.co.cacoethes.util.NameType

def props = [:]

props._group = ask(
    "Define value for 'group' [com.example]: ",
    "com.example", "_group"
)

props._basePackage = ask(
    "Base Package name for project [${props._group}]",
    props._group,
    "_basePackage"
)

props._version = ask(
    "Define value for 'version' [1.0-SNAPASHOT]: ",
    "1.0-SNAPSHOT",
    "_version"
)
props._vertxVersion = ask(
    "Define value for 'vertxVersion' [3.2.1]: ",
    "3.2.1",
    "_vertxVersion"
)

// The name of the projectDir is the basis for the
// package name and the name of the main app.
//  dir:  my-foo
//  pkg proposal:  _basePackage.my_foo
def appPkg = ("$projectDir".split("/")[-1]).split("-").collect {
  def str = it[0].toLowerCase()
  if (it.size() > 1) str += it[1..-1]
  return str
}.join("_")

props._mainAppName = ask(
    "Project name for the main app [${appPkg}-app].",
    "${appPkg}-app", "_mainAppName"
)

// The name of the main app project is the basis for the name of the main
// class.
// projectDir:  my-foo
// main class:  MyFoo

def proposedMainClass = transformText(
    props._mainAppName,
    from: NameType.HYPHENATED,
    to: NameType.CAMEL_CASE
)

props._mainClass = ask(
    "Class name for the main app [${proposedMainClass}].",
    proposedMainClass, "_mainClass"
)

props._mainModule = ask(
    "Class name for the main module [${props._mainClass}Module].",
    "${props._mainClass}Module", "_mainModule"
)

def proposedFullAppPackage = "${props._basePackage}.${appPkg}"
props._appPackage = ask(
    "Package name for main application [${proposedFullAppPackage}].",
    proposedFullAppPackage, "_appPackage"
)

// Process build files in place as templates
processTemplates "build.gradle", props
processTemplates "settings.gradle", props

processTemplates("MainApp.java", props)
processTemplates("MainModule.java", props)
processTemplates("buildApp.gradle", props)
processTemplates("vertx-default-jul-logging.properties", props)

def pkgPath = props._appPackage.replace('.' as char, '/' as char)

def filename = props._mainClass + ".java"
def destFile = new File(
    projectDir,
    "${props._mainAppName}/src/main/java/${pkgPath}/${filename}"
)
destFile.parentFile.mkdirs()
FileUtils.moveFile(new File(templateDir, "MainApp.java"), destFile)

filename = props._mainModule + ".java"
destFile = new File(
    projectDir,
    "${props._mainAppName}/src/main/java/${pkgPath}/${filename}"
)
FileUtils.moveFile(new File(templateDir, "MainModule.java"), destFile)

filename = "build.gradle"
destFile = new File(
    projectDir,
    "${props._mainAppName}/${filename}"
)
FileUtils.moveFile(new File(templateDir, "buildApp.gradle"), destFile)

filename = "vertx-default-jul-logging.properties"
destFile = new File(
    projectDir,
    "${props._mainAppName}/src/main/resources/${filename}"
)
destFile.parentFile.mkdirs()
FileUtils.moveFile(new File(templateDir, "vertx-default-jul-logging.properties"), destFile)
