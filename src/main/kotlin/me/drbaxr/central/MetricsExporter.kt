package me.drbaxr.central

import com.google.gson.GsonBuilder
import me.drbaxr.model.HierarchyUnit
import me.drbaxr.util.FileTools
import me.drbaxr.util.HierarchyUnitTools
import java.io.FileWriter
import kotlin.io.path.Path

class MetricsExporter {

    companion object {
        const val SUMMARY_FILE_PATH = "analytics/summary.json"
        const val MODEL_FILE_PATH = "analytics/model.json"
    }

    fun export(model: Set<HierarchyUnit>) {
        val coverageByTypes = mutableMapOf<String, TypeCoverage>()

        val coverageTypes = getCoverageTypes(model)
        coverageTypes.forEach { type ->
            val typeUnits = HierarchyUnitTools.getTypeUnits(model, type)
            val typeCoverageAvg = typeUnits.map { it.getCoverage() }.average()
            val typeCoverageCeil = typeUnits.map { if (it.getCoverage() > 0) 1f else 0f }.average()

            coverageByTypes[type] = TypeCoverage(typeCoverageAvg.toFloat(), typeCoverageCeil.toFloat())
        }

        val exportModel = HierarchyUnitTools.mapToExport(model)

        val gson = GsonBuilder().setPrettyPrinting().create()

        FileTools.createFiles(SUMMARY_FILE_PATH, MODEL_FILE_PATH)

        val summaryWriter = FileWriter(Path(SUMMARY_FILE_PATH).toAbsolutePath().toString())
        val modelWriter = FileWriter(MODEL_FILE_PATH)

        gson.toJson(coverageByTypes, summaryWriter)
        gson.toJson(exportModel, modelWriter)

        summaryWriter.flush()
        modelWriter.flush()
        summaryWriter.close()
        modelWriter.close()
    }

    private fun getCoverageTypes(model: Set<HierarchyUnit>): Set<String> {
        val typesSet = mutableSetOf<String>()
        getTreeTypes(model, typesSet)
        return typesSet
    }

    private fun getTreeTypes(units: Set<HierarchyUnit>, types: MutableSet<String>) {
        units.forEach {
            types.add(it.type)
            getTreeTypes(it.children, types)
        }
    }

    private data class TypeCoverage(val average: Float, val ceil: Float)

}