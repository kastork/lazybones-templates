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

props._mainClass = ask(
    "Class name for the main app [App].",
    "App", "_mainClass"
)

props._kotlinVersion = ask(
    "Define value for 'kotlinVersion' [1.0.2]: ",
    "1.0.2",
    "_kotlinVersion"
)

props._tornadofxVersion = ask(
    "Define value for 'tornadofxVersion' [1.4.3]: ",
    '1.4.3',
    "_tornadofxVersion"
)

// Process build files in place as templates
processTemplates "build.gradle", props
// processTemplates "settings.gradle", props

def pkgPath = props._basePackage.replace('.' as char, '/' as char)

destFile = new File(projectDir, "src/main/kotlin/${pkgPath}/.retain")
destFile.parentFile.mkdirs()

// filename = "vertx-default-jul-logging.properties"
// destFile = new File(
//     projectDir,
//     "${props._mainAppName}/src/main/resources/${filename}"
// )
// destFile.parentFile.mkdirs()
// FileUtils.moveFile(new File(templateDir, "vertx-default-jul-logging.properties"), destFile)
