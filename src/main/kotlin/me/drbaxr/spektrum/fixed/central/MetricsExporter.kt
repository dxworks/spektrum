package me.drbaxr.spektrum.fixed.central

import com.google.gson.GsonBuilder
import me.drbaxr.spektrum.fixed.model.ExportUnit
import me.drbaxr.spektrum.fixed.model.HierarchyUnit
import me.drbaxr.spektrum.util.FileTools
import me.drbaxr.spektrum.util.HierarchyUnitTools
import java.io.FileWriter
import kotlin.io.path.Path

class MetricsExporter {

    companion object {
        const val MODEL_FILE_PATH = "analytics/model.json"
    }

    // use this to only get the model without creating analytics files
    fun getExportModel(model: Set<HierarchyUnit>): Set<ExportUnit> {
        return HierarchyUnitTools.mapToExport(model)
    }

    // use this to also create files with analytics
    fun exportAndSave(model: Set<HierarchyUnit>) {
        val exportModel = getExportModel(model)

        val gson = GsonBuilder().setPrettyPrinting().create()

        FileTools.createFiles(MODEL_FILE_PATH)

        val modelWriter = FileWriter(MODEL_FILE_PATH)

        gson.toJson(exportModel, modelWriter)

        modelWriter.flush()
        modelWriter.close()
    }

}