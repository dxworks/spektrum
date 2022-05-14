package rules.cs

def hasSomeAttribute(unitInfo, attributes) {
    return attributes.stream()
            .anyMatch(paramAttr -> unitInfo.method.attributes.stream()
                    .anyMatch(unitAttr -> unitAttr == paramAttr)
            )
}

def hasSomeUsingStatements(unitInfo, usingStatements) {
    return usingStatements.stream()
            .anyMatch(paramUsing -> unitInfo.cls.usingStatements.stream()
                    .anyMatch(unitUsing -> unitUsing == paramUsing)
            )
}

def check(unitInfo) {
    def hasUsing = hasSomeUsingStatements(unitInfo, ["Xunit"])
    def hasAttribute = hasSomeAttribute(unitInfo, ["Xunit.FactAttribute", "Xunit.TheoryAttribute"])

    return hasUsing && hasAttribute
}