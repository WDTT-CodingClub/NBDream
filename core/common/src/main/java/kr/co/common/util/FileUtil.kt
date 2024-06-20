package kr.co.common.util

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.text.TextUtils
import kr.co.common.model.CustomException
import java.io.File
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.nio.file.Path
import kotlin.io.path.name

object FileUtil: BaseUtil() {
    override fun initialize(context: Context) {
        super.initialize(context)
    }

    fun getFileFromUri(uri: Uri): File? {
        val fileName = getFileName(uri)
        val tempFile = File(applicationContext.cacheDir, fileName)
        try {
            applicationContext.contentResolver.openInputStream(uri)?.use { inputStream ->
                tempFile.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            return tempFile
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
    fun getFilePath(path: Path): File {
        return try {
            File(
                applicationContext.getExternalFilesDir(null) ?: applicationContext.filesDir,
                "downloads/${path.name}"
            ).also {
                if (!it.exists()) {
                    it.mkdirs()
                }
            }
        } catch (e: IOException) {
            throw CustomException(cause = e)
        }
    }

    private fun getFileName(uri: Uri): String {
        var fileName = "temp_file"
        val cursor = applicationContext.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val displayNameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (displayNameIndex >= 0) {
                    fileName = it.getString(displayNameIndex)
                }
            }
        }
        return fileName
    }

    fun buildValidFilename(name: String): String {
        if (TextUtils.isEmpty(name) || "." == name || ".." == name) {
            return "(invalid)"
        }
        val res = StringBuilder(name.length)
        for (element in name) {
            if (isValidFileNameChar(element)) {
                res.append(element)
            } else {
                res.append('_')
            }
        }
        trimFileName(res)
        return res.toString()
    }

    private fun isValidFileNameChar(c: Char): Boolean {
        return if (c.code in 0x00..0x1f) {
            false
        } else when (c) {
            '"', '*', '/', ':', '<', '>', '?', '\\', '|', '\u0000', 0x7F.toChar() -> false
            else -> true
        }
    }

    private fun trimFileName(res: StringBuilder) {
        var maxBytes = 255
        var raw = res.toString().toByteArray(StandardCharsets.UTF_8)
        if (raw.size > maxBytes) {
            maxBytes -= 3
            while (raw.size > maxBytes) {
                res.deleteCharAt(res.length / 2)
                raw = res.toString().toByteArray(StandardCharsets.UTF_8)
            }
            res.insert(res.length / 2, "...")
        }
    }
}