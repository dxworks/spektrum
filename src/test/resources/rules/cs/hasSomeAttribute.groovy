def hasSomeAttribute(unitInfo, attributes) {
    return attributes.stream()
            .anyMatch(paramAttr -> unitInfo.method.attributes.stream()
                    .anyMatch(unitAttr -> unitAttr == paramAttr)
            )
}