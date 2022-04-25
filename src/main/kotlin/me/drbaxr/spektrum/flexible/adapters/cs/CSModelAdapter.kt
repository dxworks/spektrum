package me.drbaxr.spektrum.flexible.adapters.cs

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import me.drbaxr.spektrum.fixed.model.HierarchyMethod
import me.drbaxr.spektrum.fixed.model.HierarchyUnit
import me.drbaxr.spektrum.flexible.RelevantInformation
import me.drbaxr.spektrum.flexible.adapters.ModelAdapter
import me.drbaxr.spektrum.flexible.adapters.cs.model.external.*
import java.io.FileReader

class CSModelAdapter(val path: String) : ModelAdapter {
    private lateinit var methodTreeBuilder: MethodTreeBuilder

    override fun adapt(): Set<HierarchyUnit> {
        val importModel = getOriginalModel(path)
        RelevantInformation.importCSModel = importModel

        methodTreeBuilder = MethodTreeBuilder(importModel)

        return importModel.projects.map { mapProject(it) }.toSet()
    }

    private fun getOriginalModel(file: String): ImportModel {
        val type = object : TypeToken<ImportModel>() {}.type
        return Gson().fromJson(FileReader(file), type)
    }

    private fun mapProject(project: ProjectCS): HierarchyUnit {
        val unit = HierarchyUnit(
            project.name, // project name is unused in the children identifiers
            mutableSetOf(),
            HierarchyUnit.CSHierarchyUnitTypes.PROJECT
        )

        unit.children.addAll(project.files.map { mapFile(it, unit) })

        return unit
    }

    private fun mapFile(file: FileCS, parent: HierarchyUnit): HierarchyUnit {
        val unit = HierarchyUnit(
            file.path,
            mutableSetOf(),
            HierarchyUnit.CSHierarchyUnitTypes.FILE,
            true,
            parent
        )

        unit.children.addAll(file.namespaces.map { mapNamespace(it, unit) })

        return unit
    }

    private fun mapNamespace(namespace: NamespaceCS, parent: HierarchyUnit): HierarchyUnit {
        val unit = HierarchyUnit(
            "${parent.identifier}${HierarchyUnit.childSeparator}${namespace.name}",
            mutableSetOf(),
            HierarchyUnit.CSHierarchyUnitTypes.NAMESPACE,
            true,
            parent
        )

        unit.children.addAll(namespace.classes.map { mapClass(it, unit) })

        return unit
    }

    private fun mapClass(cls: ClassCS, parent: HierarchyUnit): HierarchyUnit {
        val unit = HierarchyUnit(
            "${parent.identifier}${HierarchyUnit.childSeparator}${cls.name.split(".").last()}",
            mutableSetOf(),
            HierarchyUnit.CSHierarchyUnitTypes.CLASS,
            true,
            parent
        )

        unit.children.addAll(
            cls.methods
                .filter { it.type == "Method" }
                .map { mapMethod(it, unit) }
        )

        return unit
    }

    private fun mapMethod(method: MethodCS, parent: HierarchyUnit): HierarchyMethod {
        val hMethod = HierarchyMethod(
            "${parent.identifier}${HierarchyUnit.childSeparator}${method.name}",
            mutableMapOf()
        )
        hMethod.parent = parent

        val fullName = MethodCS.methodNameToFullName(hMethod.identifier)
        val methodNode = methodTreeBuilder.build(fullName, listOf())
        val callerMap = methodNode?.getCallerMap() ?: mapOf()

        callerMap.forEach { (fullName, order) ->
            hMethod.callers[HierarchyMethod.methodFullNameToName(fullName)] = order
        }

        return hMethod
    }
}