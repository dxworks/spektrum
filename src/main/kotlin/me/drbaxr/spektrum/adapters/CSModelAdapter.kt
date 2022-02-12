package me.drbaxr.spektrum.adapters

import me.drbaxr.spektrum.adapters.model.ImportModel
import me.drbaxr.spektrum.main.model.HierarchyUnit

class CSModelAdapter : ModelAdapter {
    override fun adapt(): Set<HierarchyUnit> {
        val model = getOriginalModel("inputs/Honeydew-testing_stuff.json")

        println(model)

        return setOf()
    }

    private fun getOriginalModel(file: String): ImportModel {
        TODO("Read from file given as param")
    }
}