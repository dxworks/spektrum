package me.drbaxr.spektrum.flexible.adapters.java

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import me.drbaxr.spektrum.fixed.model.HierarchyUnit
import me.drbaxr.spektrum.flexible.adapters.ModelAdapter
import me.drbaxr.spektrum.flexible.adapters.java.model.ProjectJava
import java.io.FileReader

class JavaModelAdapter(val path: String) : ModelAdapter {
    override fun adapt(): Set<HierarchyUnit> {
        val importModel = getOriginalModel(path)

        return setOf()
    }

    private fun getOriginalModel(file: String): ProjectJava {
        val type = object : TypeToken<ProjectJava>() {}.type

        return Gson().fromJson(FileReader(file), ProjectJava::class.java)
    }
}