package hust.thehs

import java.io.FileNotFoundException
import java.net.URI


fun absolutePathOf(path: String): String {
    val classLoader = Thread.currentThread().contextClassLoader
    val absolutePath = classLoader.getResource(path)

    return absolutePath?.path?.drop(1) ?: path
}