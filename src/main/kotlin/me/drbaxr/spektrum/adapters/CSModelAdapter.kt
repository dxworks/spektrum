package me.drbaxr.spektrum.adapters

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import me.drbaxr.spektrum.adapters.model.ImportModel
import me.drbaxr.spektrum.main.model.HierarchyUnit
import java.io.FileReader

class CSModelAdapter : ModelAdapter {
    override fun adapt(): Set<HierarchyUnit> {
        val model = getOriginalModel("inputs/Honeydew-testing_stuff.json")

        return setOf()
    }

    private fun getOriginalModel(file: String): ImportModel {
        val type = object : TypeToken<ImportModel>() {}.type
        return Gson().fromJson(FileReader(file), type)
    }
}