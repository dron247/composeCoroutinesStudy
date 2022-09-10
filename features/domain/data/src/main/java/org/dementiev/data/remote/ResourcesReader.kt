package org.dementiev.data.remote

import java.io.FileNotFoundException

internal object ResourcesReader {
    fun readText(path: String): String {
        val inputStream = ResourcesReader::class.java.classLoader?.getResourceAsStream(path)
            ?: throw FileNotFoundException("File [$path] not found.")
        return inputStream.bufferedReader().readText()
    }
}