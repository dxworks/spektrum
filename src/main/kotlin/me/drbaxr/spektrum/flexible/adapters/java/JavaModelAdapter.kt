package me.drbaxr.spektrum.flexible.adapters.java

import com.google.gson.Gson
import me.drbaxr.spektrum.fixed.model.HierarchyMethod
import me.drbaxr.spektrum.fixed.model.HierarchyUnit
import me.drbaxr.spektrum.flexible.adapters.ModelAdapter
import me.drbaxr.spektrum.flexible.adapters.java.model.external.ClassJava
import me.drbaxr.spektrum.flexible.adapters.java.model.external.MethodJava
import me.drbaxr.spektrum.flexible.adapters.java.model.external.PackageJava
import me.drbaxr.spektrum.flexible.adapters.java.model.external.ProjectJava
import java.io.FileReader

class JavaModelAdapter(val path: String) : ModelAdapter {
    override fun adapt(): Set<HierarchyUnit> {
        val importModel = Gson().fromJson(FileReader(path), ProjectJava::class.java)

        val project = mapProject(importModel)

        return setOf(project)
    }

    private fun mapProject(project: ProjectJava): HierarchyUnit {
        val unit = HierarchyUnit(
            project.name,
            mutableSetOf(),
            HierarchyUnit.JavaHierarchyUnitTypes.PROJECT,
        )

        unit.children.addAll(project.packages.map { mapPackage(it, unit) })

        return unit
    }

    private fun mapPackage(pkg: PackageJava, parent: HierarchyUnit): HierarchyUnit {
        val unit = HierarchyUnit(
            "${parent.identifier}${HierarchyUnit.childSeparator}${pkg.name}",
            mutableSetOf(),
            HierarchyUnit.JavaHierarchyUnitTypes.PACKAGE,
            parent = parent
        )

        unit.children.addAll(pkg.classes.map { mapClass(it, unit) })

        return unit
    }

    private fun mapClass(cls: ClassJava, parent: HierarchyUnit): HierarchyUnit {
        val unit = HierarchyUnit(
            "${parent.identifier}${HierarchyUnit.childSeparator}${cls.name}",
            mutableSetOf(),
            HierarchyUnit.JavaHierarchyUnitTypes.CLASS,
            parent = parent
        )

        unit.children.addAll(cls.methods.map { mapMethod(it, unit) })

        return unit
    }

    private fun mapMethod(method: MethodJava, parent: HierarchyUnit): HierarchyMethod {
        val hMethod = HierarchyMethod(
            "${parent.identifier}${HierarchyUnit.childSeparator}${method.signature}",
            mutableMapOf(),
        )
        hMethod.parent = parent

        // TODO: callers stack

        return hMethod
    }
}