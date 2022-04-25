package me.drbaxr.spektrum.flexible.adapters.cs.model.external

data class ImportModel(
    val solutions: Set<SolutionCS>,
    val projects: Set<ProjectCS>
)
