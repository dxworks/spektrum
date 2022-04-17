def check(unitInfo) {
    GroovyShell shell = new GroovyShell()
    def print_helper = shell.parse(new File("rules/print_helper.groovy"))

    print_helper.invokeMethod("print_some_shit", null)

    println unitInfo.cls.name

    return true
}