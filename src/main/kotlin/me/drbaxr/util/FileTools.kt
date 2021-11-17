package me.drbaxr.util

import java.io.File
import kotlin.io.path.Path

class FileTools {

    companion object {
        fun createFiles(vararg files: String) {
            files.forEach {
                val path = Path(it).toAbsolutePath()
                val file = File(path.toString())
                file.parentFile.mkdirs()
            }
        }
    }

}