def check(unitInfo) {
    GroovyShell shell = new GroovyShell()
    def attribute = shell.parse(new File("rules/cs/hasSomeAttribute.groovy"))
    def usingStatements = shell.parse(new File("rules/cs/hasSomeUsingStatements.groovy"))

    def hasUsing = usingStatements.hasSomeUsingStatements(unitInfo, ["Xunit"])
    def hasAttribute = attribute.hasSomeAttribute(unitInfo, ["Xunit.FactAttribute", "Xunit.TheoryAttribute"])

    return hasUsing && hasAttribute
}