package me.drbaxr.spektrum.flexible.identifiers.rules.cs

import me.drbaxr.spektrum.fixed.model.HierarchyUnit
import me.drbaxr.spektrum.flexible.adapters.model.external.Class
import me.drbaxr.spektrum.flexible.adapters.model.external.File
import me.drbaxr.spektrum.flexible.adapters.model.external.ImportModel
import me.drbaxr.spektrum.flexible.adapters.model.external.Method
import me.drbaxr.spektrum.flexible.identifiers.rules.cs.exceptions.*

class CSRulesUtil {

    companion object {
        fun findMethodInModel(model: ImportModel, unit: HierarchyUnit): Method {
            val splitId = unit.identifier.split(HierarchyUnit.childSeparator) // file, namespace, class, method

            return findMethodClassInModel(model, unit).methods
                .find { it.name == splitId[3] } ?: throw MethodNotFoundException(splitId[3])
        }

        fun findMethodClassInModel(model: ImportModel, unit: HierarchyUnit): Class {
            val splitId = unit.identifier.split(HierarchyUnit.childSeparator) // file, namespace, class, method

            if (splitId.size != 4)
                throw NotHierarchyMethodException(unit.identifier)

            val file = findFileInModel(model, splitId[0])
                ?: throw FileNotFoundException(splitId[0])
            val namespace = file.namespaces.find { it.name == splitId[1] }
                ?: throw NamespaceNotFoundException(splitId[1])
            return namespace.classes.find { it.name.split(".").last() == splitId[2] }
                ?: throw ClassNotFoundException(splitId[2])
        }

        private fun findFileInModel(model: ImportModel, path: String): File? {
            var file: File? = null

            model.projects.forEach { project ->
                val found = project.files.find { it.path == path }

                if (found != null)
                    file = found
            }

            return file
        }
    }

}