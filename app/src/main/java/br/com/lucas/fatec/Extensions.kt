package br.com.lucas.fatec

import java.io.File
import java.io.InputStream

fun File.copyInputStreamToFile(inputStream: InputStream) {
    this.outputStream().use { fileOut ->
        inputStream.copyTo(fileOut)
    }
}

//fun InputStream.toFile(path: String) : File {
//    File(path).outputStream().use { this.copyTo(it) }
//}