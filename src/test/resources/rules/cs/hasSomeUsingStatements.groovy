def hasSomeUsingStatements(unitInfo, usingStatements) {
    return usingStatements.stream()
            .anyMatch(paramUsing -> unitInfo.cls.usingStatements.stream()
                    .anyMatch(unitUsing -> unitUsing == paramUsing)
            )
}