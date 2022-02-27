package me.drbaxr.spektrum.adapters

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import me.drbaxr.spektrum.adapters.model.external.*
import me.drbaxr.spektrum.main.model.HierarchyMethod
import me.drbaxr.spektrum.main.model.HierarchyUnit
import java.io.FileReader

class CSModelAdapter : ModelAdapter {
    private lateinit var methodTreeBuilder: MethodTreeBuilder

    override fun adapt(): Set<HierarchyUnit> {
        val importModel = getOriginalModel("inputs/Honeydew-testing_stuff.json")
        methodTreeBuilder = MethodTreeBuilder(importModel)

        return importModel.projects.map { mapProject(it) }.toSet()
    }

    private fun getOriginalModel(file: String): ImportModel {
        val type = object : TypeToken<ImportModel>() {}.type
        return Gson().fromJson(FileReader(file), type)
    }

    private fun mapProject(project: Project): HierarchyUnit {
        val unit = HierarchyUnit(
            project.name, // project name is unused in the children identifiers
            mutableSetOf(),
            HierarchyUnit.CSHierarchyUnitTypes.PROJECT
        )

        unit.children.addAll(project.files.map { mapFile(it, unit) })

        return unit
    }

    private fun mapFile(file: File, parent: HierarchyUnit): HierarchyUnit {
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

    private fun mapNamespace(namespace: Namespace, parent: HierarchyUnit): HierarchyUnit {
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

    private fun mapClass(cls: Class, parent: HierarchyUnit): HierarchyUnit {
        val unit = HierarchyUnit(
            "${parent.identifier}${HierarchyUnit.childSeparator}${cls.name}",
            mutableSetOf(),
            HierarchyUnit.CSHierarchyUnitTypes.CLASS,
            true,
            parent
        )

        unit.children.addAll(cls.methods.map { mapMethod(it, unit.identifier) })

        return unit
    }

    private fun mapMethod(method: Method, parentId: String): HierarchyMethod {
        val hMethod = HierarchyMethod(
            "${parentId}${HierarchyUnit.childSeparator}${method.name}",
            mutableMapOf()
        )

        val fullName = methodNameToFullName(hMethod.identifier)
        val methodNode = methodTreeBuilder.build(fullName, listOf())
        val callerMap = methodNode?.getCallerMap() ?: mapOf()

        callerMap.forEach { (fullName, order) ->
            hMethod.callers[methodFullNameToName(fullName)] = order
        }

        return hMethod
    }

    private fun methodNameToFullName(name: String): String {
        val splitName = name.split(HierarchyUnit.childSeparator)
        val file = splitName[0]
        val namespace = splitName[1]
        val cls = splitName[2].split(".").last()
        val method = splitName[3]
        return methodTreeBuilder.fullName(file, namespace, cls, method)
    }

    private fun methodFullNameToName(fullName: String): String =
        "${Method.file(fullName)}${HierarchyUnit.childSeparator}" +
        "${Method.namespace(fullName)}${HierarchyUnit.childSeparator}" +
        "${Method.namespace(fullName)}.${Method.className(fullName)}${HierarchyUnit.childSeparator}" +
        Method.method(fullName)
}