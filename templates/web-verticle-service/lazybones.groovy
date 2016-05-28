import uk.co.cacoethes.util.NameType


def props = [:]

String defaultPackage(String groupName, String svcName)
{
  String pkg = transformText(
      svcName,
      from: NameType.HYPHENATED,
      to: NameType.HYPHENATED
  )
  "${groupName}.${pkg}"
}

props.serviceName = ask(
    "Name of the sub-route [example-route]: ",
    "example-route",
    "serviceName"
)

props.group = ask("Define value for 'group' [com.zenuevo]: ", "com.zenuevo", "group")

props.package = ask(
    "Package name [${defaultPackage(props.group,props.serviceName)}]", "${defaultPackage(props.group,props.serviceName)}",
    "package"
)
props.version = ask(
    "Define value for 'version' [1.0-SNAPASHOT]: ",
    "1.0-SNAPSHOT",
    "version"
)
props.vertxVersion = ask(
    "Define value for 'vertxVersion' [3.2.1]: ",
    "3.2.1",
    "vertxVersion"
)


processTemplates "build.gradle", props