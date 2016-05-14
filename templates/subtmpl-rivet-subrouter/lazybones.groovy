import org.apache.commons.io.FileUtils
import uk.co.cacoethes.util.NameType

//println parentParams
//[
// _appPackage: com.example.foo,
// _mainAppName: foo - app,
// _mainClass: FooApp,
// _mainModule: FooAppModule,
// _group: com.example,
// _basePackage: com.example,
// _vertxVersion: 3.2. 1,
// _version: 1.0 - SNAPSHOT
// ]


def props = [:]

props._projectName = ask(
    "In Rivet, subrouters go in their own sub-project.\nPlease supply a name for the sub-project.\n\tSub project name:",
    "", "_projectName"
)

props._mountPoint = ask(
    "Mount point for this router relative to root of the main application's router. [${props._projectName}]:",
    props._projectName,
    "_mountPoint"
)

props._group = ask(
    "Define value for 'group' [${parentParams._group}]: ",
    parentParams._group, "_group"
)

def projPackage = "${props._projectName}".split("-").collect {
  def str = it[0].toLowerCase()
  if (it.size() > 1) str += it[1..-1]
  return str
}.join("_")

def proposedPackage = "${parentParams._appPackage}.${projPackage}"
props._package = ask(
    "Package name [${proposedPackage}]",
    proposedPackage,
    "_package"
)

props._version = ask(
    "Define value for 'version' [1.0-SNAPASHOT]: ",
    "1.0-SNAPSHOT",
    "_version"
)

def proposedClass = transformText(
    props._projectName,
    from: NameType.HYPHENATED,
    to: NameType.CAMEL_CASE
)
props._class = ask(
    "Class name for the Verticle [${proposedClass}].",
    proposedClass, "_mainClass"
)

//props._appPackage = "${props._basePackage}.${props._mainAppName}"


//println parentParams
//println props
//
// Process build files in place as templates
processTemplates "build.gradle", props
processTemplates "Subrouter.java", props
processTemplates "index.html", props
//processTemplates "settings.gradle", props
//processTemplates "${props._mainAppName}/build.gradle", props

// Construct and copy the main app sub-project
//processTemplates("MainApp.java", props)
//processTemplates("MainModule.java", props)

def pkgPath = props._package.replace('.' as char, '/' as char)

def filename="build.gradle"
def destFile = new File(
    projectDir,
    "${props._projectName}/${filename}"
)
destFile.parentFile.mkdirs()
FileUtils.moveFile(new File(templateDir, "build.gradle"), destFile)

filename = props._class
destFile = new File(
    projectDir,
    "${props._projectName}/src/main/java/${pkgPath}/${filename}.java"
)

destFile.parentFile.mkdirs()
FileUtils.moveFile(new File(templateDir, "Subrouter.java"), destFile)

destFile = new File(
    projectDir,
    "${props._projectName}/src/main/resources/${props._package}/index.html"
)
destFile.parentFile.mkdirs()
FileUtils.moveFile(new File(templateDir, "index.html"), destFile)


println """Sub-project ${props._projectName} created in project ${parentParams.projectDir}.

Don't forget to add the new dependency to the application build.

       compile project(\":${props._projectName}\");

and to settings.gradle

       include \"${props._projectName}\"

"""